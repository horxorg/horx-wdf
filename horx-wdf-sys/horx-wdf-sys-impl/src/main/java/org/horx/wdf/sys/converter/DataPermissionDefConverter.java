package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.DataPermissionDef;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 数据权限定义DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DataPermissionDefConverter {

    DataPermissionDef fromDto(DataPermissionDefDTO dataPermissionDefDTO);

    DataPermissionDefDTO toDto(DataPermissionDef dataPermissionDef);

    List<DataPermissionDefDTO> toDtoList(List<DataPermissionDef> dataPermissionDefList);
}
