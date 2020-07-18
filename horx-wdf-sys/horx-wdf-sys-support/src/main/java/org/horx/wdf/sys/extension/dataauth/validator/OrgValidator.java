package org.horx.wdf.sys.extension.dataauth.validator;

import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.enums.CheckedTypeEnum;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.dto.dataauth.OrgAuthDTO;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.enums.OrgAuthorityTypeEnum;
import org.horx.wdf.sys.extension.dataauth.DataValidator;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 机构数据权限验证器。
 * @since 1.0
 */
@Component
public class OrgValidator implements DataValidator<OrgAuthDTO, Long> {

    @Autowired
    private DataAuthorityService dataAuthorityService;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private OrgService orgService;

    @Autowired
    private SysConfig sysConfig;

    @Override
    public OrgAuthDTO genDataAuth(DataPermissionDefDTO dataPermissionDef, List<DataAuthorityDTO> list) {
        OrgAuthDTO orgAuth = new OrgAuthDTO();

        if (dataPermissionDef != null && sysConfig.isAdmin(sysContextHolder.getUser()) && sysConfig.isAdminHasAllDataPermission(dataPermissionDef.getCode())) {
            orgAuth.setScope(DataValidationScopeEnum.ALL.getCode());
            return orgAuth;
        }

        if (list == null || list.size() == 0) {
            orgAuth.setScope(DataValidationScopeEnum.ALL.getCode());
            return orgAuth;
        }

        for (DataAuthorityDTO dataAuthority : list) {
            if (OrgAuthorityTypeEnum.ALL.getCode().equals(dataAuthority.getAuthorityType())) {
                orgAuth.setScope(DataValidationScopeEnum.ALL.getCode());
                return orgAuth;
            }

            if (OrgAuthorityTypeEnum.USER_ORG.getCode().equals(dataAuthority.getAuthorityType()) ||
                    OrgAuthorityTypeEnum.USER_ORG_AND_SUBS.getCode().equals(dataAuthority.getAuthorityType())) {
                Long orgId = sysContextHolder.getUserOrgId();
                if (orgId == null) {
                    orgAuth.setScope(DataValidationScopeEnum.ALL.getCode());
                    return orgAuth;
                }
            }
        }

        List<Long> authIdList = new ArrayList<>();
        List<Long> authIdQueryList = new ArrayList<>();
        Set<Long> orgIdSet = new HashSet<>();
        Set<Long> orgIdWithSubSet = new HashSet<>();

        for (DataAuthorityDTO dataAuthority : list) {
            if (OrgAuthorityTypeEnum.USER_ORG.getCode().equals(dataAuthority.getAuthorityType())) {
                orgIdSet.add(sysContextHolder.getUserOrgId());
            } else if (OrgAuthorityTypeEnum.USER_ORG_AND_SUBS.getCode().equals(dataAuthority.getAuthorityType())) {
                orgIdWithSubSet.add(sysContextHolder.getUserOrgId());
            } else if (OrgAuthorityTypeEnum.ASSIGNED.getCode().equals(dataAuthority.getAuthorityType())) {
                if (dataAuthority.getDetailCount() != null && dataAuthority.getDetailCount() <= 20) {
                    authIdQueryList.add(dataAuthority.getId());
                } else {
                    authIdList.add(dataAuthority.getId());
                }
            }
        }

        if (authIdQueryList.size() > 0) {
            List<DataAuthorityDetailDTO> detailList = dataAuthorityService.queryDetailByAuthorityIds(authIdQueryList.toArray(new Long[0]));
            for (DataAuthorityDetailDTO detail : detailList) {
                if (CheckedTypeEnum.CHECKED.getCode() == detail.getCheckedType()) {
                    orgIdSet.add(Long.parseLong(detail.getAuthorityValue()));
                } else if (CheckedTypeEnum.CHECKED_ALL.getCode() == detail.getCheckedType()) {
                    orgIdWithSubSet.add(Long.parseLong(detail.getAuthorityValue()));
                }
            }
        }

        if (authIdList.size() > 0) {
            orgAuth.setAuthIds(authIdList.toArray(new Long[0]));
        }
        if (orgIdSet.size() > 0) {
            orgAuth.setOrgIds(orgIdSet.toArray(new Long[0]));
        }

        if (orgIdWithSubSet.size() > 0) {
            orgAuth.setOrgIdsWithSub(orgIdWithSubSet.toArray(new Long[0]));
        }

        if (authIdList.size() > 0 || orgIdSet.size() > 0 || orgIdWithSubSet.size() > 0) {
            orgAuth.setScope(DataValidationScopeEnum.PART.getCode());
        }

        return orgAuth;
    }

    @Override
    public boolean validate(OrgAuthDTO dataAuth, Long value) {
        if (dataAuth == null || dataAuth.getScope() == DataValidationScopeEnum.ALL.getCode()) {
            return true;
        } else if (dataAuth.getScope() == DataValidationScopeEnum.FORBIDDEN.getCode()) {
            return false;
        }

        if (dataAuth.getOrgIds() != null && dataAuth.getOrgIds().length > 0) {
           for (Long orgId : dataAuth.getOrgIds()) {
                if (orgId.equals(value)) {
                    return true;
                }
            }
        }

        if (dataAuth.getAuthIds() != null && dataAuth.getAuthIds().length > 0 ||
            dataAuth.getOrgIdsWithSub() != null && dataAuth.getOrgIdsWithSub().length > 0) {
            return orgService.validate(dataAuth, value);
        }

        return false;
    }

}
