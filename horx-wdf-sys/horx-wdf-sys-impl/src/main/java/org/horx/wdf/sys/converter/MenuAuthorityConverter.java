package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.MenuAuthority;
import org.horx.wdf.sys.dto.MenuAuthorityDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 菜单授权DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface MenuAuthorityConverter {

    MenuAuthorityDTO toDto(MenuAuthority menuAuthority);

    List<MenuAuthorityDTO> toDtoList(List<MenuAuthority> menuAuthorityList);
}
