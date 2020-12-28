package com.alibaba.result;

import com.sun.xml.internal.ws.util.xml.CDATA;

/**
 * @author WangYifei
 * @date 2020-12-27 10:15
 * @describe
 */
public class SimpleResult extends Result{

    private SimpleResult(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static Result success(Integer code, String msg, Object data) {
        return new SimpleResult(code, msg, data);
    }

    public static Result success(Object data) {
        return success(200, "操作成功", data);
    }

    public static Result error(Integer code, String msg, Object data) {
        return new SimpleResult(code, msg, data);
    }

    public static Result error(Object data) {
        return new SimpleResult(500, "操作失败", data);
    }
}
