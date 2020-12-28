package com.alibaba.base.entity;

import com.alibaba.base.aop.annotation.Select;
import com.alibaba.base.sql.Condition;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author WangYifei
 * @date 2020-12-28 9:36
 * @describe 实现controller 统一接口 的实体类
 */
@Getter
@Setter
public class Entity {
    /**
     * id
     */
    @Select(method = Condition.EQ)
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否已删除  1 是 0 不是
     */
    private Integer delFlag;

    /**
     * 更新操作人
     */
    private String updateUserId;

    /**
     * 创建操作人
     */
    private String createUserId;
}
