package org.horx.wdf.common.filed.sort;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.filed.sort.annotation.CustomSortDef;
import org.horx.wdf.common.filed.sort.generator.CustomSortGenerator;
import org.horx.wdf.common.spring.SpringContext;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义排序规则提供者。
 * @since 1.0
 */
public class CustomSortProvider {

    private Map<CustomSortDef, CustomSortGenerator> customSortGeneratorMap = new ConcurrentHashMap<>();

    /**
     * 获取自定义排序规则。
     * @param fieldMeta 属性元数据。
     * @return 自定义排序规则。
     */
    public CustomSort genCustomSort(EntityMeta.FieldMeta fieldMeta) {
        Annotation anno = fieldMeta.getCustomSortDef();
        CustomSortDef customSortDef = anno.annotationType().getAnnotation(CustomSortDef.class);
        if (customSortDef == null) {
            return null;
        }

        CustomSortGenerator generator = getGenerator(customSortDef);
        if (generator == null) {
            return null;
        }

        CustomSort customSort = generator.getCustomSort(fieldMeta);
        return customSort;
    }

    private CustomSortGenerator getGenerator(CustomSortDef customSortDef) {
        boolean isSingleton = customSortDef.isSingleton();

        if (!isSingleton) {
            return getGeneratorInstance(customSortDef);
        }

        CustomSortGenerator customSortGenerator = customSortGeneratorMap.get(customSortDef);
        if (customSortGenerator == null) {
            synchronized (customSortDef) {
                customSortGenerator = customSortGeneratorMap.get(customSortDef);
                if (customSortGenerator == null) {
                    customSortGenerator = getGeneratorInstance(customSortDef);
                    customSortGeneratorMap.put(customSortDef, customSortGenerator);
                }
            }
        }

        return customSortGenerator;
    }

    private CustomSortGenerator getGeneratorInstance(CustomSortDef customSortDef) {
        String beanId = customSortDef.beanId();
        if (StringUtils.isNotEmpty(beanId)) {
            Object bean = SpringContext.getBean(beanId);
            if (bean != null) {
                if (bean instanceof CustomSortGenerator) {
                    return (CustomSortGenerator)bean;
                }
                throw new RuntimeException("beanId:" + beanId + " 不是" + CustomSortGenerator.class.getName() + "的实例");
            }
        }

        Class<?> generatorClass = customSortDef.generatorClass();

        Object bean = SpringContext.getBean(customSortDef.generatorClass());
        if (bean != null) {
            return (CustomSortGenerator)bean;
        }

        try {
            return (CustomSortGenerator)generatorClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("生成" + generatorClass.getName() + "的实例失败");
        }
    }
}
