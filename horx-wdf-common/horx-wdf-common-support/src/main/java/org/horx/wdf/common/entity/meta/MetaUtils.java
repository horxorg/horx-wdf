package org.horx.wdf.common.entity.meta;

import net.sf.cglib.proxy.Enhancer;
import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.filed.sort.annotation.CustomSortDef;
import org.horx.wdf.common.filed.value.annotation.ValueDef;
import org.horx.common.utils.ReflectUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 元数据工具类。
 * @since 1.0
 */
public final class MetaUtils {

    private static final Map<Class<?>, EntityMeta> entityMap = new ConcurrentHashMap<>();

    private MetaUtils() {}

    /**
     * 获取增强前的原始累。
     * @param clazz
     * @return
     */
    public static Class<?> getOriginalClass(Class<?> clazz) {
        Class<?> originalClass = clazz;
        if (Enhancer.isEnhanced(clazz)) {
            originalClass = clazz.getSuperclass();
        }
        return originalClass;
    }

    /**
     * 获取类的元数据。
     * @param clazz
     * @return
     */
    public static EntityMeta getEntityInfo(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        Class<?> originalClass = getOriginalClass(clazz);

        EntityMeta entityMeta = entityMap.get(originalClass);
        if (entityMeta == null) {
            synchronized (MetaUtils.class) {
                entityMeta = entityMap.get(originalClass);
                if (entityMeta == null) {
                    entityMeta = generateEntityInfo(originalClass);
                    entityMap.put(originalClass, entityMeta);
                }
            }
        }

        return entityMeta;
    }

    /**
     * 获取属性值。
     * @param bean 对象。
     * @param fieldMeta 属性元数据。
     * @return 属性值。
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static Object getValue(Object bean, EntityMeta.FieldMeta fieldMeta) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method method = fieldMeta.getGetterMethod();
        Object result = null;
        if (method != null) {
            result = method.invoke(bean);
        } else {
            Field field = fieldMeta.getField();
            result = ReflectUtils.getValue(bean, field);
        }

        return result;
    }

    /**
     * 设置属性值。
     * @param bean 对象。
     * @param fieldMeta 属性元数据。
     * @param value 属性值。
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws NoSuchFieldException
     */
    public static void setValue(Object bean, EntityMeta.FieldMeta fieldMeta, Object value) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException {
        Method method = fieldMeta.getSetterMethod();

        if (method != null) {
            method.invoke(bean, value);
        } else {
            Field field = fieldMeta.getField();
            ReflectUtils.setValue(bean, field, value);
        }

    }

    private static EntityMeta generateEntityInfo(Class<?> clazz) {
        EntityMeta entityMeta = new EntityMeta();

        List<Field> fieldList = new ArrayList<>();
        List<Method> getterMethodList = new ArrayList<>() ;
        List<Method> setterMethodList = new ArrayList<>();
        getMetaInfo(clazz, fieldList, getterMethodList, setterMethodList, entityMeta);

        List<EntityMeta.FieldMeta> fieldMetaList = new ArrayList<>(fieldList.size());
        for (Field field : fieldList) {
            EntityMeta.FieldMeta fieldMeta = new EntityMeta.FieldMeta();

            String fieldName = field.getName();
            Column columnAno = field.getAnnotation(Column.class);
            if (columnAno != null || field.isAnnotationPresent(Id.class)) {
                String columnName = (columnAno == null) ? null : columnAno.name();
                if (StringUtils.isEmpty(columnName)) {
                    columnName = org.horx.common.utils.StringUtils.camelToUnderLine(fieldName);
                }
                fieldMeta.setColumnName(columnName);
            }

            fieldMeta.setField(field);

            Method getterMethod = getGetterMethod(field, getterMethodList);
            fieldMeta.setGetterMethod(getterMethod);

            Method setterMethod = getSetterMethod(field, setterMethodList);
            fieldMeta.setSetterMethod(setterMethod);

            Annotation[] annoArr = field.getDeclaredAnnotations();

            Annotation[] valueDefs = parseValueDefs(annoArr);
            fieldMeta.setValueDefs(valueDefs);

            Annotation customSortDef = parseCustomSortDef(annoArr);
            fieldMeta.setCustomSortDef(customSortDef);

            fieldMetaList.add(fieldMeta);

            if (entityMeta.getIdFieldMeta() == null && field.getAnnotation(Id.class) != null) {
                entityMeta.setIdFieldMeta(fieldMeta);
            }
        }


        entityMeta.setFieldMetas(fieldMetaList.toArray(new EntityMeta.FieldMeta[0]));

        for (EntityMeta.FieldMeta fieldMeta : fieldMetaList) {
            Field field = fieldMeta.getField();


            if (field.getAnnotation(Id.class) != null) {
                entityMeta.setIdFieldMeta(fieldMeta);
                break;
            }
        }

        return entityMeta;
    }

