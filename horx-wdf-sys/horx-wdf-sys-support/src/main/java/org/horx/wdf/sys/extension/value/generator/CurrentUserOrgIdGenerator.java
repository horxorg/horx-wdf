package org.horx.wdf.sys.extension.value.generator;

import org.horx.wdf.common.filed.value.ValueContext;
import org.horx.wdf.common.filed.value.generator.ValueGenerator;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * 赋值当前登录用户所属机构ID的生成器。
 * @since 1.0
 */
@Component
public class CurrentUserOrgIdGenerator implements ValueGenerator {

    @Autowired
    private SysContextHolder sysContextHolder;

    @Override
    public Object value(Annotation anno, Field field, ValueContext context) {
        return sysContextHolder.getUserOrgId();
    }
}
