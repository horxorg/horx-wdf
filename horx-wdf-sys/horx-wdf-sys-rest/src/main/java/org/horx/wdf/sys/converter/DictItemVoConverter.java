package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.vo.DictItemVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典项VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DictItemVoConverter {

    DictItemVO toVo(DictItemDTO dictItemDTO);

    DictItemDTO fromVo(DictItemVO dictItemVO);

    List<DictItemVO> toVoList(List<DictItemDTO> dictItemDTOList);
}
