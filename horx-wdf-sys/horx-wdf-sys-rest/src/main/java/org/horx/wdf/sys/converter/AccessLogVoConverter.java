package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.vo.AccessLogVO;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 访问日志VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface AccessLogVoConverter {

    AccessLogVO toVo(AccessLogDTO accessLogDTO);

    List<AccessLogVO> toVoList(List<AccessLogDTO> accessLogDTOList);
}
