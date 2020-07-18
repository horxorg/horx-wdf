package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.dto.query.OrgQueryDTO;
import org.horx.wdf.sys.vo.query.OrgQueryVO;
import org.mapstruct.Mapper;

/**
 * 机构查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface OrgQueryVoConverter {

    OrgQueryDTO fromVo(OrgQueryVO orgQueryVO);
}
