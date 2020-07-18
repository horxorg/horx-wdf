package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.vo.MenuVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 菜单VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface MenuVoConverter {

    MenuVO toVo(MenuDTO menuDTO);

    MenuDTO fromVo(MenuVO menuVO);

    List<MenuVO> toVoList(List<MenuDTO> menuDTOList);
}
