package com.githup.liuyanggithup.amon.repository;

import com.githup.liuyanggithup.amon.entity.LimiterEntry;
import com.google.gson.Gson;

/**
 * @author: xia_xun
 * @Date: 2019/4/1
 * @description:
 */
public abstract class AbstractLimiterEntryRepository implements LimiterEntryRepository {

    protected LimiterEntry toLimiterEntity(String configProperty, String appName, String limiterName){


        LimiterEntry limiterEntry = new LimiterEntry();
        limiterEntry.setRate(Double.parseDouble(configProperty));
        limiterEntry.setAppName(appName);
        limiterEntry.setName(limiterName);
        return limiterEntry;
    }

}
