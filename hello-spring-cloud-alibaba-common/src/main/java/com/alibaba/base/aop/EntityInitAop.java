package com.alibaba.base.aop;

import com.alibaba.base.entity.Entity;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Stopwatch;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @author WangYifei
 * @date 2020-12-28 10:27
 * @describe
 */
@Component
@Aspect
@Slf4j
public class EntityInitAop {
    @Autowired private StringRedisTemplate redisTemplate;

    /**
     * @Description: 定义需要拦截的切面
     * @Pointcut("execution(* com.*.controller.*Controller.*(..))")
     * @Return: void
     **/
    @Pointcut("@annotation(com.alibaba.base.aop.annotation.InitEntity)")
    public void methodArgs() {

    }

    @Before("methodArgs()")
    public void begin(JoinPoint joinPoint) throws Throwable {
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 如果有session则返回session如果没有则返回null(避免创建过多的session浪费内存)
        /*HttpSession session = request.getSession(false);
        // 获取请求头
        Enumeration<String> enumeration = request.getHeaderNames();*/
        // 创建人以及 修改人可以使用redis
        String s = redisTemplate.opsForValue().get(request.getHeader("token"));


        Object[] args = joinPoint.getArgs();
        Date date = new Date();
        for (Object arg : args) {
            if (arg.getClass().getSuperclass() == Entity.class) {
                Entity entity = (Entity) arg;
                if (StringUtils.isEmpty(entity.getId())) {
                    // 新增的
                    entity.setId(UUID.randomUUID().toString().replace("-", ""));
                    entity.setCreateTime(date);
                    entity.setDelFlag(0);
                    entity.setUpdateTime(date);
                    // entity.setCreateUserId();
                } else {
                    entity.setUpdateTime(date);
                    // 修改 更新时间
                }
                break;
            }
        }
        log.info("初始化结束:{}", joinPoint);
    }

    @Around("methodArgs()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;

        Stopwatch stopwatch = Stopwatch.createStarted();
        result = joinPoint.proceed();
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // ip地址
            String ipAddr = getRemoteHost(request);
            // 请求路径
            String requestUrl = request.getRequestURL().toString();

            // 获取请求参数进行打印
            Signature signature = joinPoint.getSignature();
            MethodSignature methodSignature = (MethodSignature) signature;

            // 类名
            // swagger中文注释名
            // String classCommentName = methodSignature.getMethod().getDeclaringClass().getAnnotation(Api.class).tags()[0];
            // String[] sourceName = signature.getDeclaringTypeName().split("\\.");
            // String className = sourceName[sourceName.length - 1] + "[" + classCommentName +"]";

            // 方法名
            // swagger中文注释名
            /*String methodCommentName = methodSignature.getMethod().getAnnotation(ApiOperation.class).value();
            String methodName = signature.getName() + "[" + methodCommentName + "]";*/

            // 参数名数组
            String[] parameterNames = ((MethodSignature) signature).getParameterNames();
            // 构造参数组集合
            List<Object> argList = new ArrayList<>();
            // updateData(joinPoint.getArgs());
            for (Object arg : joinPoint.getArgs()) {
                System.out.println("arg" + arg);
                // request/response无法使用toJSON
                if (arg instanceof HttpServletRequest) {
                    argList.add("request");
                } else if (arg instanceof HttpServletResponse) {
                    argList.add("response");
                } else {
                    // updateData(arg);
                    argList.add(JSON.toJSON(arg));
                }
            }

            stopwatch.stop();
            long timeConsuming = stopwatch.elapsed(TimeUnit.MILLISECONDS);

            log.error("请求源IP【{}】 -> 请求URL【{}】\n{} -> {} -> 请求耗时：[{}]毫秒 \n请求参数：{} -> {}\n请求结果：{}",
                    ipAddr, requestUrl,
                    "className", "methodName", timeConsuming,
                    JSON.toJSON(parameterNames), JSON.toJSON(argList),
                    JSON.toJSON(result));
        } catch (Exception e) {
            log.error("参数获取失败: {}", e.getMessage());
        }

        return result;
    }

    private void updateData(Object data) {
        Entity entity = (Entity) data;
        entity.setId("5555");
    }

    /**
     * 获取目标主机的ip
     * @param request
     * @return
     */
    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.contains("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }
}
