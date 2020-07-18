package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.User;
import org.horx.wdf.sys.dto.UserDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 用户DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface UserConverter {

    User fromDto(UserDTO userDTO);

    UserDTO toDto(User user);

    List<UserDTO> toDtoList(List<User> userList);
}
