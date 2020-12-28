package com.alibaba.base;

import com.alibaba.base.aop.annotation.InitEntity;
import com.alibaba.base.aop.annotation.Select;
import com.alibaba.base.entity.Entity;
import com.alibaba.base.entity.EntityImpl;
import com.alibaba.base.service.Service;
import com.alibaba.base.sql.Condition;
import com.alibaba.base.sql.Data;
import com.alibaba.result.Result;
import com.alibaba.result.SimpleResult;
import com.alibaba.utils.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WangYifei
 * @date 2020-12-28 9:32
 * @describe 公共处理类，适用于单表操作
 */
public class BaseController<Entity extends com.alibaba.base.entity.Entity,
        M extends BaseMapper<Entity>> extends Service<M, Entity> implements Controller<Entity>{

    private static Pattern humpPattern = Pattern.compile("[A-Z]");


    /**
     * 公共新增
     * @param entity 实体类
     * @return 返回状态
     */
    @PostMapping("insert")
    @Override
    @InitEntity
    public Result insert(@RequestBody Entity entity) {
        if (baseMapper.insert(entity) < 1) {
            return SimpleResult.error(null);
        }
        return SimpleResult.success(null);
    }

    /**
     * 根据id删除
     * @param id 主键id
     * @return 返回状态
     */
    @GetMapping("deleteById")
    @Override
    public Result deleteById(String id) {
        if (baseMapper.deleteById(id) < 1) {
            return SimpleResult.error(null);
        }
        return SimpleResult.success(null);
    }

    /**
     * 根据对象不为空的属性作为查询条件
     * @param entity 对象
     * @return 返回状态
     */
    @PostMapping("deleteByNotNull")
    @Override
    public Result deleteByNotNull(Entity entity) {
        Map map = JSON.ObjectToObject(entity, Map.class);
        QueryWrapper<Entity> entityQueryWrapper = new QueryWrapper<>();
        for (Object o : map.keySet()) {
            Object cur = map.get(o);
            if (cur != null) {
                entityQueryWrapper.eq(humpToLine(o.toString()), map.get(o));
            }
        }
        if (baseMapper.delete(entityQueryWrapper) < 1) {
            return SimpleResult.error(null);
        }
        return SimpleResult.success(null);
    }

    /** 驼峰转下划线*/
    private static String humpToLine(String str) {
        Matcher matcher = humpPattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 自定义删除字段作为条件
     * @param entity 对象
     * @return 返回状态
     */
    @PostMapping("deleteByCustomize")
    @Override
    public Result deleteByCustomize(Entity entity) {

        if (baseMapper.delete(getWrapper(entity)) < 1) {
            return SimpleResult.error(null);
        }
        return SimpleResult.success(null);
    }

    /**
     * 通过实体类中的注解 解析条件拼接
     * @param entity 实体类
     * @return 返回值
     */
    private Wrapper<Entity> getWrapper(Entity entity) {
        List<Data> data = new ArrayList<>();
        Class<?> aClass = entity.getClass();
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            // 获取字段使用到的注解
            Select annotation = field.getAnnotation(Select.class);
            if (annotation == null) {
                continue;
            }
            try {
                data.add(new Data(humpToLine(field.getName()), annotation.method(), field.get(entity)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 获取父类
        Class<?> superclass = aClass.getSuperclass();
        Field[] superFields = superclass.getDeclaredFields();
        for (Field superField : superFields) {
            superField.setAccessible(true);
            Select annotation = superField.getAnnotation(Select.class);
            if (annotation == null) {
                continue;
            }
            try {
                data.add(new Data(humpToLine(superField.getName()), annotation.method(), superField.get(entity)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        QueryWrapper<Entity> queryWrapper = new QueryWrapper<>();
        for (Data datum : data) {
            match(queryWrapper, datum);
        }
        return queryWrapper;
    }


    private void match(QueryWrapper<Entity> queryWrapper, Data data) {
        for (Condition condition : data.getMethod()) {
            switch (condition) {
                case EQ: {
                    queryWrapper.eq(data.getFileName(), data.getValue());
                    break;
                }
                case GEQ: {
                    queryWrapper.ge(data.getFileName(), data.getValue());
                    break;
                }
            }

        }
    }

    /**
     * 逻辑删除
     * @param id
     * @return
     */
    @Override
    public Result deleteByDelFlag(String id) {
        LambdaQueryWrapper<Entity> entityQueryWrapper = new LambdaQueryWrapper<>();
        entityQueryWrapper.eq(Entity::getId, id);

        return null;
    }

    /**
     * 通过id更新
     * @param entity 对象实体类
     * @return 返回状态
     */
    @PutMapping("updateById")
    @Override
    public Result updateEntityById(Entity entity) {
        return null;
    }

    /**
     * 通过自定义属性作为更新条件
     * @param entity 对象 实体类
     * @return 返回状态
     */
    @PutMapping("updateByCustomize")
    @Override
    public Result updateByCustomize(Entity entity) {
        return null;
    }

    /**
     * 查询列表
     * @param pageNum 页码
     * @param pageSize 页数
     * @return 返回状态
     */
    @GetMapping("list")
    @Override
    public Result list(Integer pageNum, Integer pageSize) {
        return null;
    }

    /**
     * 自定义查询条件
     * @param entity 对象实体类
     * @param pageNum 页码
     * @param pageSize 页数
     * @return 返回状态
     */
    @GetMapping("listByCondition")
    @Override
    public Result listByCondition(Entity entity, Integer pageNum, Integer pageSize) {
        return null;
    }
}
