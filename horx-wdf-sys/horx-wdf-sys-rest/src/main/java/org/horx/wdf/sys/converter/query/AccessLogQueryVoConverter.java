package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.vo.query.AccessLogQueryVO;
import org.horx.wdf.sys.dto.query.AccessLogQueryDTO;
import org.mapstruct.Mapper;

/**
 * 访问日志查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface AccessLogQueryVoConverter {

    AccessLogQueryDTO fromVo(AccessLogQueryVO accessLogQueryVO);
}
