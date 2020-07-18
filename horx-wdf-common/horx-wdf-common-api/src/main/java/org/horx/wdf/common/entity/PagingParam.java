package org.horx.wdf.common.entity;

import java.io.Serializable;

/**
 * 分页参数。
 * @since 1.0
 */
public class PagingParam extends SortParam implements Pageable, Serializable {
    private static final long serialVersionUID = 1L;

    private Integer pageSize;
    private Integer currPage;

    @Override
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     * 设置分页大小。
     * @param pageSize 分页大小。
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public Integer getCurrPage() {
        return currPage;
    }

    /**
     * 设置当前页。
     * @param currPage 当前页。
     */
    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    @Override
    public Integer getStart() {
        if (pageSize == null || pageSize.intValue() <= 0 || currPage == null || currPage.intValue() <= 0) {
            return null;
        }

        return pageSize * (currPage - 1) + 1;
    }
}
