package org.horx.wdf.common.entity;

/**
 * 分页接口。
 * @since 1.0
 */
public interface Pageable {

    /**
     * 获取分页大小。
     * @return 分页大小。
     */
    Integer getPageSize();

    /**
     * 获取当前页。
     * @return 当前页。从1开始。
     */
    Integer getCurrPage();

    /**
     * 获取数据起始位置
     * @return 数据起始位置。数据起始位置从1开始。
     * @return
     */
    Integer getStart();
}
