package org.horx.wdf.sys.converter;

import org.horx.wdf.common.extension.session.SessionAttrDTO;
import org.horx.wdf.sys.domain.SessionAttr;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 会话属性DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface SessionAttrConverter {

    SessionAttr fromDto(SessionAttrDTO sessionAttrDTO);

    SessionAttrDTO toDto(SessionAttr sessionAttr);

    List<SessionAttrDTO> toDtoList(List<SessionAttr> sessionAttrList);
}
