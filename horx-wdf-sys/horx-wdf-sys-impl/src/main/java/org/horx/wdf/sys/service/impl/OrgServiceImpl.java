package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.SortParam;
import org.horx.wdf.common.entity.SortQuery;
import org.horx.wdf.common.enums.SortEnum;
import org.horx.wdf.sys.converter.OrgConverter;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.dataauth.OrgAuthDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.domain.Org;
import org.horx.wdf.sys.dto.query.OrgQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.OrgWithAuthDTO;
import org.horx.wdf.sys.manager.OrgManager;
import org.horx.wdf.sys.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 机构Service实现。
 * @since 1.0
 */
@Service("orgService")
public class OrgServiceImpl implements OrgService {

    @Autowired
    private OrgManager orgManager;

    @Autowired
    private OrgConverter orgConverter;

    @Override
    public OrgDTO getById(Long id) {
        Org org = orgManager.getById(id);
        OrgDTO orgDTO = orgConverter.toDto(org);
        return orgDTO;
    }

    @Override
    public OrgDTO getByIdAuthorized(Long id, SysDataAuthDTO sysDataAuth) {
        Org org = orgManager.getByIdAuthorized(id, sysDataAuth);
        OrgDTO orgDTO = orgConverter.toDto(org);
        return orgDTO;
    }

    @Override
    public List<OrgDTO> query(SortQuery<OrgQueryDTO> sortQuery) {
        OrgQueryDTO orgQueryDTO = (sortQuery == null) ? null : sortQuery.getQuery();
        SortParam sortParam = (sortQuery == null) ? null : sortQuery.getSortParam();
        if (sortParam == null) {
            sortParam = new SortParam();
        }
        if (sortParam.getSortField() == null || sortParam.getSortField().length == 0) {
            sortParam.setSortField(new String[] {"displaySeq"});
            sortParam.setSortOrder(new String[] {SortEnum.ASC.name()});
        }

        List<Org> list = orgManager.query(orgQueryDTO, sortParam);
        List<OrgDTO> dtoList = orgConverter.toDtoList(list);
        return dtoList;
    }

    @Override
    public Long create(OrgWithAuthDTO orgWithAuthDTO) {
        OrgDTO orgDTO = orgWithAuthDTO.getOrg();
        Org org = orgConverter.fromDto(orgDTO);
        orgManager.create(org, orgWithAuthDTO.getSysDataAuth());
        return org.getId();
    }

    @Override
    public void modify(OrgWithAuthDTO orgWithAuthDTO) {
        OrgDTO orgDTO = orgWithAuthDTO.getOrg();
        Org org = orgConverter.fromDto(orgDTO);
        orgManager.modify(org, orgWithAuthDTO.getSysDataAuth());
    }

    @Override
    public void remove(BatchWithSysAuthDTO batchWithAuthDTO) {
        orgManager.remove(batchWithAuthDTO.getIds(), batchWithAuthDTO.getSysDataAuth());
    }

    @Override
    public boolean validate(OrgAuthDTO orgAuth, Long orgId) {
        return orgManager.validate(orgAuth, orgId);
    }
}
