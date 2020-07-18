package org.horx.wdf.common.field;

/**
 * 属性赋值状态接口。
 * @since 1.0
 */
public interface FieldAssignment {

    /**
     * 属性是否已赋值。
     * @param fieldName 属性名。
     * @return 属性是否已赋值。
     */
    boolean isFieldAssigned(String fieldName);
}
