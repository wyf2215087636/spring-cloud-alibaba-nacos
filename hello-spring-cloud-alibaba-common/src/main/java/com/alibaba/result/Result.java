package com.alibaba.result;

import com.alibaba.utils.JSON;
import lombok.Setter;
import lombok.ToString;

/**
 * @author WangYifei
 * @date 2020-12-27 10:22
 * @describe 默认实现
 */
@Setter
@ToString
public class Result implements ResultImpl<String>{
    Integer code;

    String msg;

    Object data;

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    @Override
    public Object getData() {
        return data;
    }

    @Override
    public String serializationData() {
        return JSON.toJSONString(data);
    }
}
