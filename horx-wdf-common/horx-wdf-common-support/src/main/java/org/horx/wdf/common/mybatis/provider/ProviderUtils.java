package org.horx.wdf.common.mybatis.provider;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.horx.common.utils.ReflectUtils;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.field.FieldAssignment;
import org.horx.wdf.common.jdbc.annotation.EntityClass;
import org.horx.wdf.common.mybatis.value.annotation.Deleted;
import org.springframework.beans.SimpleTypeConverter;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * provider工具类。
 * @since 1.0
 */
final class ProviderUtils {

    /**
     * 获取实体所对应的class。
     * @param context
     * @return
     */
    static Class<?> getEntityClass(ProviderContext context) {
        Method method = context.getMapperMethod();
        EntityClass ecAno = method.getAnnotation(EntityClass.class);
        if (ecAno != null) {
            Class<?> cls = ecAno.cls();
            if (cls != null) {
                return cls;
            }
        }

        Class<?> mapperClass = context.getMapperType();
        ecAno = mapperClass.getAnnotation(EntityClass.class);
        if (ecAno != null) {
            Class<?> cls = ecAno.cls();
            if (cls != null) {
                return cls;
            }
        }
        Class<?> cls = ReflectUtils.getParameterizedClassFromInterface(mapperClass, 0);
        return cls;
    }

    static String genUpdateSql(EntityMeta entityMeta, Object entity, String paramPrefix, Map param) {
        StringBuilder sql = new StringBuilder();
        StringBuilder where = new StringBuilder();

        FieldAssignment fieldAssignment = null;
        if (entity instanceof FieldAssignment) {
            fieldAssignment = (FieldAssignment)entity;
        }

        EntityMeta.FieldMeta[] fieldMetas = entityMeta.getFieldMetas();

        if (fieldMetas != null) {
            for (EntityMeta.FieldMeta fieldMeta : fieldMetas) {
                if (fieldMeta.getColumnName() == null) {
                    continue;
                }
                Field field = fieldMeta.getField();
                Id idAno = field.getAnnotation(Id.class);
                if (idAno == null && field.getAnnotation(Transient.class) != null || fieldAssignment != null && !fieldAssignment.isFieldAssigned(field.getName())) {
                    continue;
                }

                try {
                    Object value = MetaUtils.getValue(entity, fieldMeta);

                    if (idAno != null) {
                        if (where.length() > 0) {
                            where.append(" AND ");
                        }
                        where.append(fieldMeta.getColumnName()).append("=#{").append(paramPrefix).append(field.getName()).append("}");
                    } else if (value != null || fieldAssignment != null && fieldAssignment.isFieldAssigned(field.getName())) {
                        if (sql.length() > 0) {
                            sql.append(",");
                        }
                        if (value != null) {
                            sql.append(fieldMeta.getColumnName()).append("=#{").append(paramPrefix).append(field.getName()).append("}");
                        } else {
                            sql.append(fieldMeta.getColumnName()).append("=null");
                        }
                    }

                } catch (Exception e) {
                    throw new RuntimeException("获取" + entity.getClass().getName() + "的" + field.getName() + "属性失败", e);
                }
            }
        }

        if (where.length() == 0) {
            throw new RuntimeException(entity.getClass().getName() + ":update has no where clause");
        }

        if (sql.length() == 0) {
            return null;
        }

        genDeletedCondition(entityMeta, where, param, true);

        sql.append(" WHERE ").append(where);
        where.setLength(0);
        where.append("UPDATE ").append(entityMeta.getTableName()).append(" SET ");
        sql.insert(0, where);

        return sql.toString();
    }

    static DeletedWrapper getNotDeletedValue(EntityMeta entityMeta) {
        EntityMeta.FieldMeta[] fieldMetas = entityMeta.getFieldMetas();
        if (fieldMetas == null || fieldMetas.length == 0) {
            return null;
        }

        for (EntityMeta.FieldMeta fieldMeta : fieldMetas) {
            Annotation[] annos = fieldMeta.getValueDefs();
            if (annos == null || annos.length == 0) {
                continue;
            }
            for (Annotation anno : annos) {
                if (anno instanceof Deleted) {
                    Deleted deleted = (Deleted)anno;
                    String str = deleted.notDeletedValue();
                    SimpleTypeConverter typeConverter = new SimpleTypeConverter();
                    DeletedWrapper deletedWrapper = new DeletedWrapper();
                    deletedWrapper.setDeletedField(fieldMeta);
                    deletedWrapper.setNotDeletedValueStr(str);
                    deletedWrapper.setNotDeletedValue(typeConverter.convertIfNecessary(str, fieldMeta.getField().getType()));
                    return deletedWrapper;
                }
            }
        }

        return null;
    }

    static void genSelectField(EntityMeta.FieldMeta[] fieldMetas, StringBuilder sql) {
        if (fieldMetas != null) {
            int cols = 0;
            for (EntityMeta.FieldMeta fieldMeta : fieldMetas) {
                if (fieldMeta.getColumnName() == null || fieldMeta.getField().getAnnotation(Transient.class) != null) {
                    continue;
                }
                if (cols > 0) {
                    sql.append(',');
                }
                sql.append(fieldMeta.getColumnName());
                cols++;
            }
        }
    }

    static void genDeletedCondition(EntityMeta entityMeta, StringBuilder sql, Map param, boolean hasCondition) {
        ProviderUtils.DeletedWrapper deletedWrapper = getNotDeletedValue(entityMeta);
        if (deletedWrapper != null && deletedWrapper.getNotDeletedValue() != null) {
            String fieldName = deletedWrapper.getDeletedField().getField().getName();
            if (hasCondition) {
                sql.append(" AND ");
            } else {
                sql.append(" WHERE ");
            }

            sql.append(deletedWrapper.getDeletedField().getColumnName());

            if (param == null) {
                sql.append("='")
                        .append(deletedWrapper.notDeletedValueStr)
                        .append("'");
            } else {
                sql.append("=#{")
                        .append(fieldName)
                        .append("}");

                param.put(fieldName, deletedWrapper.getNotDeletedValue());
            }
        }
    }

    static String getParamName(ProviderContext context) {
        return getParamName(context.getMapperMethod().getParameterAnnotations()[0]);
    }

    static String getParamName(Annotation[] annos) {
        if (annos != null && annos.length > 0) {
            for (Annotation anno : annos ) {
                if (anno instanceof Param) {
                    Param paramAnno = (Param)anno;
                    return paramAnno.value();
                }
            }
        }
        return null;
    }

    static class DeletedWrapper {
        private EntityMeta.FieldMeta deletedField;
        private String notDeletedValueStr;
        private Object notDeletedValue;

        public EntityMeta.FieldMeta getDeletedField() {
            return deletedField;
        }

        public void setDeletedField(EntityMeta.FieldMeta deletedField) {
            this.deletedField = deletedField;
        }

        public String getNotDeletedValueStr() {
            return notDeletedValueStr;
        }

        public void setNotDeletedValueStr(String notDeletedValueStr) {
            this.notDeletedValueStr = notDeletedValueStr;
        }

        public Object getNotDeletedValue() {
            return notDeletedValue;
        }

        public void setNotDeletedValue(Object notDeletedValue) {
            this.notDeletedValue = notDeletedValue;
        }
    }
}
