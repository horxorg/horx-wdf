package org.horx.wdf.common.mybatis.value.generator;

import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.filed.value.ValueContext;
import org.horx.wdf.common.filed.value.generator.ValueGenerator;
import org.horx.wdf.common.mybatis.value.annotation.DeletedRid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 逻辑删除辅助字段生成器。
 * @since 1.0
 */
public class DeletedRidGenerator implements ValueGenerator {
    private final static Logger logger = LoggerFactory.getLogger(DeletedRidGenerator.class);

    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        if (!(anno instanceof DeletedRid)) {
            return null;
        }
        DeletedRid deletedRid = (DeletedRid)anno;

        Object obj = deletedRid.defaultValue();
        if (context.hasGroup(Groups.LogicalDelete.class) && context.getEntityMeta() != null) {
            EntityMeta.FieldMeta idFieldMeta = context.getEntityMeta().getIdFieldMeta();
            if (idFieldMeta != null) {
                try {
                    obj = MetaUtils.getValue(context.getEntity(), idFieldMeta);
                } catch (Exception e) {
                    logger.warn("获取对象属性值错误", e);
                }
            }
        }

        return obj;
    }
}
