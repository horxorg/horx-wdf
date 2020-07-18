package org.horx.wdf.common.mybatis.entity;

import org.apache.ibatis.session.RowBounds;
import org.horx.wdf.common.entity.Pageable;
import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.entity.SortItem;
import org.horx.wdf.common.entity.Sortable;

/**
 * 分页RowBounds。
 * @since 1.0
 */
public class PagingRowBounds extends RowBounds implements Pageable, Sortable {

    private PagingParam pagingParam;
    private Integer total;

    public PagingRowBounds(PagingParam pagingParam) {
        this.pagingParam = (pagingParam == null) ? new PagingParam() : pagingParam;
    }

    @Override
    public int getOffset() {
        return 0;
    }

    @Override
    public int getLimit() {
        Integer pageSize = pagingParam.getPageSize();
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
        return pagingParam.getPageSize();
    }

    @Override
    public Integer getCurrPage() {
        return pagingParam.getCurrPage();
    }

    @Override
    public Integer getStart() {
        return pagingParam.getStart();
    }

    @Override
    public SortItem[] getSortItems() {
        return pagingParam.getSortItems();
    }
}
