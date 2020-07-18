package org.horx.wdf.sys.converter.query;

import org.horx.wdf.sys.vo.query.DataOperationLogQueryVO;
import org.horx.wdf.sys.dto.query.DataOperationLogQueryDTO;
import org.mapstruct.Mapper;

/**
 * 数据操作日志查询条件VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DataOperationLogQueryVoConverter {

    DataOperationLogQueryDTO fromVo(DataOperationLogQueryVO dataOperationLogQueryVO);
}
