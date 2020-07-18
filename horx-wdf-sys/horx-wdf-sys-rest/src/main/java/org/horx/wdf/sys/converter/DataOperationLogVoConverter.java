package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.vo.DataOperationLogVO;
import org.horx.wdf.sys.dto.DataOperationLogDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 数据操作日志VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DataOperationLogVoConverter {

    DataOperationLogVO toVo(DataOperationLogDTO dataOperationLogDTO);

    List<DataOperationLogVO> toVoList(List<DataOperationLogDTO> dataOperationLogDTOList);
}
