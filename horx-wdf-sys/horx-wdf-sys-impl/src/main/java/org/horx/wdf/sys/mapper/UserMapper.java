package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.jdbc.annotation.EntityClass;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.LogicalDeleteByIdProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;
import org.horx.wdf.sys.domain.User;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.UserQueryDTO;

/**
 * 用户Mapper。
 * @since 1.0
 */
@Mapper
@EntityClass(cls = User.class)
public interface UserMapper {

    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(User po);


    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") User po);


    @DeleteProvider(type = LogicalDeleteByIdProvider.class, method = "deleteByIdSql")
    int logicalDelete(@Param("id") Long id);

    User selectById(@Param("id") Long id);

    User selectByIdAuthorized(@Param("id") Long id, @Param("sysDataAuth") SysDataAuthDTO sysDataAuth);

    User selectByUsername(@Param("username") String username);

    User selectForPwd(@Param("id") Long id);

    PaginationResult<User> paginationSelect(UserQueryDTO query, PaginationRowBounds paginationParam);
}
