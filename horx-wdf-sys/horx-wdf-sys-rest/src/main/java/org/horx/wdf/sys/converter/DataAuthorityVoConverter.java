package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.vo.DataAuthorityVO;
import org.mapstruct.Mapper;

/**
 * 数据授权VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DataAuthorityVoConverter {

    DataAuthorityVO toVo(DataAuthorityDTO dataAuthorityDTO);

    DataAuthorityDTO fromVo(DataAuthorityVO dataAuthorityVO);

}
