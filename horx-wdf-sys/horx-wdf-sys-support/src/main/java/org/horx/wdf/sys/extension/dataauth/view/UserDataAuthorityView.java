package org.horx.wdf.sys.extension.dataauth.view;

import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.enums.DataAuthorityObjTypeEnum;
import org.horx.wdf.sys.enums.UserAuthorityTypeEnum;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.extension.dataauth.DataAuthorityView;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户数据权限授权视图。
 * @since 1.0
 */
@Component
public class UserDataAuthorityView implements DataAuthorityView {

    @Autowired
    private DataAuthorityService dataAuthorityService;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private SysConfig sysConfig;

    @Override
    public String getViewName(DataPermissionDefDTO dataPermissionDef) {
        return "sys/dataAuthority/user";
    }

    @Override
    public List<Map<String, String>> getAuthorityTypeDict(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        UserAuthorityTypeEnum[] enums = getUserAuthorityTypeEnum(dataPermissionDef, authorityObjTypeEnum);
        return convert(enums);
    }

    @Override
    public Object getAssignedData(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        return null;
    }

    @Override
    public boolean checkDataAuthority(DataPermissionDefDTO dataPermissionDef, DataAuthorityDTO dataAuthority, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        UserAuthorityTypeEnum[] enums = getUserAuthorityTypeEnum(dataPermissionDef, authorityObjTypeEnum);
        for (UserAuthorityTypeEnum item : enums) {
            if (item.getCode().equals(dataAuthority.getAuthorityType())) {
                return true;
            }
        }

        return false;
    }

    private UserAuthorityTypeEnum[] getUserAuthorityTypeEnum(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        if (authorityObjTypeEnum == DataAuthorityObjTypeEnum.DEFAULT || sysConfig.isAdmin(sysContextHolder.getUser())) {
            return UserAuthorityTypeEnum.values();
        }
        Long[] roleIds = sysContextHolder.getRoleIdsByPermissionCode();
        List<DataAuthorityDTO> authorityList = dataAuthorityService.queryAdminRoleDataAuthority(roleIds, dataPermissionDef.getId());
        int grade = 0;
        for (DataAuthorityDTO authority : authorityList) {
            int tempGrade = 0;
            if (authority.getAuthorityType().equals(UserAuthorityTypeEnum.CURRENT_USER.getCode())) {
                tempGrade = 1;
            } else if (authority.getAuthorityType().equals(UserAuthorityTypeEnum.ALL.getCode())) {
                tempGrade = 2;
            }
            if (tempGrade > grade) {
                grade = tempGrade;
            }
        }

        if (grade == 2) {
            return UserAuthorityTypeEnum.values();
        }

        UserAuthorityTypeEnum[] enums = null;
        if (grade == 1) {
            enums = new UserAuthorityTypeEnum[]{UserAuthorityTypeEnum.FORBIDDEN, UserAuthorityTypeEnum.CURRENT_USER};
        } else {
            enums = new UserAuthorityTypeEnum[]{UserAuthorityTypeEnum.FORBIDDEN};
        }

        return enums;
    }

    private List<Map<String, String>> convert(UserAuthorityTypeEnum[] enums) {
        List<Map<String, String>> list = new ArrayList<>(enums.length);
        for (UserAuthorityTypeEnum item : enums) {
            Map<String, String> map = new HashMap<>();
            map.put("code", item.getCode());
            map.put("name", msgTool.getMsg(item.getMsgKey()));
            list.add(map);
        }
        return list;
    }
}
