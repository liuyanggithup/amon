package com.githup.liuyanggithup.amon.constants;

/**
 * @author: seventeen
 * @Date: 2019/4/5
 * @description:
 */
public class LimiterConstants {

    /**
     * 限流单位,60s(分钟)
     */
    public static final Integer RATE_LIMITER_MINUTES_TIME_UNIT = 60;

    /**
     * 限流单位,1s(秒)
     */
    public static final Integer RATE_LIMITER_SECOND_TIME_UNIT = 1;

    /**
     * 默认限流速率 (100/s)
     */
    public static final Double DEFAULT_LIMITER_RATE = 100.0;
}
