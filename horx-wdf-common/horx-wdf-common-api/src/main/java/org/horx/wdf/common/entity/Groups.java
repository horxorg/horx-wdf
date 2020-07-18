package org.horx.wdf.common.entity;

/**
 * 常用分组。
 * @since 1.0
 */
public final class Groups {

    private Groups() {}

    /**
     * 默认分组，可用于新建、更新场景。
     */
    public interface Default {}

    /**
     * 新建分组，可用于新建场景。
     */
    public interface Create {}

    /**
     * 修改分组，可用于更新场景。
     */
    public interface Modify {}

    /**
     * Insert分组，可用于DAO层insert操作赋值。
     */
    public interface Insert {}

    /**
     * Update分组，可用于DAO层update操作赋值。
     */
    public interface Update {}

    /**
     * 逻辑删除分组，可用于DAO层逻辑删除操作赋值。
     */
    public interface LogicalDelete {}
}
