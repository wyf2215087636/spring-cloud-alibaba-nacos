package com.alibaba.base.aop.annotation;

import java.lang.annotation.*;

/**
 * @author WangYifei
 * @date 2020-12-28 10:36
 * @describe
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InitEntity {
    String remark() default "";
}
