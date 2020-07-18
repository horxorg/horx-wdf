package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.OnlineUser;
import org.horx.wdf.sys.dto.OnlineUserDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 在线用户DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface OnlineUserConverter {

    OnlineUserDTO toDto(OnlineUser onlineUse);

    List<OnlineUserDTO> toDtoList(List<OnlineUser> userList);
}
