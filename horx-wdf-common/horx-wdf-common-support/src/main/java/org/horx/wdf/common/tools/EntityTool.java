package org.horx.wdf.common.tools;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.Groups;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.field.annotation.FieldDesc;
import org.horx.wdf.common.exception.ResultException;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.entity.meta.EntityMeta;
import org.horx.wdf.common.entity.meta.MetaUtils;
import org.horx.wdf.common.filed.value.ValueProvider;
import org.springframework.beans.SimpleTypeConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.lang.reflect.Field;
import java.util.Set;

/**
 * 实体类工具类。
 * @since 1.0
 */
public class EntityTool {
    @Autowired
    private EntityExtension entityExtension;

    @Autowired
    private Validator validator;

    @Autowired
    private MsgTool msgTool;

    private ValueProvider valueProvider = new ValueProvider();

    private SimpleTypeConverter typeConverter = new SimpleTypeConverter();

    /**
     * 获取Group。
     * @param argEntity
     * @return
     */
    public Class<?>[] toGroups(ArgEntity argEntity) {
        Class<?>[] groups = null;
        if (argEntity.modify()) {
            if (argEntity.create()) {
                groups = new Class<?>[]{Groups.Default.class, Groups.Create.class,  Groups.Modify.class};
            } else {
                groups = new Class<?>[]{Groups.Default.class, Groups.Modify.class};
            }
        } else if (argEntity.create()) {
            groups = new Class<?>[]{Groups.Default.class, Groups.Create.class};
        } else {
            groups = argEntity.groups();
        }

        return groups;
    }

    /**
     * 生成对象实例并赋值。
     * @param beanClass
     * @param <T>
     * @return
     */
    public <T> T newEntity(Class<T> beanClass) {
        return newEntity(beanClass, false, null, false);
    }

    /**
     * 生成对象实例并赋值。
     * @param beanClass
     * @param recordAssignment 是否记录赋值状态。
     * @param <T>
     * @return
     */
    public <T> T newEntity(Class<T> beanClass, boolean recordAssignment) {
        return newEntity(beanClass, recordAssignment, null, false);
    }

    /**
     * 生成对象实例并赋值。
     * @param beanClass
     * @param recordAssignment 是否记录赋值状态。
     * @param groups
     * @param validate 是否校验合法性。
     * @param <T>
     * @return
     */
    public <T> T newEntity(Class<T> beanClass, boolean recordAssignment, Class<?>[] groups, boolean validate) {
        Class<?> extClass = entityExtension.getExtensionClass(beanClass);
        EntityMeta entityMeta = MetaUtils.getEntityInfo(extClass);
        T bean = entityExtension.newEntity(beanClass, recordAssignment);

        setValue(bean, entityMeta, groups, validate);


        return bean;
    }

    /**
     * 给实例赋值。
     * @param entity
     * @param groups
     * @param validate 是否校验合法性。
     */
    public void setValue(Object entity, Class<?>[] groups, boolean validate) {
        if (entity == null) {
            return;
        }

        EntityMeta entityMeta = MetaUtils.getEntityInfo(entity.getClass());
        setValue(entity, entityMeta, groups, validate);
    }

    /**
     * 校验。
     * @param bean
     * @param groups
     * @param <T>
     */
    public <T> void validate(Object bean, Class[] groups) {
        if (bean == null) {
            return;
        }
        EntityMeta entityMeta = MetaUtils.getEntityInfo(bean.getClass());
        validate(bean, groups, entityMeta);
    }

    private void setValue(Object entity, EntityMeta entityMeta, Class<?>[] groups, boolean validate) {
        valueProvider.genFieldValue(entity, entityMeta, groups);

        if (validate) {
            validate(entity, groups, entityMeta);
        }

    }

    private <T> void validate(Object bean, Class[] groups, EntityMeta entityMeta) {
        if (bean == null) {
            return;
        }
        Set<ConstraintViolation<Object>> set = validator.validate(bean, groups);
        if (set.size() > 0) {
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<Object> constraintViolation : set) {

                if (builder.length() > 0) {
                    builder.append('\n');
                }
                EntityMeta.FieldMeta fieldMeta = entityMeta.getFieldMeta(constraintViolation.getPropertyPath().toString());
                if (fieldMeta != null) {
                    builder.append(getFieldName(fieldMeta.getField())).append(':').append(constraintViolation.getMessage());
                }
            }

            Result result = new Result(ErrorCodeEnum.A0430.getCode(), builder.toString());
            throw new ResultException("验证失败", result);
        }
    }

    private String getFieldName(Field field) {
        String name = null;
        FieldDesc fieldDesc = field.getAnnotation(FieldDesc.class);
        if (fieldDesc != null) {
            name = fieldDesc.name().trim();
            if (name.startsWith("${") && name.endsWith("}")) {
                name = msgTool.getMsg(name.substring(2, name.length() - 1));
            } else if (name.startsWith("{") && name.endsWith("}")) {
                name = msgTool.getMsg(name.substring(1, name.length() - 1));
            }
        }
        if (StringUtils.isEmpty(name)) {
            name = field.getName();
        }
        return name;
    }
}
