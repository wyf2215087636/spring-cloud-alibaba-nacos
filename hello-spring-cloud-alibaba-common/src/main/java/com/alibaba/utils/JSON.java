package com.alibaba.utils;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author WangYifei
 * @date 2020-12-11 14:15
 * @describe spring 自带json工具封装 jackson（模拟fastjson使用）
 */
@Slf4j
public class JSON {

    static ObjectMapper mapper = new ObjectMapper();

    static {
        // 转换为格式化的json
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        // 如果json中有新增的字段并且是实体类类中不存在的，不报错
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }

    /**
     * 对象转 JSON字符串
     * @param o
     */
    public static String toJSONString(Object o) {
        String json = null;
        try {
            json = mapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }

    /**
     * 字符串转对象默认Map
     * @param json
     * @return
     */
    public static Map<String, Object> toJSONObject(String json) {
        return toJSONObject(json, Map.class);
    }

    /**
     * 字符串转对象指定
     * @param json json字符串
     * @param entityClass 对象的class
     * @param <Entity> 实体类
     * @return 对象
     */
    public static <Entity> Entity toJSONObject(String json, Class<Entity> entityClass) {
        Entity entity = null;
        try {
            entity = mapper.readValue(json, entityClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return entity;
    }



    /**
     * 转集合 指定类型
     * @param json json
     * @param el 对象类型
     * @param <Entity> 实体类
     * @return 集合
     */
    public static <Entity> List<Entity> toJSONArray(String json, Class<Entity> el) {
        JavaType javaType = getCollectionType(el);
        List<Entity> list = null;
        try {
            list = mapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * json转集合 指定集合和对象
     * @param json json
     * @param cl 指定集合
     * @param el 对象
     * @param <Entity> 实体类
     * @return 集合泛型结果集
     */
    public static <Entity> List<Entity> toJSONArray(String json, Class<? extends List> cl, Class<Entity> el) {
        JavaType javaType = getCollectionType(cl, el);
        List<Entity> list = null;
        try {
            list = mapper.readValue(json, javaType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 转集合默认 map
     * @param json json字符串
     * @return 集合
     */
    public static List<Map<String,Object>> toJSONArray(String json) {
        List<Map<String,Object>> list = null;
        try {
            list = mapper.readValue(json, getCollectionType(Map.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 默认集合为arrayList
     * @param el 集合泛型
     * @param <Entity> 实体类
     * @return 指定泛型
     */
    private static <Entity> JavaType getCollectionType(Class<Entity> el) {
        return mapper.getTypeFactory().constructParametricType(ArrayList.class, el);
    }

    /**
     * 用于指定泛型
     * @param co 对象类型
     * @param el 泛型
     * @param <Entity> 实体类
     * @return 对象泛型
     */
    @SafeVarargs
    private static <Entity> JavaType getCollectionType(Class<? extends List> co, Class<Entity>... el) {
        return mapper.getTypeFactory().constructParametricType(co, el);
    }

    /**
     * 根据数据源创建指定对象
     * @param source 数据源 ？ extends Object
     * @param entityClass 需要生成的对象类
     * @param <Entity> 需要生成的对象类
     * @param <Source> 数据源 ？ extends Object
     * @return 返回 Entity
     */
    public static <Entity,Source> Entity ObjectToObject(Source source, Class<Entity> entityClass) {
        return toJSONObject(toJSONString(source), entityClass);
    }

}