package org.horx.wdf.sys.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.horx.wdf.common.mybatis.entity.SortRowBounds;
import org.horx.wdf.common.mybatis.mapper.BaseMapper;
import org.horx.wdf.sys.domain.Org;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.OrgQueryDTO;

import java.util.List;

/**
 * 机构Mapper。
 * @since 1.0
 */
@Mapper
public interface OrgMapper extends BaseMapper<Org> {

    List<Org> select(OrgQueryDTO query, SortRowBounds sortRowBounds);

    int countByOrgAuth(@Param("sysDataAuth") SysDataAuthDTO sysDataAuth, @Param("value") Long value);
}
