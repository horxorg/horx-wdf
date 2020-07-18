package org.horx.wdf.common.entity;

import java.io.Serializable;

/**
 * 分页查询对象。
 * @param <T> 查询条件对象。
 * @since 1.0
 */
public class SortQuery<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T query;

    private SortParam sortParam;

    public SortQuery() {}

    /**
     * 构造方法。
     * @param query 查询条件对象。
     * @param sortParam 排序参数。
     */
    public SortQuery(T query, SortParam sortParam) {
        this.query = query;
        this.sortParam = sortParam;
    }

    /**
     * 获取查询条件对象。
     * @return 查询条件对象。
     */
    public T getQuery() {
        return query;
    }

    /**
     * 设置查询条件对象。
     * @param query 查询条件对象。
     */
    public void setQuery(T query) {
        this.query = query;
    }

    /**
     * 获取分页参数。
     * @return 排序参数。
     */
    public SortParam getSortParam() {
        return sortParam;
    }

    /**
     * 设置分页参数。
     * @param sortParam 排序参数。
     */
    public void setSortParam(SortParam sortParam) {
        this.sortParam = sortParam;
    }
}
