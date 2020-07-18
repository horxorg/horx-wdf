package org.horx.wdf.sys.manager.impl;

import org.apache.commons.lang3.StringUtils;
import org.horx.common.collection.Tree;
import org.horx.wdf.common.entity.SortParam;
import org.horx.wdf.common.enums.SortEnum;
import org.horx.wdf.common.mybatis.entity.SortRowBounds;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.domain.Org;
import org.horx.wdf.sys.dto.dataauth.OrgAuthDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.OrgQueryDTO;
import org.horx.wdf.sys.exception.PermissionDeniedException;
import org.horx.wdf.common.extension.level.LevelCodeGenerator;
import org.horx.wdf.sys.manager.OrgManager;
import org.horx.wdf.sys.mapper.OrgMapper;
import org.horx.wdf.sys.support.datalog.DataLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 机构Manager实现。
 * @since 1.0
 */
@Component("orgManager")
public class OrgManagerImpl implements OrgManager {

    private static final Long ROOT_ID = -1L;
    private static final String ORG_LEVEL_CODE = "wdfOrg";

    @Autowired
    private OrgMapper orgMapper;

    @Autowired
    private LevelCodeGenerator levelCodeGenerator;


    @Override
    @Transactional(readOnly = true)
    public Org getById(Long id) {
        Org org = orgMapper.selectById(id);
        if (org != null && !ROOT_ID.equals(org.getParentId())) {
            Org parentOrg = orgMapper.selectById(org.getParentId());
            if (parentOrg != null) {
                org.setParentName(parentOrg.getName());
            }
        }
        return org;
    }


    @Override
    @Transactional(readOnly = true)
    public Org getByIdAuthorized(Long id, SysDataAuthDTO sysDataAuth) {
        validateInner(sysDataAuth, id);
        return getById(id);
    }

    @Override
    public List<Org> query(OrgQueryDTO orgQuery, SortParam sortParam) {
        return orgMapper.select(orgQuery, new SortRowBounds(sortParam));
    }

    @Override
    public Tree<Org, Long> queryForTree(OrgQueryDTO orgQuery) {
        SortParam sortParam = new SortParam();
        sortParam.setSortField(new String[] {"displaySeq"});
        sortParam.setSortOrder(new String[] {SortEnum.ASC.name()});
        SortRowBounds sortRowBounds = new SortRowBounds(sortParam);
        List<Org> list = orgMapper.select(orgQuery, sortRowBounds);
        Tree<Org, Long> tree = new Tree<>();
        for (Org org : list) {
            tree.addNode(org, org.getId(), org.getParentId());
        }

        tree.buildTree(ROOT_ID);
        tree.upgradeNoRarentNode();

        return tree;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.org}", paramIndex = {0})
    public void create(Org org, SysDataAuthDTO sysDataAuth) {
        if (org.getParentId() != null && sysDataAuth != null && sysDataAuth.getOrgAuth() != null) {
            validateInner(sysDataAuth, org.getParentId());
        }

        if (StringUtils.isEmpty(org.getCode())) {
            org.setDelRid(-System.nanoTime());
        }

        if (org.getParentId() == null) {
            org.setParentId(ROOT_ID);
        }

        int levelNum = 1;
        Org parentOrg = null;
        if (!ROOT_ID.equals(org.getParentId())) {
            parentOrg = orgMapper.selectById(org.getParentId());
            if (parentOrg == null) {
                throw new RuntimeException("父节点" + org.getParentId() + "不存在");
            }

            levelNum = parentOrg.getLevelNum() + 1;
        }

        org.setLevelNum(levelNum);
        boolean levelCodeAfterInsert = levelCodeGenerator.generateAfterInsert(ORG_LEVEL_CODE, org.getId());
        if (levelCodeAfterInsert) {
            org.setLevelCode(levelCodeGenerator.getTempLevelCode(ORG_LEVEL_CODE, org.getParentId(),
                    (parentOrg == null) ? null : parentOrg.getLevelCode()));
        } else {
            org.setLevelCode(levelCodeGenerator.getLevelCode(ORG_LEVEL_CODE, org.getParentId(),
                    (parentOrg == null) ? null : parentOrg.getLevelCode(), org.getId()));
        }

        orgMapper.insert(org);

        if (StringUtils.isEmpty(org.getCode()) || levelCodeAfterInsert) {
            Org orgModify = new Org();
            orgModify.setId(org.getId());
            if (StringUtils.isEmpty(org.getCode())) {
                orgModify.setDelRid(org.getId());
            }
            if (levelCodeAfterInsert) {
                orgModify.setLevelCode(levelCodeGenerator.getLevelCode(ORG_LEVEL_CODE, org.getParentId(),
                        (parentOrg == null) ? null : parentOrg.getLevelCode(), org.getId()));
            }
            orgMapper.update(orgModify);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.org}", paramIndex = {0})
    public void modify(Org org, SysDataAuthDTO sysDataAuth) {
        validateInner(sysDataAuth, org.getId());

        if (StringUtils.isEmpty(org.getCode())) {
            org.setDelRid(org.getId());
        } else {
            org.setDelRid(0L);
        }
        orgMapper.update(org);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.org}", paramIndex = {0})
    public void remove(Long[] ids, SysDataAuthDTO sysDataAuth) {
        if (ids == null || ids.length == 0) {
            return;
        }

        for (Long id : ids) {
            validateInner(sysDataAuth, id);
            orgMapper.logicalDelete(id);
        }
    }

    @Override
    public boolean validate(OrgAuthDTO orgAuth, Long orgId) {
        SysDataAuthDTO sysDataAuth = new SysDataAuthDTO();
        sysDataAuth.setOrgAuth(orgAuth);
        int count = orgMapper.countByOrgAuth(sysDataAuth, orgId);
        return count > 0;
    }

    private void validateInner(SysDataAuthDTO sysDataAuth, Long orgId) {
        if (sysDataAuth != null && sysDataAuth.getOrgAuth() != null && !validate(sysDataAuth.getOrgAuth(), orgId)) {
            throw new PermissionDeniedException();
        }
    }
}
