package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.DictItem;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典项DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DictItemConverter {

    DictItem fromDto(DictItemDTO dictDTO);

    DictItemDTO toDto(DictItem dict);

    List<DictItemDTO> toDtoList(List<DictItem> dictItemList);
}
