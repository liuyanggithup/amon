# amon
> 针对互联网常见的限流场景，一个注解解决方法级限流难题，与spring轻松整合

----

## 1 介绍
amon限流框架将会支持与apollo,xxl-conf,zconf配置中心（具体支持项参见release版本说明），动态修改限流速率无需重启。


## 2 使用
### 2.1 引入jar包
```
<dependency>
    <groupId>com.githup.liuyanggithup</groupId>
    <artifactId>amon-core</artifactId>
    <version>${last.version}</version>
</dependency>
```

### 2.2 配置扫描
在SpringBoot启动类添加

```
@ComponentScan(basePackages = {"com.githup.liuyanggithup.amon"})
```

### 2.3 配置中心集成

集成支持的配置中心，以下配置项必填
- amon.configuration.center

	`apollo xxl-conf zconf`

	集成的配置中心,对应apollo,xxl-conf,zconf是作者的基于zk开源配置中心敬请关注。

- amon.app.name

	`例如amon-sample-apollo`

	应用的名称，建议和配置的spring.application.name保持一致。

- 限流接口名称

    `例如amon.test.amon = 600`
    
    配置自定义的key,value为限流速率

### 2.4 方法级限流实战
```
    @Limiter(name = "amon.test.amon")
    public String amon() {
        return "hello";
    }
```
   使用Limiter注解，name属性是在配置中心配置的限流接口名称
   
## 3 技术交流&答疑
>QQ群：721567149
