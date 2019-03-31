package com.githup.liuyanggithup.amon.repository;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.githup.liuyanggithup.amon.entity.LimiterEntry;
import com.githup.liuyanggithup.amon.manager.LimiterManager;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ApolloLimiterEntryRepository implements  LimiterEntryRepository{

    private static final Logger LOGGER = LoggerFactory.getLogger(ApolloLimiterEntryRepository.class);


    private Map<String, LimiterEntry> limitNameValueMap = new ConcurrentHashMap<>();


    private static final Config config = ConfigService.getAppConfig();


    @Autowired
    @Qualifier("limiterManager")
    private LimiterManager rateLimiterManager;


    @Override
    public LimiterEntry getByAppNameAndLimiterName(String appName, String limiterName) {

        String configKey = appName + "." + limiterName;
        String configProperty = config.getProperty(configKey, null);
        if (null == limitNameValueMap.get(configKey)) {
            LimiterEntry limiterEntry = toLimiterEntity(configProperty,appName,limiterName);


            config.addChangeListener(new ConfigChangeListener() {
                @Override
                public void onChange(ConfigChangeEvent changeEvent) {
                    for (String key : changeEvent.changedKeys()) {
                        if(key.equals(configKey)){
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
        }else {
            return limitNameValueMap.get(configKey);
        }
    }


    private LimiterEntry toLimiterEntity(String configProperty, String appName, String limiterName){

        LimiterEntry limiterEntry = new Gson().fromJson(configProperty, LimiterEntry.class);
        limiterEntry.setAppName(appName);
        limiterEntry.setName(limiterName);
        return limiterEntry;
    }


}
