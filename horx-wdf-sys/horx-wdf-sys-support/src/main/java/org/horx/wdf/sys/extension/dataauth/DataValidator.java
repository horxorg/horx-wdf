package org.horx.wdf.sys.extension.dataauth;

import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;

import java.util.List;

/**
 * 数据权限验证接口。
 * @since 1.0
 */
public interface DataValidator<K, V> {

    K genDataAuth(DataPermissionDefDTO dataPermissionDef, List<DataAuthorityDTO> list);

    boolean validate(K dataAuth, V value);
}
