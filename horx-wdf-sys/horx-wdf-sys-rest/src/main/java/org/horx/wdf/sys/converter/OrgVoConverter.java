package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.vo.OrgVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 机构VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface OrgVoConverter {

    OrgVO toVo(OrgDTO orgDTO);

    OrgDTO fromVo(OrgVO orgVO);

    List<OrgVO> toVoList(List<OrgDTO> orgDTOList);
}
