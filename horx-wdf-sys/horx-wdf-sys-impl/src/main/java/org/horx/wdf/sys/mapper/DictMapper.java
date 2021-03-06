package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.horx.wdf.common.mybatis.mapper.BaseMapper;
import org.horx.wdf.sys.domain.Dict;
import org.horx.wdf.sys.dto.query.DictQueryDTO;

/**
 * 字典Mapper。
 * @since 1.0
 */
@Mapper
public interface DictMapper extends BaseMapper<Dict> {

    Dict selectByCode(@Param("code") String code);

    PaginationResult<Dict> paginationSelect(DictQueryDTO query, PaginationRowBounds paginationParam);
}
