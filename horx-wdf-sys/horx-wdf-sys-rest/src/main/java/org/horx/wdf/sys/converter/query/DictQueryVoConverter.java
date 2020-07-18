package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.dto.query.DictQueryDTO;
import org.horx.wdf.sys.vo.query.DictQueryVO;
import org.mapstruct.Mapper;

/**
 * 字典查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DictQueryVoConverter {

    DictQueryDTO fromVo(DictQueryVO dictQueryVO);
}
