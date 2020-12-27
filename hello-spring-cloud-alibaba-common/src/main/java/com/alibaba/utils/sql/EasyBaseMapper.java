package com.alibaba.utils.sql;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @author WangYifei
 * @date 2020-12-24 16:26
 * @describe
 */
public interface EasyBaseMapper<T> extends BaseMapper<T> {

    /**
     * 批量插入数据
     * @param entityList 实体类集合
     * @return
     */
    Integer insertBatchSomeColumn(List<T> entityList);
}
