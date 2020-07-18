package org.horx.wdf.common.entity.meta;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 实体元数据。
 * @since 1.0
 */
public class EntityMeta {
    private String tableName;

    private FieldMeta idFieldMeta;

    private FieldMeta[] fieldMetas;

    /**
     * 获取表名。
     * @return 表名。
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * 设置表名。
     * @param tableName 表名。
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 获取id属性元数据。
     * @return id属性元数据。
     */
    public FieldMeta getIdFieldMeta() {
        return idFieldMeta;
    }

    /**
     * 设置id属性元数据。
     * @param idFieldMeta id属性元数据。
     */
    public void setIdFieldMeta(FieldMeta idFieldMeta) {
        this.idFieldMeta = idFieldMeta;
    }

    /**
     * 获取属性元数据。
     * @return 属性元数据。
     */
    public FieldMeta[] getFieldMetas() {
        return fieldMetas;
    }

    /**
     * 设置属性元数据。
     * @param fieldMetas 属性元数据。
     */
    public void setFieldMetas(FieldMeta[] fieldMetas) {
        this.fieldMetas = fieldMetas;
    }

    /**
     * 获取属性元数据。
     * @param fieldName 属性名。
     * @return 属性元数据。
     */
    public FieldMeta getFieldMeta(String fieldName) {
        if (fieldMetas == null || fieldMetas.length == 0) {
            return null;
        }

        FieldMeta fieldMeta = null;
        for (FieldMeta item : fieldMetas) {
            if (item.getField().getName().equals(fieldName)) {
                fieldMeta = item;
                break;
            }
        }

        return fieldMeta;
    }

    /**
     * 属性元数据。
     * @since 1.0
     */
    public static class FieldMeta {
        private Field field;
        private Method getterMethod;
        private Method setterMethod;
        private String columnName;
        private Annotation[] valueDefs;
        private Annotation customSortDef;

        /**
         * 获取属性。
         * @return 属性。
         */
        public Field getField() {
            return field;
        }

        /**
         * 设置属性。
         * @param field 属性。
         */
        public void setField(Field field) {
            this.field = field;
        }

        /**
         * 获取getter方法。
         * @return getter方法。
         */
        public Method getGetterMethod() {
            return getterMethod;
        }

        /**
         * 设置getter方法。
         * @param getterMethod getter方法。
         */
        public void setGetterMethod(Method getterMethod) {
            this.getterMethod = getterMethod;
        }

        /**
         * 获取setter方法。
         * @return setter方法。
         */
        public Method getSetterMethod() {
            return setterMethod;
        }

        /**
         * 设置setter方法。
         * @param setterMethod setter方法。
         */
        public void setSetterMethod(Method setterMethod) {
            this.setterMethod = setterMethod;
        }

        /**
         * 获取数据库表列名。
         * @return 数据库表列名。
         */
        public String getColumnName() {
            return columnName;
        }

        /**
         * 设置数据库表列名。
         * @param columnName 数据库表列名。
         */
        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        /**
         * 获取属性赋值定义注解。
         * @return 属性赋值定义注解。
         */
        public Annotation[] getValueDefs() {
            return valueDefs;
        }

        /**
         * 设置属性赋值定义注解。
         * @param valueDefs 属性赋值定义注解。
         */
        public void setValueDefs(Annotation[] valueDefs) {
            this.valueDefs = valueDefs;
        }

        /**
         * 获取自定义排序定义注解。
         * @return 自定义排序定义注解。
         */
        public Annotation getCustomSortDef() {
            return customSortDef;
        }

        /**
         * 设置自定义排序定义注解。
         * @param customSortDef 自定义排序定义注解。
         */
        public void setCustomSortDef(Annotation customSortDef) {
            this.customSortDef = customSortDef;
        }
    }
}