    private static void getMetaInfo(Class<?> clazz, List<Field> fieldList, List<Method> getterMethodList, List<Method> setterMethodList, EntityMeta entityMeta) {
        if (entityMeta.getTableName() == null) {
            Table tableAno = clazz.getAnnotation(Table.class);
            if (tableAno != null) {
                String tableName = tableAno.name();
                if (StringUtils.isEmpty(tableName)) {
                    tableName = org.horx.common.utils.StringUtils.camelToUnderLine(clazz.getSimpleName());
                }

                entityMeta.setTableName(tableName);
            }
        }

        int fieldListLen = fieldList.size();
        Field[] beanFields = clazz.getDeclaredFields();
        for (Field field : beanFields) {
            if ((field.getModifiers() & Modifier.STATIC) != 0) {
                continue;
            }
            if (fieldListLen > 0) {
                boolean exists = false;
                for (int i = 0; i < fieldListLen; i++) {
                    if (field.getName().equals(fieldList.get(i).getName())) {
                        exists = true;
                        break;
                    }
                }

                if (exists) {
                    continue;
                }
            }

            fieldList.add(field);
        }

        int getterLen = getterMethodList.size();
        int setterLen = setterMethodList.size();
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            int modifiers = method.getModifiers();
            if ((modifiers & Modifier.STATIC) != 0 || (modifiers & Modifier.PUBLIC) == 0) {
                continue;
            }

            String methodName = method.getName();
            Class<?> r = method.getReturnType();
            if ((methodName.startsWith("get") || methodName.startsWith("is")) && method.getParameterTypes().length == 0
                && !method.getReturnType().equals(Void.TYPE)) {
                boolean exists = false;
                if (getterLen > 0) {
                    for (int i = 0; i < getterLen; i++) {
                        if (method.getName().equals(getterMethodList.get(i).getName())) {
                            exists = true;
                            break;
                        }
                    }
                }
                if (!exists) {
                    getterMethodList.add(method);
                }
            } else if (methodName.startsWith("set")  && method.getParameterTypes().length == 1
                && method.getReturnType().equals(Void.TYPE)) {
                boolean exists = false;
                if (setterLen > 0) {
                    for (int i = 0; i < setterLen; i++) {
                        if (method.getName().equals(setterMethodList.get(i).getName())) {
                            exists = true;
                            break;
                        }
                    }
                }
                if (!exists) {
                    setterMethodList.add(method);
                }
            }
        }


        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null && !superClass.equals(Object.class)) {
            getMetaInfo(superClass, fieldList, getterMethodList, setterMethodList, entityMeta);
        }
    }

    private static Method getGetterMethod(Field field, List<Method> methodList) {
        String fieldName = field.getName();
        Method result = null;
        if (field.getType().isAssignableFrom(boolean.class) || field.getType().isAssignableFrom(Boolean.class)) {
            if (fieldName.startsWith("is")) {
                result = getGetterMethod(fieldName, field.getType(), methodList);
            } else {
                String methodName = "is" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
                result = getGetterMethod(methodName, field.getType(), methodList);
            }
        }

        if (result == null) {
            String methodName = "get" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
            result = getGetterMethod(methodName, field.getType(), methodList);
        }

        return result;
    }

    private static Method getGetterMethod(String methodName, Class<?> returnType, List<Method> methodList) {
        for (Method method : methodList) {
            if (methodName.equals(method.getName()) && returnType.equals(method.getReturnType())) {
                return method;
            }
        }
        return null;
    }

    private static Method getSetterMethod(Field field, List<Method> methodList) {
        String fieldName = field.getName();
        Method result = null;
        if (field.getType().isAssignableFrom(boolean.class) || field.getType().isAssignableFrom(Boolean.class)) {
            if (fieldName.startsWith("is")) {
                String methodName = "set" + String.valueOf(fieldName.charAt(2)).toUpperCase() + fieldName.substring(3);
                result = getSetterMethod(methodName, field.getType(), methodList);
            }
        }

        if (result == null) {
            String methodName = "set" + String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1);
            result = getSetterMethod(methodName, field.getType(), methodList);
        }

        return result;
    }

    private static Method getSetterMethod(String methodName, Class<?> paramType, List<Method> methodList) {
        for (Method method : methodList) {
            if (methodName.equals(method.getName()) && paramType.equals(method.getParameterTypes()[0])) {
                return method;
            }
        }
        return null;
    }

    private static Annotation[] parseValueDefs(Annotation[] annoArr) {
        List<Annotation> valueDefList = new ArrayList<>();

        for (Annotation anno : annoArr) {
            if (anno.annotationType().isAnnotationPresent(ValueDef.class)) {
                valueDefList.add(anno);
                continue;
            }

            try {
                Method[] methods = anno.annotationType().getDeclaredMethods();
                Method method = null;
                for (Method m : methods) {
                    if ("value".equals(m.getName())) {
                        method = m;
                        break;
                    }
                }

                if (method != null) {
                    Object obj = method.invoke(anno);
                    if (obj != null && obj.getClass().isArray()) {
                        int len = Array.getLength(obj);
                        for (int i = 0; i< len; i++) {
                            Object item = Array.get(obj, i);
                            if (item instanceof Annotation) {
                                Annotation a = (Annotation)item;
                                if (a.annotationType().isAnnotationPresent(ValueDef.class)) {
                                    valueDefList.add(anno);
                                }
                            }

                        }
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException("annotation reflect error", e);
            }

        }

        return valueDefList.toArray(new Annotation[0]);
    }

    private static Annotation parseCustomSortDef(Annotation[] annoArr) {
        for (Annotation anno : annoArr) {
            if (anno.annotationType().isAnnotationPresent(CustomSortDef.class)) {
                return anno;
            }
        }
        return null;
    }
}
