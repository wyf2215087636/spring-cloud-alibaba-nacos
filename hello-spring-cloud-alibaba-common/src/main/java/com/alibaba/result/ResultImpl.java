package com.alibaba.result;

import lombok.Getter;

/**
 * @author WangYifei
 * @date 2020-12-27 10:15
 * @describe
 */

public interface ResultImpl<Entity> {
    /**
     * 获取 返回编号 信息
     * @return
     */
    Integer getCode();

    /**
     * 获取返回提示信息
     * @return
     */
    String getMsg();

    /**
     * 获取数据
     * @return
     */
    Object getData();

    /**
     * 获取序列化之后的数据例如json
     * Entity 用于序列化的数据
     * @return
     */
    Entity serializationData();
}
