package com.githup.liuyanggithup.amon.repository;

import com.githup.liuyanggithup.amon.entity.ConfigurationEnum;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author: xia_xun
 * @Date: 2019/4/1
 * @description:
 */
@Component
public class RepositoryFactory implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        RepositoryFactory.applicationContext = applicationContext;
    }

    private <T> T getBean(Class<T> clz){
        return applicationContext.getBean(clz);
    }

    private Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }


    private LimiterEntryRepository limiterEntryRepository;

    @Value("${amon.configuration.center}")
    private String configuration;


    public LimiterEntryRepository getLimiterEntryRepository() {
        String beanName = ConfigurationEnum.getBeanNameByCode(configuration);
        return (LimiterEntryRepository) getBean(beanName);
    }

}
