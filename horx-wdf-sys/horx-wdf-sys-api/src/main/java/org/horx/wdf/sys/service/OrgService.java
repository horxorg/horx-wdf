package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.SortQuery;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.dataauth.OrgAuthDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.OrgQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.OrgWithAuthDTO;

import java.util.List;

/**
 * 机构Service。
 * @since 1.0
 */
public interface OrgService {
    OrgDTO getById(Long id);

    OrgDTO getByIdAuthorized(Long id, SysDataAuthDTO sysDataAuth);

    List<OrgDTO> query(SortQuery<OrgQueryDTO> sortQuery);

    Long create(OrgWithAuthDTO orgWithAuthDTO);

    void modify(OrgWithAuthDTO orgWithAuthDTO);

    void remove(BatchWithSysAuthDTO batchWithAuthDTO);

    boolean validate(OrgAuthDTO orgAuth, Long orgId);
}
