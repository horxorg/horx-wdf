package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.DataAuthorityDetail;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 数据授权项DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DataAuthorityDetailConverter {

    DataAuthorityDetail fromDto(DataAuthorityDetailDTO dataAuthorityDetailDTO);

    DataAuthorityDetailDTO toDto(DataAuthorityDetail dataAuthorityDetail);

    List<DataAuthorityDetailDTO> toDtoList(List<DataAuthorityDetail> dataAuthorityDetailList);
}
