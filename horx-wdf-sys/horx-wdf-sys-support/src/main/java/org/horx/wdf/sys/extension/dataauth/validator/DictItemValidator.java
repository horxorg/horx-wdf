package org.horx.wdf.sys.extension.dataauth.validator;

import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.enums.DictAuthorityTypeEnum;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.extension.dataauth.DataValidator;
import org.horx.wdf.sys.dto.dataauth.DictItemAuthDTO;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 字典项数据权限验证器。
 * @since 1.0
 */
@Component
public class DictItemValidator implements DataValidator<DictItemAuthDTO, String> {

    @Autowired
    private DataAuthorityService dataAuthorityService;

    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Override
    public DictItemAuthDTO genDataAuth(DataPermissionDefDTO dataPermissionDef, List<DataAuthorityDTO> list) {
        DictItemAuthDTO dictItemAuth = new DictItemAuthDTO();

        if (dataPermissionDef != null && sysConfig.isAdmin(sysContextHolder.getUser()) && sysConfig.isAdminHasAllDataPermission(dataPermissionDef.getCode())) {
            dictItemAuth.setScope(DataValidationScopeEnum.ALL.getCode());
            return dictItemAuth;
        }

        if (list == null || list.size() == 0) {
            dictItemAuth.setScope(DataValidationScopeEnum.ALL.getCode());
            return dictItemAuth;
        }

        for (DataAuthorityDTO dataAuthority : list) {
            if (DictAuthorityTypeEnum.ALL.getCode().equals(dataAuthority.getAuthorityType())) {
                dictItemAuth.setScope(DataValidationScopeEnum.ALL.getCode());
                return dictItemAuth;
            }
        }



        int count = 0;
        List<Long> idList = new ArrayList<>(list.size());
        for (DataAuthorityDTO dataAuthority : list) {
            if (!DictAuthorityTypeEnum.ALL.getCode().equals(dataAuthority.getAuthorityType())) {
                idList.add(dataAuthority.getId());
            }

            Integer detailCount = dataAuthority.getDetailCount();
            if (detailCount != null) {
                count += detailCount;
            }
        }

        Long[] ids = idList.toArray(new Long[0]);
        if (count <= 100) {
            List<DataAuthorityDetailDTO> detailList = dataAuthorityService.queryDetailByAuthorityIds(ids);
            Set<String> valueSet = new HashSet<>();
            for (DataAuthorityDetailDTO detail : detailList) {
                valueSet.add(detail.getAuthorityValue());
            }

            dictItemAuth.setDictItemCodes(valueSet.toArray(new String[0]));
        } else {
            dictItemAuth.setAuthIds(ids);
        }

        if (ids.length == 0 && (dictItemAuth.getDictItemCodes() == null || dictItemAuth.getDictItemCodes().length == 0)) {
            dictItemAuth.setScope(DataValidationScopeEnum.FORBIDDEN.getCode());
        }

        return dictItemAuth;
    }

    @Override
    public boolean validate(DictItemAuthDTO dataAuth, String value) {
        if (dataAuth == null || dataAuth.getScope() == DataValidationScopeEnum.ALL.getCode()) {
            return true;
        } else if (dataAuth.getScope() == DataValidationScopeEnum.FORBIDDEN.getCode()) {
            return false;
        }

        if (dataAuth.getDictItemCodes() != null && dataAuth.getDictItemCodes().length > 0) {
            for (String code : dataAuth.getDictItemCodes()) {
                if (code.equals(value)) {
                    return true;
                }
            }
        }

        if (dataAuth.getAuthIds() != null && dataAuth.getAuthIds().length > 0) {
            return dataAuthorityService.validate(dataAuth.getAuthIds(), value);
        }

        return false;
    }

}
