package org.horx.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 反射工具类。
 * @since 1.0
 */
public final class ReflectUtils {
    private static final Map<Class<?>, Field[]> fieldsMap = new ConcurrentHashMap<>();

    private ReflectUtils() {}

    /**
     * 获取obj对象fieldName的Field。
     *
     * @param obj 对象。
     * @param fieldName 属性名。
     * @return
     */
    public static Field getField(Object obj, String fieldName) {
        for (Class<?> superClass = obj.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
            }
        }
        return null;
    }

    /**
     * 获取累的所有属性。
     * @param clz
     * @return
     */
    public static Field[] getFields(Class<?> clz) {
        if (clz == null) {
            return new Field[0];
        }

        Field[] fields = fieldsMap.get(clz);
        if (fields != null) {
            return fields;
        }

        synchronized (ReflectUtils.class) {
            fields = fieldsMap.get(clz);
            if (fields != null) {
                return fields;
            }

            Class<?> temp = clz;
            List<Field> list = new ArrayList<>();
            while (temp != null && !temp.equals(Object.class)) {
                Field[] arr = temp.getDeclaredFields();
                int listLen = list.size();

                for (Field f : arr) {
                    if (listLen > 0) {
                        boolean exists = false;
                        for (int i = 0; i < listLen; i++) {
                            if (f.equals(list.get(i))) {
                                exists = true;
                                break;
                            }
                        }

                        if (exists) {
                            continue;
                        }
                    }

                    list.add(f);
                }

                temp = temp.getSuperclass();
            }

            Field[] result = list.toArray(new Field[0]);
            fieldsMap.put(clz, result);
            return result;
        }
    }

    /**
     * 获取obj对象fieldName的属性值。
     *
     * @param obj
     * @param fieldName
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValue(Object obj, String fieldName) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getField(obj, fieldName);
        return getValue(obj, field);
    }

    /**
     * 获取obj对象的属性值。
     * @param obj
     * @param field
     * @return
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static Object getValue(Object obj, Field field) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Object value = null;
        if (field != null) {
            if (field.isAccessible()) {
                value = field.get(obj);
            } else {
                field.setAccessible(true);
                value = field.get(obj);
                field.setAccessible(false);
            }
        }
        return value;
    }

    /**
     * 设置obj对象fieldName的属性值。
     *
     * @param obj
     * @param fieldName
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValue(Object obj, String fieldName, Object value) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        Field field = getField(obj, fieldName);
        setValue(obj, field, value);
    }

    /**
     * 设置obj对象fieldName的属性值。
     * @param obj
     * @param field
     * @param value
     * @throws SecurityException
     * @throws NoSuchFieldException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setValue(Object obj, Field field, Object value) throws SecurityException,
            NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
        if (field.isAccessible()) {
            field.set(obj, value);
        } else {
            field.setAccessible(true);
            field.set(obj, value);
            field.setAccessible(false);
        }
    }

    /**
     * 获取属性所对应的setter方法。
     * @param cls
     * @param field
     * @return
     */
    public static Method getSetterMethod(Class<?> cls, Field field) {
        String fieldName = field.getName();
        String methodName = "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
        Method method = null;
        try {
            method = cls.getDeclaredMethod(methodName, field.getType());
        } catch (Exception e) {

        }
        return method;
    }

    /**
     * 获取接口的泛型Class。
     * @param clazz
     * @param index
     * @return
     */
    public static Class<?> getParameterizedClassFromInterface(Class<?> clazz, int index) {
        Type[] types = clazz.getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[index];
        Type type = parameterizedType.getActualTypeArguments()[index];
        return checkType(type, index);
    }

    /**
     * 获取类的泛型Class。
     * @param clazz
     * @param index
     * @return
     */
    public static Class<?> getParameterizedClassFromClass(Class<?> clazz, int index) {
        Type type = clazz.getGenericSuperclass();
        return checkType(type, index);
    }

    private static Class<?> checkType(Type type, int index) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type t = pt.getActualTypeArguments()[index];
            return checkType(t, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                    + ", but <" + type + "> is of type " + className);
        }
    }
}
