package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.Role;
import org.horx.wdf.sys.dto.RoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 角色DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface RoleConverter {

    Role fromDto(RoleDTO roleDTO);

    RoleDTO toDto(Role role);

    List<RoleDTO> toDtoList(List<Role> roleList);
}
