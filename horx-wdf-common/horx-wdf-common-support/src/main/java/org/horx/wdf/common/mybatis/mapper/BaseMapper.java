package org.horx.wdf.common.mybatis.mapper;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;
import org.horx.wdf.common.mybatis.provider.InsertSqlProvider;
import org.horx.wdf.common.mybatis.provider.LogicalDeleteByIdProvider;
import org.horx.wdf.common.mybatis.provider.SelectByIdProvider;
import org.horx.wdf.common.mybatis.provider.UpdateSqlProvider;

/**
 * 持久化对象基础Mapper接口，继承该接口自动拥有insert、update、逻辑删除、根据id查询的方法。
 * @param <P> 持久化对象类型。
 * @since 1.0
 */
public interface BaseMapper<P> {
    @InsertProvider(type = InsertSqlProvider.class, method = "insertSql")
    int insert(@Param("po") P po);

    @UpdateProvider(type = UpdateSqlProvider.class, method = "updateSql")
    int update(@Param("po") P po);

    @UpdateProvider(type = LogicalDeleteByIdProvider.class, method = "deleteByIdSql")
    int logicalDelete(@Param("id") Long id);

    @SelectProvider(type = SelectByIdProvider.class, method = "selectByIdSql")
    P selectById(@Param("id") Long id);
}
