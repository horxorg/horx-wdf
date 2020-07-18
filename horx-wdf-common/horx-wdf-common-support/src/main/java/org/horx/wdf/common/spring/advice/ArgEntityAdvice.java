package org.horx.wdf.common.spring.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.horx.wdf.common.tools.EntityTool;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 参数赋值Advice。
 * @since 1.0
 */
public class ArgEntityAdvice {

    @Autowired
    private EntityTool entityTool;

    private Set<Method> noArgEntityMehods = Collections.newSetFromMap(new ConcurrentHashMap<>());

    /**
     * 方法执行前拦截执行的方法。
     * @param joinPoint
     */
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args == null || args.length == 0) {
            return;
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        if (noArgEntityMehods.contains(method)) {
            return;
        }

        Annotation[][] annos = method.getParameterAnnotations();

        boolean hasArgEntity = false;
        for (int i = 0; i < args.length; i++) {
            Annotation[] annoArr = annos[i];
            if (annoArr.length == 0) {
                continue;
            }

            ArgEntity argEntity = null;
            for (Annotation anno : annoArr) {
                if (anno instanceof ArgEntity) {
                    argEntity = (ArgEntity)anno;
                    break;
                }
            }

            if (argEntity == null) {
                continue;
            }

            hasArgEntity = true;

            Object arg = args[i];
            if (arg == null) {
                continue;
            }

            Class<?>[] groups = entityTool.toGroups(argEntity);
            entityTool.setValue(arg, groups, argEntity.validate());
        }

        if (!hasArgEntity) {
            noArgEntityMehods.add(method);
        }
    }
}
