package com.githup.liuyanggithup.amon.repository;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.githup.liuyanggithup.amon.entity.LimiterEntry;
import com.githup.liuyanggithup.amon.manager.LimiterManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: seventeen
 * @Date: 2019/4/5
 * @description:
 */
@Component
public class ApolloLimiterEntryRepository extends AbstractLimiterEntryRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApolloLimiterEntryRepository.class);
    private static final Config config = ConfigService.getAppConfig();
    private Map<String, LimiterEntry> limitNameValueMap = new ConcurrentHashMap<>();

    @Autowired
    @Qualifier("limiterManager")
    private LimiterManager rateLimiterManager;


    @Override
    public LimiterEntry getByAppNameAndLimiterName(String appName, String limiterName) {

        String configKey = appName + "." + limiterName;
        String configProperty = config.getProperty(configKey, null);
        if (null == limitNameValueMap.get(configKey)) {
            LimiterEntry limiterEntry = toLimiterEntity(configProperty, appName, limiterName);


            config.addChangeListener(new ConfigChangeListener() {
                @Override
                public void onChange(ConfigChangeEvent changeEvent) {
                    for (String key : changeEvent.changedKeys()) {
                        if (key.equals(configKey)) {
                            ConfigChange change = changeEvent.getChange(key);
                            String newValue = change.getNewValue();
                            LimiterEntry modifyEntry = toLimiterEntity(newValue, appName, limiterName);

                            rateLimiterManager.modifyRates(limiterName, modifyEntry.getRate());
                            limitNameValueMap.put(configKey, modifyEntry);
                        }
                    }
                }
            });


            return limiterEntry;
        } else {
            return limitNameValueMap.get(configKey);
        }
    }


}
