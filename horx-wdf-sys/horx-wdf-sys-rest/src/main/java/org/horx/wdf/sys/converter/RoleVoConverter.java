package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.dto.RoleDTO;
import org.horx.wdf.sys.vo.RoleVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 角色VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface RoleVoConverter {

    RoleVO toVo(RoleDTO roleDTO);

    RoleDTO fromVo(RoleVO userVO);

    List<RoleVO> toVoList(List<RoleDTO> roleDTOList);
}
