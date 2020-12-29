package com.alibaba.base.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author WangYifei
 * @date 2020-12-28 10:14
 * @describe
 */
public class Service<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}
