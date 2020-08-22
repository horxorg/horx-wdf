package org.horx.wdf.common.mybatis.entity;

import org.apache.ibatis.session.RowBounds;
import org.horx.wdf.common.entity.Pageable;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.SortItem;
import org.horx.wdf.common.entity.Sortable;

/**
 * 分页RowBounds。
 * @since 1.0
 */
public class PaginationRowBounds extends RowBounds implements Pageable, Sortable {

    private PaginationParam paginationParam;
    private Integer total;

    public PaginationRowBounds() {
        this.paginationParam = new PaginationParam();
    }

    public PaginationRowBounds(PaginationParam paginationParam) {
        this.paginationParam = (paginationParam == null) ? new PaginationParam() : paginationParam;
    }

    @Override
    public int getOffset() {
        return 0;
    }

    @Override
    public int getLimit() {
        Integer pageSize = paginationParam.getPageSize();
        return (pageSize == null || pageSize <= 0) ? Integer.MAX_VALUE : pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public Integer getPageSize() {
        return paginationParam.getPageSize();
    }

    @Override
    public Integer getCurrPage() {
        return paginationParam.getCurrPage();
    }

    @Override
    public Integer getStart() {
        return paginationParam.getStart();
    }

    @Override
    public SortItem[] getSortItems() {
        return paginationParam.getSortItems();
    }
}
