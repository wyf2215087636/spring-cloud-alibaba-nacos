package com.alibaba.base;

import com.alibaba.result.Result;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author WangYifei
 * @date 2020-12-28 9:34
 * @describe
 */
public interface Controller<Entity extends com.alibaba.base.entity.Entity> {

    Result insert(@RequestBody Entity entity);

    Result deleteById(String id);

    Result deleteByNotNull(@RequestBody Entity entity);

    Result deleteByCustomize(@RequestBody Entity entity);

    Result deleteByDelFlag(String id);

    Result updateEntityById(@RequestBody Entity entity);

    Result updateByCustomize(@RequestBody Entity entity);

    Result list(Integer pageNum, Integer pageSize);

    Result listByCondition(@RequestBody Entity entity, Integer pageNum, Integer pageSize);





}
