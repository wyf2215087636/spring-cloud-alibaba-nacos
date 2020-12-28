package com.alibaba.gateway.controller;

import com.alibaba.gateway.filters.MygateWayFilter;
import com.alibaba.result.Result;
import com.alibaba.result.SimpleResult;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYifei
 * @date 2020-12-28 8:56
 * @describe
 */
@RestController
@RequestMapping("/login/api")
public class LoginController {

    @Autowired private StringRedisTemplate redisTemplate;

    @PostMapping("login")
    public Result login(String id) {
        JwtBuilder builder = Jwts.builder()
                //设置用户id
                .setId(id)
                //设置主题（这个主题是干什么用的？，也可以不设置，感觉可以放其他重要的信息）
                .setSubject("bwcx123")
                //设置创建时间
                .setIssuedAt(new Date())
                //设置过期时间
                .setExpiration(new Date(new Date().getTime() + 1000 * 60 * 10))
                //设置角色名称
                .claim("roles","admin")
                //自定义key(可以理解为解密的钥匙一般在配置文件里面)
                .signWith(SignatureAlgorithm.HS256, MygateWayFilter.getKey());
        //用户登录之后，返回给前端token
        String token = builder.compact();
        // 加入缓存
        redisTemplate.opsForValue().set("token_" + id, token, 5, TimeUnit.MINUTES);

        // 把Token返回
        return SimpleResult.success(200, "登录成功", token);
    }
}
