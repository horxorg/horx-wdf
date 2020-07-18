package org.horx.wdf.sys.extension.sort.generator;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.filed.sort.CustomSort;
import org.horx.wdf.common.filed.sort.generator.CustomSortGenerator;
import org.horx.wdf.sys.dto.DictDTO;
import org.horx.wdf.sys.extension.sort.annotation.DictSort;
import org.horx.wdf.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 字典排序生成器。
 * @since 1.0
 */
@Component
public class DictSortGenerator implements CustomSortGenerator {

    @Autowired
    private DictService dictService;

    @Override
    public CustomSort getCustomSort(EntityMeta.FieldMeta fieldMeta) {
        Annotation anno = fieldMeta.getCustomSortDef();
        if (!(anno instanceof DictSort)) {
            return null;
        }

        DictSort dictSort = (DictSort)anno;
        String dictCode = dictSort.dictCode();
        if (StringUtils.isEmpty(dictCode)) {
            return null;
        }

        DictDTO dict = dictService.getByCode(dictCode);
        if (dict == null) {
            return null;
        }

        Field field = fieldMeta.getField();

        CustomSort customSort = new CustomSort();
        StringBuilder join = new StringBuilder();
        join.append("left join (select code,display_seq from wdf_dict_item where dict_id=")
                .append(dict.getId())
                .append(") dict_")
                .append(fieldMeta.getColumnName())
                .append(" on(dict_")
                .append(fieldMeta.getColumnName())
                .append(".code=t.")
                .append(fieldMeta.getColumnName())
                .append(")");
        customSort.setJoin(join.toString());
        customSort.setAsc("dict_" + fieldMeta.getColumnName() + ".display_seq");
        customSort.setDesc("dict_" + fieldMeta.getColumnName() + ".display_seq desc");

        return customSort;
    }
}
