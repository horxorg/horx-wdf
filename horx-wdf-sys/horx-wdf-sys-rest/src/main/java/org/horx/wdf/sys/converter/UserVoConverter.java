package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.vo.UserVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 用户VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface UserVoConverter {

    UserVO toVo(UserDTO userDTO);

    UserDTO fromVo(UserVO userVO);

    List<UserVO> toVoList(List<UserDTO> userDTOList);
}
