package com.alibaba.provider.entity;

import com.alibaba.base.aop.annotation.Select;
import com.alibaba.base.entity.Entity;
import com.alibaba.base.entity.EntityImpl;
import com.alibaba.base.sql.Condition;
import lombok.Data;

/**
 * @author WangYifei
 * @date 2020-12-25 17:44
 * @describe
 */
@Data
public class User extends Entity {
    private String name;

    private String password;
}
