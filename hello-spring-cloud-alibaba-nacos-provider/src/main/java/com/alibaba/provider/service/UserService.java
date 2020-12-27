package com.alibaba.provider.service;

import com.alibaba.provider.entity.User;
import com.alibaba.provider.mapper.UserMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author WangYifei
 * @date 2020-12-25 17:44
 * @describe
 */
@Repository
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    public String insert() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            User user = new User();
            user.setId(UUID.randomUUID().toString().replace("-",""));
            user.setName("王逸飞");
            user.setPassword("123456");
            users.add(user);
        }
        long l = System.currentTimeMillis();
        baseMapper.insertBatchSomeColumn(users);
        log.info("插入耗时:{}", System.currentTimeMillis() - l);
        return "添加成功";
    }
}
