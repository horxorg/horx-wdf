package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.dto.query.OnlineUserQueryDTO;
import org.horx.wdf.sys.vo.query.OnlineUserQueryVO;
import org.mapstruct.Mapper;

/**
 * 在线用户查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface OnlineUserQueryVoConverter {

    OnlineUserQueryDTO fromVo(OnlineUserQueryVO onlineUserQueryVO);
}
