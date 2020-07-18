package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.LogicalDeleteByIdProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;
import org.horx.wdf.sys.domain.MenuAuthority;
import org.horx.wdf.sys.domain.UserRole;

import java.util.List;

/**
 * 菜单授权Mapper。
 * @since 1.0
 */
@Mapper
public interface MenuAuthorityMapper {
    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(MenuAuthority po);

    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") MenuAuthority po);

    @UpdateProvider(type = LogicalDeleteByIdProvider.class, method = "deleteByIdSql")
    int logicalDelete(@Param("id") Long id);

    List<MenuAuthority> selectByRoleId(@Param("roleIds") Long[] roleIds, @Param("objType") String objType);

}
