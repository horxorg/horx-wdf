package org.horx.wdf.common.mybatis.provider;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.builder.annotation.ProviderContext;
import org.apache.ibatis.session.RowBounds;
import org.horx.common.utils.ReflectUtils;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.enums.ComparisonOperatorEnum;
import org.horx.wdf.common.field.annotation.Condition;
import org.horx.wdf.common.jdbc.dialect.DbDialect;
import org.horx.wdf.common.spring.SpringContext;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * 查询的provider。
 * @since 1.0
 */
public class SelectSqlProvider {

    /**
     * 生成查询的sql。
     * @param param 参数。
     * @param context
     * @return
     */
    public String selectSql(Object param, ProviderContext context) {
        if (param == null) {
            return null;
        }

        Class<?> clazz = ProviderUtils.getEntityClass(context);

        EntityExtension entityExtension = SpringContext.getBean(EntityExtension.class);
        if (entityExtension != null) {
            clazz = entityExtension.getExtensionClass(clazz);
        }

        EntityMeta entityMeta = MetaUtils.getEntityInfo(clazz);
        if (entityMeta == null || entityMeta.getTableName() == null) {
            throw new RuntimeException("生成select语句失败：" + clazz.getName() + "未配置表字段映射");
        }

        EntityMeta.FieldMeta idFieldMeta =  entityMeta.getIdFieldMeta();
        if (idFieldMeta == null) {
            throw new RuntimeException("生成select语句失败：" + clazz.getName() + "未配置id字段");
        }

        StringBuilder sql = new StringBuilder("SELECT ");

        EntityMeta.FieldMeta[] fieldMetas = entityMeta.getFieldMetas();
        ProviderUtils.genSelectField(fieldMetas, sql);

        sql.append(" FROM ")
                .append(entityMeta.getTableName());

        int conditionCount = 0;
        Map paramMap = null;
        if (param instanceof Map) {
            paramMap = (Map)param;
            int pcount = context.getMapperMethod().getParameterCount();
            Annotation[][] annoArrs = context.getMapperMethod().getParameterAnnotations();
            for (int i = 0; i < pcount; i++) {
                String paramName = ProviderUtils.getParamName(annoArrs[i]);
                if (paramName == null) {
                    continue;
                }
                Object paramObj = paramMap.get(paramName);
                if (paramObj == null || paramObj instanceof RowBounds) {
                    continue;
                }
                conditionCount = genCondition(entityMeta, paramName, paramObj, sql, conditionCount);
            }
        } else {
            genCondition(entityMeta, "", param, sql, conditionCount);
        }

        ProviderUtils.genDeletedCondition(entityMeta, sql, paramMap, conditionCount > 0);

        return sql.toString();
    }

    private int genCondition(EntityMeta entityMeta, String prefix, Object paramObj, StringBuilder sql, int conditionCount) {
        if (paramObj.getClass().isPrimitive() || paramObj instanceof Number || paramObj instanceof Date ||
                paramObj instanceof Collection || paramObj.getClass().isArray()) {
            appendCondition(entityMeta, "", prefix, ComparisonOperatorEnum.EQUAL, paramObj, sql, conditionCount);
            conditionCount++;
        } else {
            Field[] fields = ReflectUtils.getFields(paramObj.getClass());
            for (Field field : fields) {
                try {
                    Object value = ReflectUtils.getValue(paramObj, field);
                    if (value == null || value instanceof String && value.toString().length() == 0) {
                        continue;
                    }
                    Condition condition = field.getAnnotation(Condition.class);
                    String fieldName = (condition == null) ? field.getName() : condition.field();
                    ComparisonOperatorEnum operator = (condition == null) ? ComparisonOperatorEnum.EQUAL : condition.operator();
                    appendCondition(entityMeta, prefix, fieldName, operator, value, sql, conditionCount);
                    conditionCount++;
                } catch (Exception e) {
                    throw new RuntimeException("反射获取值失败", e);
                }
            }
        }

        return conditionCount;
    }

    private void appendCondition(EntityMeta entityMeta, String prefix, String fieldName, ComparisonOperatorEnum operator, Object paramObj, StringBuilder sql, int conditionCount) {
        boolean isSet = false;
        if (paramObj instanceof Collection) {
            if (CollectionUtils.isEmpty((Collection)paramObj)) {
                return;
            }
            isSet = true;
        } else if (paramObj.getClass().isArray()) {
            if (Array.getLength(paramObj) == 0) {
                return;
            }
            isSet = true;
        }

        if (conditionCount == 0) {
            sql.append(" WHERE ");
        } else {
            sql.append(" AND ");
        }
        EntityMeta.FieldMeta fieldMeta = entityMeta.getFieldMeta(fieldName);

        if (isSet) {
            sql.append("<foreach  item=\"item\" collection=\"");
            if (StringUtils.isNotEmpty(prefix)) {
                sql.append(prefix).append(".");
            }
            sql.append(fieldName)
                    .append(" separator=\"OR\" open=\"(\" close=\")\">")
                    .append(fieldMeta.getColumnName())
                    .append(" ");
            sql.append(genOperator("#{item}", operator));
            sql .append("</foreach>");
        } else {
            sql.append(fieldMeta.getColumnName());
            String conditionValue = (StringUtils.isEmpty(prefix)) ? fieldName : prefix + "." + fieldName;
            sql.append(" ");
            sql.append(genOperator("#{" + conditionValue + "}", operator));
        }
    }

    private String genOperator(String fieldName, ComparisonOperatorEnum operator) {
        DbDialect dbDialect = SpringContext.getBean(DbDialect.class);
        switch (operator) {
            case NOT_EQUAL:
                return "!=" + fieldName;
            case GREATER:
                return "&gt;" + fieldName;
            case GREATER_OR_EQUAL:
                return "&gt;=" + fieldName;
            case LESS:
                return "&lt;" + fieldName;
            case LESS_OR_EQUAL:
                return "&lt;=" + fieldName;
            case CONTAIN:
                return " LIKE " + dbDialect.concatLike(new String[]{"%", fieldName, "%"});
            case START_WITH:
                return " LIKE " + dbDialect.concatLike(new String[]{fieldName, "%"});
            case END_WITH:
                return " LIKE " + dbDialect.concatLike(new String[]{"%", fieldName});
            default:
                return "=" + fieldName;
        }
    }
}
