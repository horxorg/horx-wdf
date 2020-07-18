package org.horx.wdf.common.entity;

import java.io.Serializable;

/**
 * 培训项。
 * @since 1.0
 */
public class SortItem implements Serializable {
    private static final long serialVersionUID = 1L;

    private String field;

    private String order;

    public SortItem() {}

    /**
     * 构造方法。
     * @param field 排序的属性名。
     * @param order 排序方向。
     */
    public SortItem(String field, String order) {
        this.field = field;
        this.order = order;
    }

    /**
     * 获取排序的属性名。
     * @return 排序的属性名。
     */
    public String getField() {
        return field;
    }

    /**
     * 设置排序的属性名。
     * @param field 排序的属性名。
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * 获取排序方向。
     * @return 排序方向。
     */
    public String getOrder() {
        return order;
    }

    /**
     * 设置排序方向。
     * @param order 排序方向。
     */
    public void setOrder(String order) {
        this.order = order;
    }
}
