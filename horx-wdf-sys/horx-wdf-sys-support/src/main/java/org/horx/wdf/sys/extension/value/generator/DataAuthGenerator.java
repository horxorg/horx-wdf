package org.horx.wdf.sys.extension.value.generator;

import org.horx.wdf.common.filed.value.ValueContext;
import org.horx.wdf.common.filed.value.generator.ValueGenerator;
import org.horx.wdf.sys.extension.dataauth.DataValidationTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 赋值数据权限对象的生成器。
 * @since 1.0
 */
@Component
public class DataAuthGenerator implements ValueGenerator {

    @Autowired
    private DataValidationTool dataValidationTool;

    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        return dataValidationTool.genDataAuth(field.getType());
    }
}
