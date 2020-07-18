package org.horx.wdf.common.mybatis.provider;

import org.apache.ibatis.builder.annotation.ProviderContext;
import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.spring.SpringContext;
import org.horx.wdf.common.tools.EntityTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 根据id逻辑删除Provider。
 * @since 1.0
 */
public class LogicalDeleteByIdProvider {
    private final static Logger LOGGER = LoggerFactory.getLogger(LogicalDeleteByIdProvider.class);

    /**
     * 生成根据id删除的sql。
     * @param param 参数。
     * @param context
     * @return
     */
    public String deleteByIdSql(Map param, ProviderContext context) {
        if (param == null) {
            return null;
        }

        Class<?> clazz = ProviderUtils.getEntityClass(context);
        EntityMeta entityMeta = MetaUtils.getEntityInfo(clazz);
        if (entityMeta == null || entityMeta.getTableName() == null) {
            throw new RuntimeException("生成逻辑删除语句失败：" + clazz.getName() + "未配置表字段映射");
        }

        EntityMeta.FieldMeta idFieldMeta =  entityMeta.getIdFieldMeta();
        if (idFieldMeta == null) {
            throw new RuntimeException("生成逻辑删除语句失败：" + clazz.getName() + "未配置id字段");
        }

        EntityExtension entityExtension = SpringContext.getBean(EntityExtension.class);
        Object entity = entityExtension.newEntity(clazz);
        try {
            Object objId = param.get("id");
            MetaUtils.setValue(entity, idFieldMeta, objId);
        } catch (Exception e) {
            LOGGER.warn("设置对象属性值错误", e);
        }

        EntityTool entityTool = SpringContext.getBean(EntityTool.class);
        entityTool.setValue(entity, new Class<?>[]{Groups.LogicalDelete.class}, false);

        param.put("entity", entity);

        String sql = ProviderUtils.genUpdateSql(entityMeta, entity, "entity.", param);
        return sql;
    }

}
