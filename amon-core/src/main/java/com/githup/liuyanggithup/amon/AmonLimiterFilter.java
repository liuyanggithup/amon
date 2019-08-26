package com.githup.liuyanggithup.amon;

import com.githup.liuyanggithup.amon.exception.AmonException;
import com.githup.liuyanggithup.amon.manager.LimiterManager;
import com.google.common.collect.Maps;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author: seventeen
 * @Date: 2019/4/5
 * @description:
 */
@Aspect
@Component
public class AmonLimiterFilter {

    private Map<String, Limiter> limiterMap = Maps.newConcurrentMap();

    @Autowired
    private LimiterManager rateLimiterManager;


    @Pointcut("@annotation(com.githup.liuyanggithup.amon.Limiter)")
    public void proxyAspect() {

    }

    @Around("proxyAspect()")
    public Object doInvoke(ProceedingJoinPoint joinPoint) throws Throwable {
        Limiter limiter = getLimiter(joinPoint);
        if (limiter.blockStrategy()) {
            rateLimiterManager.acquire(limiter);
        } else {
            boolean tryAcquire = rateLimiterManager.tryAcquire(limiter);
            if (!tryAcquire) {
                throw new AmonException(limiter.blockMsg());
            }
        }

        return joinPoint.proceed();
    }

    private Limiter getLimiter(ProceedingJoinPoint joinPoint) {
        String key = joinPoint.getSignature().toShortString();
        Limiter result = limiterMap.get(key);
        if (result == null) {
            MethodSignature sig = (MethodSignature) joinPoint.getSignature();
            result = sig.getMethod().getAnnotation(Limiter.class);
            limiterMap.put(key, result);
        }
        return result;
    }


}
