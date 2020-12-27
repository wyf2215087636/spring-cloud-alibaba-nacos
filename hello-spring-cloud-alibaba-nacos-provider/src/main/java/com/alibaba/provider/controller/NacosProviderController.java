package com.alibaba.provider.controller;

import com.alibaba.provider.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYifei
 * @date 2020-12-25 14:25
 * @describe nacos 服务提供类 控制器
 */
@RestController
public class NacosProviderController {

    @Autowired private UserService userService;

    @Value("${server.port}")
    private String port;

    // 注入配置文件上下文
    @Autowired
    private ConfigurableApplicationContext applicationContext;

    @GetMapping("/test/{message}")
    public String test(@PathVariable String message) {
        return "Hello Nacos Discovery " + message + " i am from port: " + port;
    }

    // 从上下文中读取配置
    @GetMapping("hi")
    public String sayHi() {
        return "Hello, " + applicationContext.getEnvironment().getProperty("user.name");
    }

    @GetMapping("insert")
    public String insert() {
        return userService.insert();
    }
}
