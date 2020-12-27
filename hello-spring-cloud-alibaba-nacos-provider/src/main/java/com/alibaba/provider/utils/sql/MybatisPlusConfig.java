package com.alibaba.provider.utils.sql;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author WangYifei
 * @date 2020-12-24 16:09
 * @describe
 */
@Configuration
@MapperScan(basePackages = {"com.alibaba"})
public class MybatisPlusConfig {

    @Bean
    public EasySqlInjector easySqlInjector() {
        return new EasySqlInjector();
    }
}
