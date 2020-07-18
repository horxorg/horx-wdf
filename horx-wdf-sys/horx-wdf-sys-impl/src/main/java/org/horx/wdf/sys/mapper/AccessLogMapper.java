package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.sys.domain.AccessLog;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;

import java.util.Date;

/**
 * 访问日志Mapper。
 * @since 1.0
 */
@Mapper
public interface AccessLogMapper {

    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(AccessLog entity);

    int deleteHistory(@Param("dateTime") Date dateTime);

    AccessLog selectById(@Param("id") Long id);

    PagingResult<AccessLog> pagingSelect(AccessLogQueryDTO query, PagingRowBounds pagingParam);
}
