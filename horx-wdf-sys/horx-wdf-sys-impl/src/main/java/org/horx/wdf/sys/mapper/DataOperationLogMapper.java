package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.sys.domain.DataOperationLog;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;

import java.util.Date;

/**
 * 数据操作日志Mapper。
 * @since 1.0
 */
@Mapper
public interface DataOperationLogMapper {

    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(DataOperationLog entity);

    int deleteHistory(@Param("dateTime") Date dateTime);

    DataOperationLog selectById(@Param("id") Long id);

    PagingResult<DataOperationLog> pagingSelect(DataOperationLogQueryDTO query, PagingRowBounds pagingParam);
}
