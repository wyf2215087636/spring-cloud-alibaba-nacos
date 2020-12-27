package com.alibaba.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author WangYifei
 * @date 2020-12-25 15:09
 * @describe
 */
@SpringBootApplication
@EnableFeignClients // 开始feign功能
@EnableDiscoveryClient // 注册到 nacos 注册中心 表示是客户端 是微服务，需要nacos
public class ConsumerFeignApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeignApplication.class, args);
    }
}
