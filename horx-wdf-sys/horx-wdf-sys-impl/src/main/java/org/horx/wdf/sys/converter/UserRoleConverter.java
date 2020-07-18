package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.UserRole;
import org.horx.wdf.sys.dto.UserRoleDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 用户角色DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface UserRoleConverter {

    UserRole fromDto(UserRoleDTO userRoleDTO);

    UserRoleDTO toDto(UserRole userRole);

    List<UserRoleDTO>toDtoList(List<UserRole> userRoleList);
}
