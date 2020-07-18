package org.horx.wdf.sys.extension.dataauth.validator;

import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.enums.DataValidationScopeEnum;
import org.horx.wdf.sys.enums.UserAuthorityTypeEnum;
import org.horx.wdf.sys.extension.dataauth.DataValidator;
import org.horx.wdf.sys.dto.dataauth.UserAuthDTO;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户数据权限验证器。
 * @since 1.0
 */
@Component
public class UserValidator implements DataValidator<UserAuthDTO, Long> {

    @Autowired
    private SysContextHolder sysContextHolder;

    @Override
    public UserAuthDTO genDataAuth(DataPermissionDefDTO dataPermissionDef, List<DataAuthorityDTO> list) {
        UserAuthDTO userAuth = new UserAuthDTO();
        if (list == null || list.size() == 0) {
            userAuth.setScope(DataValidationScopeEnum.ALL.getCode());
            return userAuth;
        }

        for (DataAuthorityDTO dataAuthority : list) {
            if (UserAuthorityTypeEnum.ALL.getCode().equals(dataAuthority.getAuthorityType())) {
                userAuth.setScope(DataValidationScopeEnum.ALL.getCode());
                return userAuth;
            } else if (UserAuthorityTypeEnum.CURRENT_USER.getCode().equals(dataAuthority.getAuthorityType())) {
                Long[] userIds = new Long[1];
                userIds[0] = sysContextHolder.getUserId();
                userAuth.setUserIds(userIds);
            }
        }

        if (userAuth.getAuthIds() != null && userAuth.getAuthIds().length > 0 ||
                userAuth.getUserIds() != null && userAuth.getUserIds().length > 0) {
            userAuth.setScope(DataValidationScopeEnum.PART.getCode());
        }

        return userAuth;
    }

    @Override
    public boolean validate(UserAuthDTO dataAuth, Long value) {
        if (dataAuth == null || dataAuth.getScope() == DataValidationScopeEnum.ALL.getCode()) {
            return true;
        } else if (dataAuth.getScope() == DataValidationScopeEnum.FORBIDDEN.getCode()) {
            return false;
        }

        if (dataAuth.getUserIds() != null && dataAuth.getUserIds().length > 0) {
            for (Long userId : dataAuth.getUserIds()) {
                if (userId.equals(value)) {
                    return true;
                }
            }
        }

        return false;
    }

}
