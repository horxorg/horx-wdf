package org.horx.wdf.common.filed.sort.generator;

import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.filed.sort.CustomSort;
import org.horx.wdf.common.filed.sort.annotation.FieldSort;

import java.lang.annotation.Annotation;

/**
 * 自定义排序生成器实现。
 * @since 1.0
 */
public class FieldSortGenerator implements CustomSortGenerator {

    @Override
    public CustomSort getCustomSort(EntityMeta.FieldMeta fieldMeta) {
        Annotation anno = fieldMeta.getCustomSortDef();
        if (!(anno instanceof FieldSort)) {
            return null;
        }
        FieldSort fieldSort = (FieldSort)anno;
        CustomSort customSort = new CustomSort();
        customSort.setJoin(fieldSort.join());
        customSort.setAsc(fieldSort.asc());
        customSort.setDesc(fieldSort.desc());
        return customSort;
    }
}
