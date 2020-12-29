package com.alibaba.provider.controller;

import com.alibaba.base.BaseController;
import com.alibaba.provider.entity.User;
import com.alibaba.provider.mapper.UserMapper;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author WangYifei
 * @date 2020-12-25 14:25
 * @describe nacos 服务提供类 控制器
 */
@RestController
@Api("测试")
public class NacosProviderController extends BaseController<User, UserMapper> {

}


