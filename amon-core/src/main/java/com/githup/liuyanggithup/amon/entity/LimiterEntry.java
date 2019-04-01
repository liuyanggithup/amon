package com.githup.liuyanggithup.amon.entity;

/**
 * @author: seventeen
 * @Date: 2019/4/5
 * @description:
 */
public class LimiterEntry {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 应用机器数
     */
    private Integer appMachineNum;
    /**
     * 限流场景名称
     */
    private String name;

    /**
     * 单机限流速率
     */
    private Double rate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public Integer getAppMachineNum() {
        return appMachineNum;
    }

    public void setAppMachineNum(Integer appMachineNum) {
        this.appMachineNum = appMachineNum;
    }


}
