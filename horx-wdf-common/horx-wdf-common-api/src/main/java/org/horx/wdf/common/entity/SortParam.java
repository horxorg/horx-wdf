package org.horx.wdf.common.entity;

import org.horx.wdf.common.enums.SortEnum;

import java.io.Serializable;

/**
 * 排序参数。
 * @since 1.0
 */
public class SortParam implements Sortable, Serializable {
    private static final long serialVersionUID = 1L;

    private String[] sortField;
    private String[] sortOrder;
    private SortItem[] sortItems;

    /**
     * 获取排序属性名。
     * @return 排序属性名。
     */
    public String[] getSortField() {
        return sortField;
    }

    /**
     * 设置排序属性名。
     * @param sortField 排序属性名。
     */
    public void setSortField(String[] sortField) {
        this.sortField = sortField;
        sortItems = null;
    }

    /**
     * 获取排序方向。
     * @return 排序方向。
     */
    public String[] getSortOrder() {
        return sortOrder;
    }

    /**
     * 设置排序方向。
     * @param sortOrder 排序方向。
     */
    public void setSortOrder(String[] sortOrder) {
        this.sortOrder = sortOrder;
        sortItems = null;
    }

    @Override
    public SortItem[] getSortItems() {
        if (sortField == null || sortField.length == 0) {
            return new SortItem[0];
        }

        if (sortItems == null) {
            SortItem[] sortItems = new SortItem[sortField.length];
            for (int i = 0; i < sortField.length; i++) {
                String fld = sortField[i];
                String order = (sortOrder == null || sortOrder.length <= i) ? null : sortOrder[i];
                SortEnum sortEnum = SortEnum.parse(order);
                sortItems[i] = new SortItem(fld, sortEnum.name());
            }
            this.sortItems = sortItems;
        }

        return sortItems;
    }
}
