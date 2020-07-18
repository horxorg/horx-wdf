package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.dto.DictDTO;
import org.horx.wdf.sys.vo.DictVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DictVoConverter {

    DictVO toVo(DictDTO dictDTO);

    DictDTO fromVo(DictVO dictVO);

    List<DictVO> toVoList(List<DictDTO> dictDTOList);
}
