package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.jdbc.annotation.EntityClass;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.SelectByIdProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;
import org.horx.wdf.sys.domain.OnlineUser;
import org.horx.wdf.sys.domain.Session;
import org.horx.wdf.sys.dto.query.OnlineUserQueryDTO;

/**
 * 会话Mapper。
 * @since 1.0
 */
@Mapper
public interface SessionMapper {
    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(Session po);


    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") Session po);

    @EntityClass(cls = Session.class)
    @SelectProvider(type = SelectByIdProvider.class, method = "selectByIdSql")
    Session selectById(@Param("id") Long id);

    Session selectBySessionKey(@Param("sessionKey") String sessionKey);

    int deleteBySessionKey(@Param("sessionKey") String sessionKey);

    int deleteExpired();

    PaginationResult<OnlineUser> paginationSelectOnlineUser(OnlineUserQueryDTO query, PaginationRowBounds paginationParam);
}
