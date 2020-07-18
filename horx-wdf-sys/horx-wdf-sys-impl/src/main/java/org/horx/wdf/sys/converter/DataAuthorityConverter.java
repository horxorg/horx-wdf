package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.DataAuthority;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 数据授权DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DataAuthorityConverter {

    DataAuthority fromDto(DataAuthorityDTO dataAuthorityDTO);

    DataAuthorityDTO toDto(DataAuthority dataAuthority);

    List<DataAuthorityDTO> toDtoList(List<DataAuthority> dataAuthorityList);
}
