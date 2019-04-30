package com.githup.liuyanggithup.amon.manager;

import com.githup.liuyanggithup.amon.Limiter;
import com.githup.liuyanggithup.amon.constants.LimiterConstants;
import com.githup.liuyanggithup.amon.entity.LimiterEntry;
import com.githup.liuyanggithup.amon.repository.LimiterEntryRepository;
import com.githup.liuyanggithup.amon.repository.RepositoryFactory;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author: seventeen
 * @Date: 2019/4/5
 * @description:
 */
@Component
public class LimiterManager {

    private Map<String, RateLimiter> limiters = Maps.newConcurrentMap();

    @Resource
    private RepositoryFactory repositoryFactory;

    @Value(value = "${amon.app.name}")
    private String appName;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 获取限流令牌(分钟级别限流)
     *
     * @param limiter 限流注解
     */
    public void acquire(Limiter limiter) {
        try {
            RateLimiter rateLimiter = getRateLimiter(limiter.name(), limiter.rate());
            rateLimiter.acquire(LimiterConstants.RATE_LIMITER_MINUTES_TIME_UNIT);
        } catch (Exception e) {
            logger.error("获取限流令牌异常:limiterName={}", limiter.name(), e);
        }
    }

    /**
     * 获取限流令牌(秒级别限流)
     *
     * @param limiterName 限流场景名称
     */
    public void acquire(String limiterName) {
        try {
            RateLimiter rateLimiter = getRateLimiter(limiterName, LimiterConstants.DEFAULT_LIMITER_RATE);
            rateLimiter.acquire(LimiterConstants.RATE_LIMITER_SECOND_TIME_UNIT);
        } catch (Exception e) {
            logger.error("获取限流令牌异常:limiterName={}", limiterName, e);
        }
    }

    /**
     * 尝试获取限流令牌(秒级别限流)
     * 获取令牌失败返回false,成功返回true
     *
     * @param limiter
     */
    public boolean tryAcquire(Limiter limiter) {
        try {
            RateLimiter rateLimiter = getRateLimiter(limiter.name(), limiter.rate());
            if (rateLimiter.tryAcquire(LimiterConstants.RATE_LIMITER_SECOND_TIME_UNIT)) {
                return true;
            } else {
                logger.info("尝试获取令牌,RateLimiter={}获取限流令牌失败,达到限流值={}", limiter.name(), rateLimiter.getRate());
                return false;
            }
        } catch (Exception e) {
            logger.error("尝试获取限流令牌异常:limiterName={}", limiter.name(), e);
            return false;
        }
    }

    /**
     * 尝试获取限流令牌(秒级别限流)
     * 获取令牌失败返回false,成功返回true
     *
     * @param limiterName
     */
    public boolean tryAcquire(String limiterName) {
        try {
            RateLimiter rateLimiter = getRateLimiter(limiterName, LimiterConstants.DEFAULT_LIMITER_RATE);
            if (rateLimiter.tryAcquire(LimiterConstants.RATE_LIMITER_SECOND_TIME_UNIT)) {
                return true;
            } else {
                logger.info("尝试获取令牌,RateLimiter={}获取限流令牌失败,达到限流值={}", limiterName, rateLimiter.getRate());
                return false;
            }
        } catch (Exception e) {
            logger.error("尝试获取限流令牌异常:limiterName={}", limiterName, e);
            return false;
        }

    }

    private RateLimiter getRateLimiter(String limiterName, double defaultRate) {
        RateLimiter rateLimiter = limiters.get(limiterName);
        if (null == rateLimiter) {
            synchronized (this) {
                Double rate = defaultRate;
                if (null != repositoryFactory.getLimiterEntryRepository()) {
                    LimiterEntry limiterEntry =
                            repositoryFactory.getLimiterEntryRepository().getByAppNameAndLimiterName(appName, limiterName);
                    if (null != limiterEntry) {
                        rate = limiterEntry.getRate();
                    }
                }

                logger.info("创建RateLimiter:limiterName={},rate={}", limiterName, rate);
                rateLimiter = RateLimiter.create(rate);
                limiters.put(limiterName, rateLimiter);
            }
        }
        return rateLimiter;
    }

    /**
     * 修改限流速率
     *
     * @param limiterName 限流场景名称
     * @param rate        限流速率
     */
    public void modifyRates(String limiterName, double rate) {
        RateLimiter limiter = limiters.get(limiterName);
        if (null == limiter) {
            limiter = getRateLimiter(limiterName, rate);
            logger.info("修改限流速率成功(RateLimiter不存在):limiterName={},rate={}", limiterName, rate);
            Preconditions.checkArgument(null != limiter, "RateLimiterHolder do not contain " + limiterName);
        } else {
            if (Math.abs(limiter.getRate() - rate) > 0.01) {
                logger.info("修改限流速率成功:limiterName={},rate={}", limiterName, rate);
                limiter.setRate(rate);
            }
        }
    }

    public RateLimiter getRateLimiter(String limiterName) {
        return limiters.get(limiterName);
    }

}
