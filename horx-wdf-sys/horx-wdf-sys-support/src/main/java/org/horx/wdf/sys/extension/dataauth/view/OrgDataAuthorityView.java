package org.horx.wdf.sys.extension.dataauth.view;

import org.horx.common.collection.Tree;
import org.horx.common.collection.TreeNode;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.enums.CheckedTypeEnum;
import org.horx.wdf.sys.enums.DataAuthorityObjTypeEnum;
import org.horx.wdf.sys.enums.DictAuthorityTypeEnum;
import org.horx.wdf.sys.enums.OrgAuthorityTypeEnum;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.extension.dataauth.DataAuthorityView;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.horx.wdf.sys.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构数据权限授权视图。
 * @since 1.0
 */
@Component
public class OrgDataAuthorityView implements DataAuthorityView {

    @Autowired
    private DataAuthorityService dataAuthorityService;

    @Autowired
    private OrgService orgService;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private SysConfig sysConfig;

    @Override
    public String getViewName(DataPermissionDefDTO dataPermissionDef) {
        return "sys/dataAuthority/org";
    }

    @Override
    public List<Map<String, String>> getAuthorityTypeDict(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        OrgAuthorityTypeEnum[] enums = getOrgAuthorityTypeEnum(dataPermissionDef, authorityObjTypeEnum);
        return convert(enums);
    }

    @Override
    public Object getAssignedData(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        Tree<OrgDTO, Long> tree = getOrgTree(dataPermissionDef, authorityObjTypeEnum);
        List<TreeNode<OrgDTO, Long>> roots = tree.getRoots();
        List<Map<String, Object>> list = new ArrayList<>(roots.size());

        for (TreeNode<OrgDTO, Long> node : roots) {
            Map<String, Object> map = convertNode(node);
            if (map != null) {
                list.add(map);
            }
        }
        return  list;
    }

    @Override
    public boolean checkDataAuthority(DataPermissionDefDTO dataPermissionDef, DataAuthorityDTO dataAuthority, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        OrgAuthorityTypeEnum[] enums = getOrgAuthorityTypeEnum(dataPermissionDef, authorityObjTypeEnum);
        boolean exist = false;
        for (OrgAuthorityTypeEnum item : enums) {
            if (item.getCode().equals(dataAuthority.getAuthorityType())) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            return false;
        }

        if (DictAuthorityTypeEnum.ASSIGNED.getCode().equals(dataAuthority.getAuthorityType())) {
            List<DataAuthorityDetailDTO> newList = genAllowedData(dataPermissionDef, authorityObjTypeEnum, dataAuthority.getDetailList());
            dataAuthority.setDetailList(newList);
        }

        return true;
    }

    private OrgAuthorityTypeEnum[] getOrgAuthorityTypeEnum(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        if (authorityObjTypeEnum == DataAuthorityObjTypeEnum.DEFAULT || sysConfig.isAdmin(sysContextHolder.getUser())) {
            return OrgAuthorityTypeEnum.values();
        }
        Long[] roleIds = sysContextHolder.getRoleIdsByPermissionCode();
        List<DataAuthorityDTO> authorityList = dataAuthorityService.queryAdminRoleDataAuthority(roleIds, dataPermissionDef.getId());
        int grade = 0;
        for (DataAuthorityDTO authority : authorityList) {
            int tempGrade = 0;
            if (authority.getAuthorityType().equals(OrgAuthorityTypeEnum.USER_ORG.getCode()) ||
                    authority.getAuthorityType().equals(OrgAuthorityTypeEnum.USER_ORG_AND_SUBS.getCode())) {
                tempGrade = 1;
            } else if (authority.getAuthorityType().equals(OrgAuthorityTypeEnum.ASSIGNED.getCode())) {
                tempGrade = 2;
            } else if (authority.getAuthorityType().equals(OrgAuthorityTypeEnum.ALL.getCode())) {
                tempGrade = 3;
            }
            if (tempGrade > grade) {
                grade = tempGrade;
            }
        }

        if (grade == 3) {
            return OrgAuthorityTypeEnum.values();
        }

        OrgAuthorityTypeEnum[] enums = null;
        if (grade == 2) {
            enums = new OrgAuthorityTypeEnum[]{OrgAuthorityTypeEnum.FORBIDDEN, OrgAuthorityTypeEnum.USER_ORG,
                    OrgAuthorityTypeEnum.USER_ORG_AND_SUBS, OrgAuthorityTypeEnum.ASSIGNED};
        } else if (grade == 1) {
            enums = new OrgAuthorityTypeEnum[]{OrgAuthorityTypeEnum.FORBIDDEN, OrgAuthorityTypeEnum.USER_ORG,
                    OrgAuthorityTypeEnum.USER_ORG_AND_SUBS};
        } else {
            enums = new OrgAuthorityTypeEnum[]{OrgAuthorityTypeEnum.FORBIDDEN};
        }
        return enums;
    }

    private List<Map<String, String>> convert(OrgAuthorityTypeEnum[] enums) {
        List<Map<String, String>> list = new ArrayList<>(enums.length);
        for (OrgAuthorityTypeEnum item : enums) {
            Map<String, String> map = new HashMap<>();
            map.put("code", item.getCode());
            map.put("name", msgTool.getMsg(item.getMsgKey()));
            list.add(map);
        }
        return list;
    }

