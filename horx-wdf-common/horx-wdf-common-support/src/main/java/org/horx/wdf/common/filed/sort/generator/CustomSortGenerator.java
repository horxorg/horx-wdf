package org.horx.wdf.common.filed.sort.generator;

import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.filed.sort.CustomSort;

/**
 * 自定义排序生成器。
 * @since 1.0
 */
public interface CustomSortGenerator {
    /**
     * 获取自定义排序定义。
     * @param fieldMeta 属性元数据。
     * @return
     */
    CustomSort getCustomSort(EntityMeta.FieldMeta fieldMeta);
}
