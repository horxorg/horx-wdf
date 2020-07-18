package org.horx.wdf.common.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;

import java.util.Map;

/**
 * 根据id删除Provider。
 * @since 1.0
 */
public class DeleteByIdProvider {

    /**
     * 生成根据id删除的sql。
     * @param param 参数。
     * @param context
     * @return
     */
    public String deleteByIdSql(Object param, ProviderContext context) {
        if (param == null) {
            return null;
        }

        Class<?> clazz = ProviderUtils.getEntityClass(context);
        EntityMeta entityMeta = MetaUtils.getEntityInfo(clazz);
        if (entityMeta == null || entityMeta.getTableName() == null) {
            throw new RuntimeException("生成delete语句失败：" + clazz.getName() + "未配置表字段映射");
        }

        EntityMeta.FieldMeta idFieldMeta =  entityMeta.getIdFieldMeta();
        if (idFieldMeta == null) {
            throw new RuntimeException("生成delete语句失败：" + clazz.getName() + "未配置id字段");
        }

        String paramName = null;
        if (param instanceof Map) {
            paramName = ProviderUtils.getParamName(context);
        }
        if (paramName == null) {
            paramName = idFieldMeta.getField().getName();
        }

        StringBuilder sql = new StringBuilder("DELETE FROM ");
        sql.append(entityMeta.getTableName())
                .append(" WHERE ")
                .append(idFieldMeta.getColumnName())
                .append("=#{")
                .append(paramName)
                .append("}");

        return sql.toString();
    }

}
