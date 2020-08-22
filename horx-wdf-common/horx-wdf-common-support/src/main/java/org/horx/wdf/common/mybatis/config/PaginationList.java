package org.horx.wdf.common.mybatis.config;

import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 分页List。
 * @since 1.0
 */
public class PaginationList extends ArrayList {

    private PaginationRowBounds paginationRowBounds;

    public PaginationList(PaginationRowBounds paginationRowBounds, Collection c) {
        super(c);
        this.paginationRowBounds = paginationRowBounds;
    }

    public PaginationRowBounds getPaginationRowBounds() {
        return paginationRowBounds;
    }


}
