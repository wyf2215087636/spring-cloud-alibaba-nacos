package com.alibaba.consumer.feign;

import com.alibaba.consumer.fallback.FallbackServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author WangYifei
 * @date 2020-12-25 15:11
 * @describe @FeignClient 表示这是一个请求接口，value表示请求于这个模块中的数据
 */
@FeignClient(value = "nacos-provider", fallback = FallbackServiceImpl.class)
@Component
public interface FeignService {

    @GetMapping(value = "/test/{message}")
    String test(@PathVariable String message);

    @GetMapping(value = "hi")
    String hi();

    @GetMapping(value = "insert")
    String insert();
}
