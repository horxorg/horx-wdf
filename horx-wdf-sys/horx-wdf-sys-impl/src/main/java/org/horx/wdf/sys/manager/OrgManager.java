package org.horx.wdf.sys.manager;

import org.horx.common.collection.Tree;
import org.horx.wdf.common.entity.SortParam;
import org.horx.wdf.sys.domain.Org;
import org.horx.wdf.sys.dto.dataauth.OrgAuthDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.OrgQueryDTO;

import java.util.List;

/**
 * 机构Manager。
 * @since 1.0
 */
public interface OrgManager {

    Org getById(Long id);

    Org getByIdAuthorized(Long id, SysDataAuthDTO sysDataAuth);

    List<Org> query(OrgQueryDTO orgQuery, SortParam sortParam);

    Tree<Org, Long> queryForTree(OrgQueryDTO orgQuery);

    void create(Org org, SysDataAuthDTO sysDataAuth);

    void modify(Org org, SysDataAuthDTO sysDataAuth);

    void remove(Long[] ids, SysDataAuthDTO sysDataAuth);

    boolean validate(OrgAuthDTO orgAuth, Long orgId);
}
