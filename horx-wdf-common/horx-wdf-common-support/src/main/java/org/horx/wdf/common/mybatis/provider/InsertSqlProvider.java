package org.horx.wdf.common.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.jdbc.dialect.DbDialect;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.spring.SpringContext;
import org.horx.wdf.common.tools.EntityTool;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * insert provider。
 * @since 1.0
 */
public class InsertSqlProvider {

    /**
     * 生成insert的sql。
     * @param param 参数。
     * @return
     */
    public String insertSql(Object param, ProviderContext context) {
        if (param == null) {
            return null;
        }

        String paramPrefix = "";
        Object entity = param;
        if (param instanceof Map) {
            Map paramMap = (Map)param;
            String paramName = ProviderUtils.getParamName(context);
            if (paramName == null) {
                paramName = "entity";
            }
            entity = paramMap.get(paramName);
            paramPrefix = paramName + ".";
        }

        Class<?> clazz = MetaUtils.getOriginalClass(entity.getClass());
        EntityMeta entityMeta = MetaUtils.getEntityInfo(clazz);
        if (entityMeta == null || entityMeta.getTableName() == null) {
            throw new RuntimeException("生成insert语句失败：" + clazz.getName() + "未配置表字段映射");
        }

        EntityTool entityTool = SpringContext.getBean(EntityTool.class);
        entityTool.setValue(entity, new Class<?>[]{Groups.Insert.class}, false);

        DbDialect dialect = SpringContext.getBean(DbDialect.class);

        StringBuilder builder1 = new StringBuilder("INSERT INTO ");
        StringBuilder builder2 = new StringBuilder();
        builder1.append(entityMeta.getTableName()).append("(");
        EntityMeta.FieldMeta[] fieldMetas = entityMeta.getFieldMetas();
        if (fieldMetas != null) {
            int cols = 0;
            for (EntityMeta.FieldMeta fieldMeta : fieldMetas) {
                if (fieldMeta.getColumnName() == null) {
                    continue;
                }
                Field field = fieldMeta.getField();
                Id idAno = field.getAnnotation(Id.class);
                if (idAno != null && !dialect.useSequence() || field.getAnnotation(Transient.class) != null) {
                    continue;
                }
                try {
                    Object value = MetaUtils.getValue(entity, fieldMeta);
                    if (value != null || idAno != null && dialect.useSequence()) {
                        if (cols > 0) {
                            builder1.append(',');
                            builder2.append(',');
                        }
                        builder1.append(fieldMeta.getColumnName());
                        builder2.append("#{").append(paramPrefix).append(field.getName()).append("}");
                        cols++;
                    }
                } catch (Exception e) {
                    throw new RuntimeException("获取" + clazz.getName() + "的" + field.getName() + "属性失败", e);
                }
            }
        }

        builder1.append(") VALUES(").append(builder2).append(")");
        return builder1.toString();
    }
}
