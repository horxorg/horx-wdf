package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.jdbc.annotation.EntityClass;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.SelectByIdProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;
import org.horx.wdf.sys.domain.DataAuthority;

import java.util.List;

/**
 * 数据授权Mapper。
 * @since 1.0
 */
@Mapper
public interface DataAuthorityMapper {
    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(DataAuthority po);

    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") DataAuthority po);

    @EntityClass(cls = DataAuthority.class)
    @SelectProvider(type = SelectByIdProvider.class, method = "selectByIdSql")
    DataAuthority selectById(@Param("id") Long id);

    DataAuthority selectByObj(@Param("dataPermissionId") Long dataPermissionId,
                              @Param("objType") String objType, @Param("objId") Long objId);

    List<DataAuthority> selectUserEnabledData(@Param("userId") Long userId, @Param("roleIds") Long[] roleIds,
                                              @Param("dataPermissionId") Long dataPermissionId);

    List<DataAuthority> selectAdminRoleEnabledData(@Param("roleIds") Long[] roleIds,
                                                   @Param("dataPermissionId") Long dataPermissionId);
}
