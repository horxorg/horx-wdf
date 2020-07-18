package org.horx.wdf.common.filed.enhancer;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.horx.wdf.common.field.FieldAssignment;

/**
 * 对象的赋值增强。
 * @since 1.0
 */
public class AssignmentEnhancer {

    /**
     * 获取增强后的对象实例。
     * @param clz 类名。
     * @param <T> 对象类型。
     * @return 增强后的对象实例。
     */
    public static <T> T newEntity(Class<T> clz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clz);
        enhancer.setInterfaces(new Class[]{FieldAssignment.class});
        enhancer.setCallback(new AssignmentInterceptor());
        T result = (T)enhancer.create();
        return result;
    }

    /**
     * 对象方法拦截器。
     */
    static class AssignmentInterceptor implements MethodInterceptor {

        private Set<String> fieldValueSet = new HashSet<String>();

        @Override
        public Object intercept(Object obj, Method method, Object[] args,
                                MethodProxy methodproxy) throws Throwable {
            String methodName = method.getName();
            if (methodName.equals("isFieldAssigned")) {
                return fieldValueSet.contains(args[0]);
            } else if (methodName.startsWith("set")) {
                StringBuilder builder = new StringBuilder();
                builder.append(Character.toLowerCase(methodName.charAt(3)));
                if (methodName.length() > 4) {
                    builder.append(methodName.substring(4));
                }
                fieldValueSet.add(builder.toString());
            }
            return methodproxy.invokeSuper(obj, args);
        }
    }
}
