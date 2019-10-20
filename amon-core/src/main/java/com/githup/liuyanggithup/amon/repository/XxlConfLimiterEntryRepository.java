package com.githup.liuyanggithup.amon.repository;

import com.githup.liuyanggithup.amon.entity.LimiterEntry;
import com.githup.liuyanggithup.amon.manager.LimiterManager;
import com.xxl.conf.core.XxlConfClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xkhu
 * @date 2019/5/139:56
 */
@Component
public class XxlConfLimiterEntryRepository extends AbstractLimiterEntryRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(XxlConfLimiterEntryRepository.class);
    private Map<String, LimiterEntry> limitNameValueMap = new ConcurrentHashMap<>();

    @Autowired
    @Qualifier("limiterManager")
    private LimiterManager rateLimiterManager;

    @Override
    public LimiterEntry getByAppNameAndLimiterName(String appName, String limiterName) {
        String configKey = appName + "." + limiterName;
        String configProperty = XxlConfClient.get(limiterName, null);
        if (null == limitNameValueMap.get(configKey)) {
            LimiterEntry limiterEntry = toLimiterEntity(configProperty, appName, limiterName);
            limitNameValueMap.put(configKey, limiterEntry);

            XxlConfClient.addListener(limiterName, (String key, String value) -> {
                LimiterEntry modifyEntry = toLimiterEntity(value, appName, limiterName);
                rateLimiterManager.modifyRates(limiterName, modifyEntry.getRate());
                limitNameValueMap.put(configKey, modifyEntry);

            });

            return limiterEntry;
        } else {
            return limitNameValueMap.get(configKey);
        }
    }
}