    private Tree<OrgDTO, Long> getOrgTree(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum dataAuthorityObjTypeEnum) {
        List<OrgDTO> list = orgService.query(null);
        Tree<OrgDTO, Long> tree = new Tree<>();
        for (OrgDTO org : list) {
            tree.addNode(org, org.getId(), org.getParentId());
        }

        tree.buildTree(-1L);
        tree.upgradeNoRarentNode();

        boolean all = false;
        if (sysConfig.isAdmin(sysContextHolder.getUser()) || dataAuthorityObjTypeEnum == DataAuthorityObjTypeEnum.DEFAULT) {
            all = true;
        }

        if (!all) {
            OrgAuthorityTypeEnum[] enums = getOrgAuthorityTypeEnum(dataPermissionDef, dataAuthorityObjTypeEnum);
            for (OrgAuthorityTypeEnum item : enums) {
                if (item == OrgAuthorityTypeEnum.ALL) {
                    all = true;
                    break;
                }
            }
        }

        if (all) {
            List<TreeNode<OrgDTO, Long>> roots = tree.getRoots();
            for (TreeNode<OrgDTO, Long> node : roots) {
                node.checkAll();
            }
            return tree;
        }

        Long[] roleIds = sysContextHolder.getRoleIdsByPermissionCode();
        if (roleIds.length == 0) {
            return tree;
        }
        List<DataAuthorityDTO> authorityList = dataAuthorityService.queryAdminRoleDataAuthority(roleIds, dataPermissionDef.getId());
        if (CollectionUtils.isEmpty(authorityList)) {
            return tree;
        }
        Long[] authorityIds = new Long[authorityList.size()];
        int i = 0;
        for (DataAuthorityDTO authority : authorityList) {
            authorityIds[i++] = authority.getId();
        }
        List<DataAuthorityDetailDTO> detailList = dataAuthorityService.queryDetailByAuthorityIds(authorityIds);
        if (CollectionUtils.isEmpty(detailList)) {
            return tree;
        }

        for (DataAuthorityDetailDTO detail : detailList) {
            TreeNode<OrgDTO, Long> node = tree.getNode(Long.valueOf(detail.getAuthorityValue()));

            if (node != null) {
                if (CheckedTypeEnum.CHECKED.getCode() == detail.getCheckedType()) {
                    node.check();
                } else if (CheckedTypeEnum.CHECKED_ALL.getCode() == detail.getCheckedType()) {
                    node.checkAll();
                }
            }
        }

        return tree;
    }

    private Map<String, Object> convertNode(TreeNode<OrgDTO, Long> node) {
        if (node.getCheckStatus() == TreeNode.UNCHECKED && !node.hasCheckedSubNode()) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", node.getId());
        map.put("name", node.getData().getName());
        if (node.getCheckStatus() == TreeNode.UNCHECKED) {
            map.put("disabled", true);
        }

        List<TreeNode<OrgDTO, Long>> children = node.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            List<Map<String, Object>> mapList = null;
            for (TreeNode<OrgDTO, Long> child : children) {
                Map<String, Object> childMap = convertNode(child);
                if (childMap != null) {
                    if (mapList == null) {
                        mapList = new ArrayList<>();
                    }
                    mapList.add(childMap);
                }
            }

            if (mapList != null) {
                map.put("children", mapList);
            }
        }

        return map;
    }

    private List<DataAuthorityDetailDTO> genAllowedData(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum dataAuthorityObjTypeEnum, List<DataAuthorityDetailDTO> detailList) {
        Tree<OrgDTO, Long> tree = getOrgTree(dataPermissionDef, dataAuthorityObjTypeEnum);
        List<DataAuthorityDetailDTO> newList = new ArrayList<>(detailList.size());
        for (DataAuthorityDetailDTO detail : detailList) {
            TreeNode<OrgDTO, Long> node = tree.getNode(Long.valueOf(detail.getAuthorityValue()));
            if (node != null) {
                if (CheckedTypeEnum.CHECKED.getCode() == detail.getCheckedType()) {
                    node.overlyingCheck();
                } else if (CheckedTypeEnum.CHECKED_ALL.getCode() == detail.getCheckedType()) {
                    node.overlyingCheckAll();
                }
            }
        }

        List<TreeNode<OrgDTO, Long>> roots = tree.getRoots();
        for (TreeNode<OrgDTO, Long> node : roots) {
            convertCheckedNode(node, newList);
        }

        return newList;
    }

    private void convertCheckedNode(TreeNode<OrgDTO, Long> node, List<DataAuthorityDetailDTO> newList) {
        if (node.getOverlyingCheckStatus() == TreeNode.CHECKED_ALL) {
            DataAuthorityDetailDTO detail = new DataAuthorityDetailDTO();
            detail.setAuthorityValue(String.valueOf(node.getId()));
            detail.setCheckedType(CheckedTypeEnum.CHECKED_ALL.getCode());
            newList.add(detail);
            return;
        }

        if (node.getOverlyingCheckStatus() == TreeNode.CHECKED) {
            DataAuthorityDetailDTO detail = new DataAuthorityDetailDTO();
            detail.setAuthorityValue(String.valueOf(node.getId()));
            detail.setCheckedType(CheckedTypeEnum.CHECKED.getCode());
            newList.add(detail);
        }

        List<TreeNode<OrgDTO, Long>> children = node.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (TreeNode<OrgDTO, Long> sub : children) {
                convertCheckedNode(sub, newList);
            }
        }
    }
}
