package org.horx.wdf.common.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.spring.SpringContext;

import java.util.Map;

/**
 * 根据id查询的provider。
 * @since 1.0
 */
public class SelectByIdProvider {

    /**
     * 生成根据id查询的sql。
     * @param param 参数。
     * @param context
     * @return
     */
    public String selectByIdSql(Object param, ProviderContext context) {
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

        String paramName = null;
        if (param instanceof Map) {
            paramName = ProviderUtils.getParamName(context);
        }
        if (paramName == null) {
            paramName = idFieldMeta.getField().getName();
        }

        sql.append(" FROM ")
                .append(entityMeta.getTableName())
                .append(" WHERE ")
                .append(idFieldMeta.getColumnName())
                .append("=#{")
                .append(paramName)
                .append("}");

        Map paramMap = (param instanceof Map) ? (Map)param : null;
        ProviderUtils.genDeletedCondition(entityMeta, sql, paramMap, true);

        return sql.toString();
    }

}
