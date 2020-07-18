package org.horx.wdf.common.mybatis.config;

import org.horx.wdf.common.mybatis.entity.PagingRowBounds;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页List。
 * @since 1.0
 */
public class PagingList extends ArrayList {

    private PagingRowBounds pagingRowBounds;

    public PagingList(PagingRowBounds pagingRowBounds, Collection c) {
        super(c);
        this.pagingRowBounds = pagingRowBounds;
    }

    public PagingRowBounds getPagingRowBounds() {
        return pagingRowBounds;
    }


}
