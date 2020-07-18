package org.horx.wdf.sys.converter;

import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.vo.DataPermissionDefVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 数据权限定义VO转换器。
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface DataPermissionDefVoConverter {

    DataPermissionDefVO toVo(DataPermissionDefDTO dto);

    DataPermissionDefDTO fromVo(DataPermissionDefVO vo);

    List<DataPermissionDefVO> toVoList(List<DataPermissionDefDTO> dtoList);
}
