package org.horx.wdf.sys.extension.dataauth;

import org.horx.wdf.sys.enums.DataAuthorityObjTypeEnum;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;

import java.util.List;
import java.util.Map;

/**
 * 数据权限授权视图接口。
 * @since 1.0
 */
public interface DataAuthorityView {

    String getViewName(DataPermissionDefDTO dataPermissionDef);

    List<Map<String, String>> getAuthorityTypeDict(DataPermissionDefDTO dataPermissionDef,
                                                   DataAuthorityObjTypeEnum authorityObjTypeEnum);

    Object getAssignedData(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum);

    boolean checkDataAuthority(DataPermissionDefDTO dataPermissionDef, DataAuthorityDTO dataAuthority,
                               DataAuthorityObjTypeEnum authorityObjTypeEnum);
}
