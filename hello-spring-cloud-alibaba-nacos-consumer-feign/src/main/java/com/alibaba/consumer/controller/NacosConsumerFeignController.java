package com.alibaba.consumer.controller;

import com.alibaba.consumer.feign.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYifei
 * @date 2020-12-25 15:13
 * @describe
 */
@RestController
public class NacosConsumerFeignController {

    @Autowired private FeignService feignService;

    @GetMapping(value = "/test/{message}")
    public String test(@PathVariable String message) {
        return feignService.test(message);
    }

    @GetMapping(value = "hi")
    public String hi() {
        return feignService.hi();
    }

    @GetMapping(value = "insert")
    public String insert() {
        return feignService.insert();
    }


}
