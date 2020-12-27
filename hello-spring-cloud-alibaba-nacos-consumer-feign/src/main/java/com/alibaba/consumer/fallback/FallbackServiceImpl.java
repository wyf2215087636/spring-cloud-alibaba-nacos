package com.alibaba.consumer.fallback;

import com.alibaba.consumer.feign.FeignService;
import org.springframework.stereotype.Component;

/**
 * @author WangYifei
 * @date 2020-12-25 15:29
 * @describe
 */
@Component
public class FallbackServiceImpl implements FeignService{

    @Override
    public String test(String message) {
        return "响应异常，请稍后再试";
    }

    @Override
    public String hi() {
        return "响应异常，请稍后再试";
    }

    @Override
    public String insert() {
        return "响应异常，请稍后再试";
    }
}
