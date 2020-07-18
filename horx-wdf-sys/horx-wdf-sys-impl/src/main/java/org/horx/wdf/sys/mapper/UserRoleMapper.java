package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.jdbc.annotation.EntityClass;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.LogicalDeleteByIdProvider;
import org.horx.wdf.common.mybatis.provider.SelectSqlProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;
import org.horx.wdf.sys.domain.UserRole;

import java.util.List;

/**
 * 用户角色Mapper。
 * @since 1.0
 */
@Mapper
@EntityClass(cls = UserRole.class)
public interface UserRoleMapper {

    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(UserRole po);

    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") UserRole po);

    @DeleteProvider(type = LogicalDeleteByIdProvider.class, method = "deleteByIdSql")
    int logicalDelete(@Param("id") Long id);

    @SelectProvider(type = SelectSqlProvider.class, method = "selectSql")
    List<UserRole> selectByUserId(@Param("userId") Long userId);

}
