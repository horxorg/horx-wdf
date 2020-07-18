package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.Dict;
import org.horx.wdf.sys.dto.DictDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DictConverter {

    Dict fromDto(DictDTO dictDTO);

    DictDTO toDto(Dict dict);

    List<DictDTO> toDtoList(List<Dict> dictList);
}
