package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.domain.Org;
import org.horx.wdf.sys.dto.OrgDTO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 机构DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface OrgConverter {

    Org fromDto(OrgDTO orgDTO);

    OrgDTO toDto(Org org);

    List<OrgDTO> toDtoList(List<Org> orgList);
}
