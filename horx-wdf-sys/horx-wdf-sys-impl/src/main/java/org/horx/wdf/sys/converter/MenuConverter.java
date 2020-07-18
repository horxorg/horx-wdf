package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.Menu;
import org.horx.wdf.sys.dto.MenuDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 菜单DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface MenuConverter {

    Menu fromDto(MenuDTO menuDTO);

    MenuDTO toDto(Menu menu);

    List<MenuDTO> toDtoList(List<Menu> menuList);
}
