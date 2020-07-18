package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.jdbc.annotation.EntityClass;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.LogicalDeleteByIdProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;
import org.horx.wdf.sys.domain.Menu;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;

import java.util.List;

/**
 * 菜单Mapper。
 * @since 1.0
 */
@Mapper
@EntityClass(cls = Menu.class)
public interface MenuMapper {
    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(Menu po);

    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") Menu po);

    @UpdateProvider(type = LogicalDeleteByIdProvider.class, method = "deleteByIdSql")
    int logicalDelete(@Param("id") Long id);

    Menu selectById(@Param("id") Long id);

    List<Menu> select(MenuQueryDTO query, PagingRowBounds pagingRowBounds);
}
