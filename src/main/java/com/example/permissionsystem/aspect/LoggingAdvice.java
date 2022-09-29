package com.example.permissionsystem.aspect;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
//@Aspect
public class LoggingAdvice {
    private Logger logger =  LoggerFactory.getLogger(LoggingAdvice.class);

    @Pointcut(value = "execution(* com.example.permissionsystem.controller.*.*(..))")
    public void myPointCut(){

    }


    @Around("myPointCut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable{ // ProceedingJoinPoint 环绕通知可以 其他用Joinpoint
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        ObjectMapper objectMapper = new ObjectMapper();
        Object[] array = pjp.getArgs();
        logger.info("调用前:"+className+":"+methodName+" args= "+objectMapper.writeValueAsString(array));
        Object proceed = pjp.proceed();
        logger.info("调用后："+className+ " :"+methodName+" object="+objectMapper.writeValueAsString(proceed));
        return proceed;
    }
}
