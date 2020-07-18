package org.horx.wdf.common.enums;

/**
 * 数据操作类型。
 * @since 1.0
 */
public enum OperationTypeEnum {
    /**
     * 新建。
     */
    CREATE,
    /**
     * 更新。
     */
    MODIFY,
    /**
     * 删除。
     */
    REMOVE,
    /**
     * 逻辑删除。
     */
    LOGICAL_REMOVE,
    /**
     * 其他。
     */
    OTHER;
}
