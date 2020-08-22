package org.horx.wdf.sys.mapper;

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
import org.horx.wdf.sys.domain.DataPermissionDef;
import org.horx.wdf.sys.dto.query.DataPermissionQueryDTO;

import java.util.List;

/**
 * 数据权限定义Mapper。
 * @since 1.0
 */
@Mapper
@EntityClass(cls = DataPermissionDef.class)
public interface DataPermissionDefMapper {
    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(DataPermissionDef po);


    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") DataPermissionDef po);

    @UpdateProvider(type = LogicalDeleteByIdProvider.class, method = "deleteByIdSql")
    int logicalDelete(@Param("id") Long id);

    DataPermissionDef selectById(@Param("id") Long id);

    DataPermissionDef selectByCode(@Param("code") String code);

    PaginationResult<DataPermissionDef> paginationSelect(DataPermissionQueryDTO query, PaginationRowBounds paginationParam);

    List<DataPermissionDef> select(DataPermissionQueryDTO query);
}
