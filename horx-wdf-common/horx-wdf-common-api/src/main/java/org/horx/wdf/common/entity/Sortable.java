package org.horx.wdf.common.entity;

/**
 * 可排序接口。
 * @since 1.0
 */
public interface Sortable {

    /**
     * 获取排序项。
     * @return 排序项数组。
     */
    SortItem[] getSortItems();

}
