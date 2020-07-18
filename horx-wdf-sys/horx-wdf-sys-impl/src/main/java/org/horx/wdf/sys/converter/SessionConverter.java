package org.horx.wdf.sys.converter;

import org.horx.wdf.common.extension.session.SessionDTO;
import org.horx.wdf.sys.domain.Session;
import org.mapstruct.Mapper;

/**
 * 会话DTO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface SessionConverter {

    Session fromDto(SessionDTO sessionDTO);

    SessionDTO toDto(Session session);
}
