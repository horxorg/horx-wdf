package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.jdbc.annotation.EntityClass;
import org.horx.wdf.common.mybatis.provider.DeleteByIdProvider;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.LogicalDeleteByIdProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;
import org.horx.wdf.sys.domain.DataAuthorityDetail;

import java.util.List;

/**
 * 数据授权项Mapper。
 * @since 1.0
 */
@Mapper
@EntityClass(cls = DataAuthorityDetail.class)
public interface DataAuthorityDetailMapper {
    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(DataAuthorityDetail po);

    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") DataAuthorityDetail po);

    @UpdateProvider(type = LogicalDeleteByIdProvider.class, method = "deleteByIdSql")
    int logicalDelete(@Param("id") Long id);

    List<DataAuthorityDetail> selectByAuthorityId(@Param("authorityId") Long authorityId);

    List<DataAuthorityDetail> selectByAuthorityIds(@Param("authorityIds") Long[] authorityIds);

    int countByAuthorityIds(@Param("authorityIds") Long[] authorityIds, @Param("value") String value);
}
