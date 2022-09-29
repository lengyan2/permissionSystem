package com.example.permissionsystem.aspect;

import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
/**
 * 声明一个切面类
 */
public class MyLogAspect {
    private final Logger logger = LoggerFactory.getLogger(MyLogAspect.class);

    /**
     * 定义一个切点
     */
    @Pointcut("@annotation(com.example.permissionsystem.anno.MyLog) || @annotation(org.springframework.security.access.prepost.PreAuthorize)")
    public void LogPointCut(){}


    @Before("LogPointCut()")
    public void LogBefore(JoinPoint jp){
        Signature signature = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        // 获取方法
        Method method = methodSignature.getMethod();
        String methodName = method.getName(); // 获取到对应的方法名
        PreAuthorize annotation = method.getAnnotation(PreAuthorize.class);
        String value = annotation.value(); //获取到对应的preAuthorize的value值 方便做权限校验
        // 获取请求的URL 和 IP
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        logger.info("======方法名："+methodName+"==注解对应的值:"+value+"===请求的url:"+url
        +"====请求的ip:"+ip);

    }


    /**
     * 声明通知
     */
    @Around("LogPointCut()")
    public void logAround(ProceedingJoinPoint pjp) throws Throwable {
        // 获取方法名
        String methodName = pjp.getSignature().getName();

        // 获取目标对象
        String className = pjp.getTarget().getClass().toString();

        // 获取参数
        Object[] args = pjp.getArgs();

        logger.info("调用方法前：-》 调用的方法名： "+ methodName +" 目标对象是："+className +" " +
                " 参数为： "+args);
        Object proceed = pjp.proceed(); // 执行目标方法

        logger.info("调用方法后: + 获取的对象："+proceed);

    }

}
