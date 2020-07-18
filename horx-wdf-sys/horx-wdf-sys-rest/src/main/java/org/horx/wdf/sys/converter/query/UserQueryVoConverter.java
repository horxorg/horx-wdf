package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.dto.query.UserQueryDTO;
import org.horx.wdf.sys.vo.query.UserQueryVO;
import org.mapstruct.Mapper;

/**
 * 用户查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface UserQueryVoConverter {

    UserQueryDTO fromVo(UserQueryVO userQueryVO);
}
