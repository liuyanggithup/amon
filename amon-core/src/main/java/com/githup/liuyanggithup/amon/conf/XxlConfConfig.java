package com.githup.liuyanggithup.amon.conf;

import com.xxl.conf.core.spring.XxlConfFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xkhu
 * @date 2019/5/1310:03
 */
@Configuration
public class XxlConfConfig {
    private Logger logger = LoggerFactory.getLogger(XxlConfConfig.class);

    @Value("${xxl.conf.admin.address}")
    private String adminAddress;

    @Value("${xxl.conf.env}")
    private String env;

    @Value("${xxl.conf.access.token}")
    private String accessToken;

    @Value("${xxl.conf.mirrorfile}")
    private String mirrorfile;


    @Bean
    public XxlConfFactory xxlConfFactory() {
        XxlConfFactory xxlConf = new XxlConfFactory();
        xxlConf.setAdminAddress(adminAddress);
        xxlConf.setEnv(env);
        xxlConf.setAccessToken(accessToken);
        xxlConf.setMirrorfile(mirrorfile);

        logger.info(">>>>>>>>>>> xxl-conf config init.");
        return xxlConf;
    }
}
