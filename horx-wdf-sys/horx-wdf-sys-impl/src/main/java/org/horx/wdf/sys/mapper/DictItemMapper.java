package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.mybatis.entity.PaginationRowBounds;
import org.horx.wdf.common.mybatis.mapper.BaseMapper;
import org.horx.wdf.sys.dto.dataauth.DictItemAuthDTO;
import org.horx.wdf.sys.domain.DictItem;
import org.horx.wdf.sys.dto.query.DictItemQueryDTO;

import java.util.List;

/**
 * 字典项Mapper。
 * @since 1.0
 */
@Mapper
public interface DictItemMapper extends BaseMapper<DictItem> {

    DictItem selectByCode(@Param("dictId") Long dictId, @Param("code") String code);

    PaginationResult<DictItem> paginationSelect(DictItemQueryDTO query, PaginationRowBounds paginationParam);

    List<DictItem> selectForTree(DictItemQueryDTO query);

    List<DictItem> select(DictItemQueryDTO query);

    List<DictItem> selectEnabledByDictId(@Param("dictId") Long dictId);

    List<DictItem> selectEnabledByDictIdWithAuth(@Param("dictId") Long dictId, @Param("dictItemAuth") DictItemAuthDTO dictItemAuth);
}
