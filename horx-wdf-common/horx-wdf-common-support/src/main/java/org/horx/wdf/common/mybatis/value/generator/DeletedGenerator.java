package org.horx.wdf.common.mybatis.value.generator;

import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.filed.value.ValueContext;
import org.horx.wdf.common.filed.value.generator.ValueGenerator;
import org.horx.wdf.common.mybatis.value.annotation.Deleted;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 逻辑删除生成器。
 * @since 1.0
 */
public class DeletedGenerator implements ValueGenerator {

    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        if (!(anno instanceof Deleted)) {
            return null;
        }
        Deleted deleted = (Deleted)anno;

        Object obj = deleted.notDeletedValue();
        if (context.hasGroup(Groups.LogicalDelete.class)) {
            obj = deleted.deletedValue();
        }

        return obj;
    }
}
