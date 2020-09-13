package org.horx.wdf.common.filed.value;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.filed.value.annotation.ValueDef;
import org.horx.wdf.common.filed.value.generator.ValueGenerator;
import org.horx.wdf.common.spring.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 属性赋值提供者。
 * @since 1.0
 */
public class ValueProvider {
    private final static Logger logger = LoggerFactory.getLogger(ValueProvider.class);

    private Map<ValueDef, ValueGenerator> valueGeneratorMap = new ConcurrentHashMap<>();

    /**
     * 生成属性的值。
     * @param entity 对象。
     * @param groups 需赋值属性对应的group。
     */
    public void genFieldValue(Object entity, Class<?>[] groups) {
        if (entity == null) {
            return;
        }

        EntityMeta entityMeta = MetaUtils.getEntityInfo(entity.getClass());
        genFieldValue(entity, entityMeta, groups);
    }

    /**
     * 生成属性的值。
     * @param entity 对象。
     * @param entityMeta 对象元数据。
     * @param groups 需赋值属性对应的group。
     */
    public void genFieldValue(Object entity, EntityMeta entityMeta, Class<?>[] groups) {
        if (entity == null || entityMeta == null) {
            return;
        }

        EntityMeta.FieldMeta[] fieldMetas = entityMeta.getFieldMetas();
        if (fieldMetas == null || fieldMetas.length == 0) {
            return;
        }

        ValueContext valueContext = new ValueContext(entity, entityMeta, groups);
        for (EntityMeta.FieldMeta fieldMeta : fieldMetas) {
            Annotation[] valueDefs = fieldMeta.getValueDefs();
            if (valueDefs == null || valueDefs.length == 0) {
                continue;
            }

            try {
                Object currentValue = MetaUtils.getValue(entity, fieldMeta);
                if (currentValue != null) {
                    continue;
                }
            } catch (Exception e) {
                logger.warn("获取对象属性值异常", e);
            }


            Annotation annoHit = null;
            for (Annotation anno : valueDefs) {
                Class<?>[] annoGroups = getGroups(anno);
                boolean hit = checkGroup(groups, annoGroups);
                if (hit) {
                    annoHit = anno;
                    break;
                }
            }

            if (annoHit == null) {
                continue;
            }

            ValueDef valueDef = annoHit.annotationType().getAnnotation(ValueDef.class);
            ValueGenerator valueGenerator = getValueGenerator(valueDef);
            Field field = fieldMeta.getField();

            Object value = valueGenerator.value(annoHit, field, valueContext);

            if (value != null) {
                value = valueContext.convertSimpleType(value, field);
            }

            try {
                MetaUtils.setValue(entity, fieldMeta, value);
            } catch (Exception e) {
                Class<?> originalClass = MetaUtils.getOriginalClass(entity.getClass());
                throw new RuntimeException("设置" +  originalClass.getName() + "的" + field.getName() + "属性错误, value=" + value, e);
            }
        }
    }

    private Class<?>[] getGroups(Annotation anno) {
        Class<?>[] result = null;
        Method[] methods = anno.annotationType().getDeclaredMethods();
        for(Method method : methods) {
            if ("groups".equals(method.getName())) {
                try {
                    result = (Class<?>[]) (method.invoke(anno));
                } catch (Exception e) {
                    logger.warn(e.getMessage(), e);
                }
            }
        }

        return result;
    }

    private boolean checkGroup(Class<?>[] groups, Class<?>[] annoGroups) {
        if (groups == null || groups.length == 0) {
            return true;
        }

        if (annoGroups == null || annoGroups.length == 0) {
            return false;
        }

        boolean find = false;
        for (Class<?> groupClass : groups) {
            for (Class<?> annoGroup : annoGroups) {
                if (groupClass.equals(annoGroup)) {
                    find = true;
                    break;
                }
            }
            if (find) {
                break;
            }
        }

        return find;
    }

    private ValueGenerator getValueGenerator(ValueDef valueDef) {
        boolean isSingleton = valueDef.isSingleton();

        if (!isSingleton) {
            return getValueGeneratorInstance(valueDef);
        }

        ValueGenerator valueGenerator = valueGeneratorMap.get(valueDef);
        if (valueGenerator == null) {
            synchronized (valueDef) {
                valueGenerator = valueGeneratorMap.get(valueDef);
                if (valueGenerator == null) {
                    valueGenerator = getValueGeneratorInstance(valueDef);
                    valueGeneratorMap.put(valueDef, valueGenerator);
                }
            }
        }

        return valueGenerator;
    }

    private ValueGenerator getValueGeneratorInstance(ValueDef valueDef) {
        String beanId = valueDef.beanId();
        if (StringUtils.isNotEmpty(beanId)) {
            Object bean = SpringContext.getBean(beanId);
            if (bean != null) {
                if (bean instanceof ValueGenerator) {
                    return (ValueGenerator)bean;
                }
                throw new RuntimeException("beanId:" + beanId + " 不是" + ValueGenerator.class.getName() + "的实例");
            }
        }

        Class<?> generatorClass = valueDef.generatorClass();

        Object bean = SpringContext.getBean(valueDef.generatorClass());
        if (bean != null) {
            return (ValueGenerator)bean;
        }

        try {
            return (ValueGenerator)generatorClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("生成" + generatorClass.getName() + "的实例失败");
        }
    }
}
