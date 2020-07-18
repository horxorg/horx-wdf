package org.horx.wdf.common.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.spring.SpringContext;
import org.horx.wdf.common.tools.EntityTool;

import java.util.Map;

/**
 * update provider。
 * @since 1.0
 */
public class UpdateSqlProvider {

    /**
     * 生成update的sql。
     * @param param 参数。
     * @return
     */
    public String updateSql(Object param, ProviderContext context) {
        if (param == null) {
            return null;
        }

        Map paramMap = null;
        String paramPrefix = "";
        Object entity = param;
        if (param instanceof Map) {
            paramMap = (Map)param;
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
            throw new RuntimeException("生成update语句失败：" + clazz.getName() + "未配置表字段映射");
        }

        EntityTool entityTool = SpringContext.getBean(EntityTool.class);
        entityTool.setValue(entity, new Class<?>[]{Groups.Update.class}, false);

        String sql = ProviderUtils.genUpdateSql(entityMeta, entity, paramPrefix, paramMap);
        return sql;
    }
}
