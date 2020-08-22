package org.horx.wdf.common.entity;

import java.io.Serializable;

/**
 * 分页查询对象。
 * @param <T> 查询条件对象。
 * @since 1.0
 */
public class PaginationQuery<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private T query;

    private PaginationParam paginationParam;

    public PaginationQuery() {}

    /**
     * 构造方法。
     * @param query 查询条件对象。
     * @param paginationParam 分页参数。
     */
    public PaginationQuery(T query, PaginationParam paginationParam) {
        this.query = query;
        this.paginationParam = paginationParam;
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
     * @return 分页参数。
     */
    public PaginationParam getPaginationParam() {
        return paginationParam;
    }

    /**
     * 设置分页参数。
     * @param paginationParam 分页参数。
     */
    public void setPaginationParam(PaginationParam paginationParam) {
        this.paginationParam = paginationParam;
    }
}
