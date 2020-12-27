package com.alibaba.provider.mapper;

import com.alibaba.provider.entity.User;
import com.alibaba.provider.utils.sql.EasyBaseMapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author WangYifei
 * @date 2020-12-25 17:43
 * @describe
 */
@Mapper
public interface UserMapper extends EasyBaseMapper<User> {
}
