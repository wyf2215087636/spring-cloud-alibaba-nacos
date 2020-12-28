package com.alibaba.base.aop.annotation;

import com.alibaba.base.sql.Condition;

import java.lang.annotation.*;

/**
 * @author WangYifei
 * @date 2020-12-28 16:29
 * @describe
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Select {
    Condition[] method();
}
