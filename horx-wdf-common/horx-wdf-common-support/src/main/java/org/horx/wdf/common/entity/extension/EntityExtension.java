package org.horx.wdf.common.entity.extension;

import org.horx.wdf.common.filed.enhancer.AssignmentEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 * 实体对象扩展工具。
 * @since 1.0
 */
public class EntityExtension {

    private Map<String, String> extendMap;
    private Map<Class<?>, Class<?>> clzExtendMap;

    /**
     * 设置类扩展规则。
     * @param extendMap key为原class，value为扩展后的class。
     */
    public void setExtendMap(Map<String, String> extendMap) {
        this.extendMap = extendMap;

        if (extendMap == null) {
            return;
        }

        clzExtendMap = new HashMap<>(extendMap.size());

        for (Map.Entry<String, String> entry : extendMap.entrySet()) {
            Class<?> keyClass = null;
            try {
                keyClass = Class.forName(entry.getKey());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("解析class：" + entry.getKey() + "失败");
            }

            Class<?> valueClass = null;
            try {
                valueClass = Class.forName(entry.getValue());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("解析class：" + entry.getValue() + "失败");
            }

            clzExtendMap.put(keyClass, valueClass);
        }
    }

    /**
     * 获取扩展后的class。
     * @param clazz 原class。
     * @return 扩展后的class。
     */
    public Class<?> getExtensionClass(Class<?> clazz) {
        Class<?> result = (clzExtendMap == null) ? null : clzExtendMap.get(clazz);
        if (result == null) {
            result = clazz;
        }
        return result;
    }

    /**
     * 获取实例。
     * @param clazz 对象的class。
     * @param <T> 对象类型。
     * @return 扩展后的实例。如果没有扩展class，为原class的实例。
     */
    public <T> T newEntity(Class<T> clazz) {
        Class<?> subClazz = (clzExtendMap == null) ? null : clzExtendMap.get(clazz);
        if (subClazz == null) {
            subClazz = clazz;
        }
        try {
            return (T)subClazz.newInstance();
        } catch (Exception e) {
            throw new RuntimeException("生成" + clazz.getName() + "的扩展示例" + subClazz.getName() + "失败", e);
        }
    }

    /**
     * 获取实例。
     * @param clazz 对象的class。
     * @param assignmentEnhanced 石头进行赋值增强，记录人赋值状态。
     * @param <T> 对象类型。
     * @return 扩展后的实例。如果没有扩展class，为原class的实例。
     */
    public <T> T newEntity(Class<T> clazz, boolean assignmentEnhanced) {
        if (!assignmentEnhanced) {
            return newEntity(clazz);
        }
        Class<?> subClazz = (clzExtendMap == null) ? null : clzExtendMap.get(clazz);
        if (subClazz == null) {
            subClazz = clazz;
        }

        return (T)AssignmentEnhancer.newEntity(subClazz);
    }
}
