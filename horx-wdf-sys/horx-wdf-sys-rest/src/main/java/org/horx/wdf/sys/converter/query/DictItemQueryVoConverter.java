package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.dto.query.DictItemQueryDTO;
import org.horx.wdf.sys.vo.query.DictItemQueryVO;
import org.mapstruct.Mapper;

/**
 * 字典项查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DictItemQueryVoConverter {

    DictItemQueryDTO fromVo(DictItemQueryVO dictItemQueryVO);
}
