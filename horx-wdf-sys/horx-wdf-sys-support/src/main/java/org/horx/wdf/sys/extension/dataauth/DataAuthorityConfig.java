package org.horx.wdf.sys.extension.dataauth;

import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.enums.DataPermissionObjTypeEnum;
import org.horx.wdf.sys.extension.dataauth.validator.DictItemValidator;
import org.horx.wdf.sys.extension.dataauth.validator.OrgValidator;
import org.horx.wdf.sys.extension.dataauth.validator.UserValidator;
import org.horx.wdf.sys.extension.dataauth.view.DictDataAuthorityView;
import org.horx.wdf.sys.extension.dataauth.view.OrgDataAuthorityView;
import org.horx.wdf.sys.extension.dataauth.view.UserDataAuthorityView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据权限授权配置。
 * @since 1.0
 */
@Component
public class DataAuthorityConfig {

    @Autowired
    private DictItemValidator dictItemValidator;

    @Autowired
    private OrgValidator orgValidator;

    @Autowired
    private UserValidator userValidator;

    @Autowired
    private UserDataAuthorityView userDataAuthorityView;

    @Autowired
    private DictDataAuthorityView dictDataAuthorityView;

    @Autowired
    private OrgDataAuthorityView orgDataAuthorityView;

    private Map<String, DataValidator> dataValidatorMap;

    private Map<String, DataAuthorityView> dataAuthorityViewMap;

    public void setDataValidatorMap(Map<String, DataValidator> dataValidatorMap) {
        this.dataValidatorMap = dataValidatorMap;
    }

    @PostConstruct
    public void init() {
        if (dataValidatorMap == null) {
            dataValidatorMap = new HashMap<>();
        }

        if (!dataValidatorMap.containsKey(DataPermissionObjTypeEnum.USER.getCode())) {
            dataValidatorMap.put(DataPermissionObjTypeEnum.USER.getCode(), userValidator);
        }

        if (!dataValidatorMap.containsKey(DataPermissionObjTypeEnum.ORG.getCode())) {
            dataValidatorMap.put(DataPermissionObjTypeEnum.ORG.getCode(), orgValidator);
        }

        if (!dataValidatorMap.containsKey(DataPermissionObjTypeEnum.DICT.getCode())) {
            dataValidatorMap.put(DataPermissionObjTypeEnum.DICT.getCode(), dictItemValidator);
        }

        if (dataAuthorityViewMap == null) {
            dataAuthorityViewMap = new HashMap<>();
        }

        if (!dataAuthorityViewMap.containsKey(DataPermissionObjTypeEnum.USER.getCode())) {
            dataAuthorityViewMap.put(DataPermissionObjTypeEnum.USER.getCode(), userDataAuthorityView);
        }

        if (!dataAuthorityViewMap.containsKey(DataPermissionObjTypeEnum.DICT.getCode())) {
            dataAuthorityViewMap.put(DataPermissionObjTypeEnum.DICT.getCode(), dictDataAuthorityView);
        }

        if (!dataAuthorityViewMap.containsKey(DataPermissionObjTypeEnum.ORG.getCode())) {
            dataAuthorityViewMap.put(DataPermissionObjTypeEnum.ORG.getCode(), orgDataAuthorityView);
        }
    }

    public DataValidator getValidator(DataPermissionDefDTO dataPermissionDef) {
        return dataValidatorMap.get(dataPermissionDef.getObjType());
    }

    public DataValidator getValidatorByVO(DataPermissionObj dataAuth) {
        return dataValidatorMap.get(dataAuth.getDataPermissionObjType());
    }

    public DataAuthorityView getDataAuthorityView(DataPermissionDefDTO dataPermissionDef) {
        return dataAuthorityViewMap.get(dataPermissionDef.getObjType());
    }
}
