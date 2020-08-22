package org.horx.wdf.common.mybatis.entity;

import org.apache.ibatis.session.RowBounds;
import org.horx.wdf.common.entity.SortItem;
import org.horx.wdf.common.entity.SortParam;
import org.horx.wdf.common.entity.Sortable;

/**
 * 排序RowBounds。
 * @since 1.0
 */
public class SortRowBounds extends RowBounds implements Sortable {

    private SortParam sortParam;

    public SortRowBounds() {
        this.sortParam = new SortParam();
    }

    public SortRowBounds(SortParam sortParam) {
        this.sortParam = (sortParam == null) ? new SortParam() : sortParam;
    }

    @Override
    public SortItem[] getSortItems() {
        return sortParam.getSortItems();
    }
}
