package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.DataOperationLog;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 数据操作日志DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DataOperationLogConverter {

    DataOperationLog fromDto(DataOperationLogDTO dataOperationLogDTO);

    DataOperationLogDTO toDto(DataOperationLog dataOperationLog);

    List<DataOperationLogDTO> toDtoList(List<DataOperationLog> dataOperationLogList);
}
