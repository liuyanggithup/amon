package com.githup.liuyanggithup.amon.entity;

/**
 * @author: xia_xun
 * @Date: 2019/4/1
 * @description:
 */
public enum ConfigurationEnum {
    //apollo
    APOLLO("apollo", "apolloLimiterEntryRepository"),
    //xxl-conf
    XXLCONF("xxl-conf", "xxlConfLimiterEntryRepository"),
    //zconf
    ZCONF("zconf", "zConfLimiterEntryRepository");

    /**
     * code
     */
    private String code;
    /**
     * bean name
     */
    private String beanName;

    ConfigurationEnum(String code, String beanName) {
        this.code = code;
        this.beanName = beanName;
    }

    public static String getBeanNameByCode(String code) {

        for (ConfigurationEnum payTypeEnum : ConfigurationEnum.values()) {
            if (payTypeEnum.getCode().equals(code)) {
                return payTypeEnum.getBeanName();
            }
        }
        return null;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }}
