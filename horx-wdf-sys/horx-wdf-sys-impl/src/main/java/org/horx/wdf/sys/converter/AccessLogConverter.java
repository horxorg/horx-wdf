package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.AccessLog;
import org.horx.wdf.sys.dto.AccessLogDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 访问日志DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface AccessLogConverter {

    AccessLog fromDto(AccessLogDTO accessLogDTO);

    AccessLogDTO toDto(AccessLog accessLog);

    List<AccessLogDTO> toDtoList(List<AccessLog> accessLogList);
}
