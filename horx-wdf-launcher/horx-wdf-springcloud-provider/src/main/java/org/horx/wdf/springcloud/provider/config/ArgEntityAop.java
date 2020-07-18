package org.horx.wdf.springcloud.provider.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.horx.wdf.common.spring.advice.ArgEntityAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ArgEntityAop {

    @Autowired
    private ArgEntityAdvice argEntityAdvice;

    @Pointcut("execution(* org.horx.wdf.sys.service..*.*(..)) || execution(* org.horx.wdf.sys.mapper.*.*(..))")
    private void argEntityPointcut() {

    }

    @Before("argEntityPointcut()")
    public void argEntityBefore(JoinPoint joinPoint) {
        argEntityAdvice.doBefore(joinPoint);
    }
}
