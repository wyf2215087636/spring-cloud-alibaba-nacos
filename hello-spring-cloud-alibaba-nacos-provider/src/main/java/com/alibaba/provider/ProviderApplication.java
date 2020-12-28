package com.alibaba.provider;

import com.alibaba.base.aop.EntityInitAop;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author WangYifei
 * @date 2020-12-25 14:22
 * @describe
 */
@SpringBootApplication(scanBasePackageClasses = EntityInitAop.class,
        scanBasePackages = {"com.alibaba.provider"})
@EnableDiscoveryClient
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
