package com.githup.liuyanggithup.amon.repository;


import com.githup.liuyanggithup.amon.entity.LimiterEntry;

public interface LimiterEntryRepository {


    /**
     * 根据应用名称和限流场景名查询限流规则
     *
     * @param appName     应用名称
     * @param limiterName 限流场景名
     * @return 限流规则
     */
    LimiterEntry getByAppNameAndLimiterName(String appName, String limiterName);
}
