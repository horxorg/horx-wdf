package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.dto.query.DataPermissionQueryDTO;
import org.horx.wdf.sys.vo.query.DataPermissionQueryVO;
import org.mapstruct.Mapper;

/**
 * 数据权限查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DataPermissionQueryVoConverter {

    DataPermissionQueryDTO fromVo(DataPermissionQueryVO dictQueryVO);
}
