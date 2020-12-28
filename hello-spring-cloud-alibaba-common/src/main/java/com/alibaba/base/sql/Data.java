package com.alibaba.base.sql;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * @author WangYifei
 * @date 2020-12-28 16:33
 * @describe
 */
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private String fileName;

    private Condition[] method;

    private Object value;
}
