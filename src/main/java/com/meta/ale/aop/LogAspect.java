package com.meta.ale.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class LogAspect {

    @Around("execution(* com..service.*.*(..))")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {

        Object obj = pjp.proceed();
        log.info(" Call by : " + pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName());

        return obj;
    }
}
