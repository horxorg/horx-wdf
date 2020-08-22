package org.horx.wdf.common.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.horx.wdf.common.mybatis.entity.SortRowBounds;
import org.horx.wdf.common.mybatis.provider.SelectSqlProvider;

import java.util.List;

/**
 * 持久化对象查询Mapper接口，继承该接口自动拥有insert、update、逻辑删除、根据id查询、查询、分页查询的方法。
 * @param <P> 持久化对象类型。
 * @since 1.0
 */
public interface BaseSelectMapper<P, Q> extends BaseMapper<P> {

    @SelectProvider(type = SelectSqlProvider.class, method = "selectSql")
    List<P> select(@Param("query") Q query, SortRowBounds sortRowBounds);

    @SelectProvider(type = SelectSqlProvider.class, method = "selectSql")
    PaginationResult<P> paginationSelect(@Param("query") Q query, PaginationRowBounds paginationRowBounds);
}
