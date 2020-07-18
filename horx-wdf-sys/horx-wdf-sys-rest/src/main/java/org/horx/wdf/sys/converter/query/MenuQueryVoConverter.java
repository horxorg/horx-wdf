package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.dto.query.MenuQueryDTO;
import org.horx.wdf.sys.vo.query.MenuQueryVO;
import org.mapstruct.Mapper;

/**
 * 菜单查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface MenuQueryVoConverter {

    MenuQueryDTO fromVo(MenuQueryVO menuQueryVO);
}
