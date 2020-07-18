package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.jdbc.annotation.EntityClass;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.LogicalDeleteByIdProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;
import org.horx.wdf.sys.domain.Role;
import org.horx.wdf.sys.dto.query.RoleQueryDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;

/**
 * 角色Mapper。
 * @since 1.0
 */
@Mapper
@EntityClass(cls = Role.class)
public interface RoleMapper {

    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(Role po);

    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") Role po);

    @UpdateProvider(type = LogicalDeleteByIdProvider.class, method = "deleteByIdSql")
    int logicalDelete(@Param("id") Long id);

    Role selectById(@Param("id") Long id);

    Role selectByIdAuthorized(@Param("id") Long id, @Param("sysDataAuth") SysDataAuthDTO sysDataAuth);

    Role selectByIdUsable(@Param("id") Long id, @Param("sysDataAuth") SysDataAuthDTO sysDataAuth);

    PagingResult<Role> pagingSelect(RoleQueryDTO query, PagingRowBounds pagingParam);

    PagingResult<Role> pagingSelectUsable(RoleQueryDTO query, PagingRowBounds pagingParam);
}
