package org.horx.wdf.springboot.config;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.horx.wdf.sys.support.datalog.DataLogAdvice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Order(2)
@Component
public class DataLogAop {

    @Autowired
    private DataLogAdvice dataLogAdvice;

    @Pointcut("execution(* org.horx.wdf.sys.manager..*.*(..)) && @annotation(org.horx.wdf.sys.support.datalog.DataLog)")
    private void dataLogPointcut() {

    }

    @Around("dataLogPointcut()")
    public void dataLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        dataLogAdvice.doAround(joinPoint);
    }
}
