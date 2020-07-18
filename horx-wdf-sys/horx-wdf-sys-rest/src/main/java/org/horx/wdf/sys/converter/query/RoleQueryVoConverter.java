package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.dto.query.RoleQueryDTO;
import org.horx.wdf.sys.vo.query.RoleQueryVO;
import org.mapstruct.Mapper;

/**
 * 角色查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface RoleQueryVoConverter {

    RoleQueryDTO fromVo(RoleQueryVO roleQueryVO);
}
