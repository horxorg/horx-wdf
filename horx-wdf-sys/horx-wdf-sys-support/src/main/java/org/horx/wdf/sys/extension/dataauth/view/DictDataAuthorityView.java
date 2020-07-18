package org.horx.wdf.sys.extension.dataauth.view;

import org.horx.common.collection.Tree;
import org.horx.common.collection.TreeNode;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.enums.CheckedTypeEnum;
import org.horx.wdf.sys.enums.DataAuthorityObjTypeEnum;
import org.horx.wdf.sys.enums.DictAuthorityTypeEnum;
import org.horx.wdf.sys.dto.DataAuthorityDTO;
import org.horx.wdf.sys.dto.DataAuthorityDetailDTO;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.dto.DictDTO;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.extension.dataauth.DataAuthorityView;
import org.horx.wdf.sys.service.DataAuthorityService;
import org.horx.wdf.sys.service.DataPermissionService;
import org.horx.wdf.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 字典项数据权限授权视图。
 * @since 1.0
 */
@Component
public class DictDataAuthorityView implements DataAuthorityView {

    @Autowired
    private DataPermissionService dataPermissionService;

    @Autowired
    private DataAuthorityService dataAuthorityService;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private DictService dictService;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private SysConfig sysConfig;

    @Override
    public String getViewName(DataPermissionDefDTO dataPermissionDef) {
        DictDTO dict = dictService.getById(dataPermissionDef.getObjId());
        if (dict.getTreeData()) {
            return "sys/dataAuthority/dictTree";
        }
        return "sys/dataAuthority/dictList";
    }

    @Override
    public List<Map<String, String>> getAuthorityTypeDict(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        DictAuthorityTypeEnum[] enums = getDictAuthorityTypeEnum(dataPermissionDef, authorityObjTypeEnum);
        return convert(enums);
    }

    @Override
    public Object getAssignedData(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        DictDTO dict = dictService.getById(dataPermissionDef.getObjId());
        if (dict.getTreeData()) {
            Tree<DictItemDTO, Long> tree = getDictTree(dataPermissionDef, authorityObjTypeEnum);
            List<TreeNode<DictItemDTO, Long>> roots = tree.getRoots();
            List<Map<String, Object>> list = new ArrayList<>(roots.size());

            for (TreeNode<DictItemDTO, Long> node : roots) {
                Map<String, Object> map = convertNode(node);
                if (map != null) {
                    list.add(map);
                }
            }
            return  list;
        } else {
            List<DictItemDTO> list = getDictList(dataPermissionDef, authorityObjTypeEnum);
            return list;
        }
    }

    @Override
    public boolean checkDataAuthority(DataPermissionDefDTO dataPermissionDef, DataAuthorityDTO dataAuthority, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        DictAuthorityTypeEnum[] enums = getDictAuthorityTypeEnum(dataPermissionDef, authorityObjTypeEnum);
        boolean exist = false;
        for (DictAuthorityTypeEnum item : enums) {
            if (item.getCode().equals(dataAuthority.getAuthorityType())) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            return false;
        }

        if (DictAuthorityTypeEnum.ASSIGNED.getCode().equals(dataAuthority.getAuthorityType())) {
            DictDTO dict = dictService.getById(dataPermissionDef.getObjId());
            if (dict.getTreeData()) {
                List<DataAuthorityDetailDTO> newList = genAllowedData(dataPermissionDef, authorityObjTypeEnum, dataAuthority.getDetailList());
                dataAuthority.setDetailList(newList);
            } else {
                List<DictItemDTO> list = getDictList(dataPermissionDef, authorityObjTypeEnum);
                List<DataAuthorityDetailDTO> detailList = dataAuthority.getDetailList();
                List<DataAuthorityDetailDTO> newList = new ArrayList<>(detailList.size());
                for (DataAuthorityDetailDTO detail : detailList) {
                    for (DictItemDTO item : list) {
                        if (item.getCode().equals(detail.getAuthorityValue())) {
                            newList.add(detail);
                            break;
                        }
                    }
                }
                dataAuthority.setDetailList(newList);
            }
        }


        return true;
    }

    private DictAuthorityTypeEnum[] getDictAuthorityTypeEnum(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum authorityObjTypeEnum) {
        if (authorityObjTypeEnum == DataAuthorityObjTypeEnum.DEFAULT || sysConfig.isAdmin(sysContextHolder.getUser())) {
            return DictAuthorityTypeEnum.values();
        }

        Long[] roleIds = sysContextHolder.getRoleIdsByPermissionCode();
        List<DataAuthorityDTO> authorityList = dataAuthorityService.queryAdminRoleDataAuthority(roleIds, dataPermissionDef.getId());
        int grade = 0;
        for (DataAuthorityDTO authority : authorityList) {
            int tempGrade = 0;
            if (authority.getAuthorityType().equals(DictAuthorityTypeEnum.ASSIGNED.getCode())) {
                tempGrade = 1;
            } else if (authority.getAuthorityType().equals(DictAuthorityTypeEnum.ALL.getCode())) {
                tempGrade = 2;
            }
            if (tempGrade > grade) {
                grade = tempGrade;
            }
        }

        if (grade == 2) {
            return DictAuthorityTypeEnum.values();
        }

        DictAuthorityTypeEnum[] enums;
        if (grade == 1) {
            enums = new DictAuthorityTypeEnum[]{DictAuthorityTypeEnum.FORBIDDEN, DictAuthorityTypeEnum.ASSIGNED};
        } else {
            enums = new DictAuthorityTypeEnum[]{DictAuthorityTypeEnum.FORBIDDEN};
        }
        return enums;
    }

    private List<Map<String, String>> convert(DictAuthorityTypeEnum[] enums) {
        List<Map<String, String>> list = new ArrayList<>(enums.length);
        for (DictAuthorityTypeEnum item : enums) {
            Map<String, String> map = new HashMap<>();
            map.put("code", item.getCode());
            map.put("name", msgTool.getMsg(item.getMsgKey()));
            list.add(map);
        }
        return list;
    }

    private List<DictItemDTO> getDictList(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum dataAuthorityObjTypeEnum) {
        List<DictItemDTO> list = dictService.queryEnabledItemListByDictId(dataPermissionDef.getObjId());
        if (CollectionUtils.isEmpty(list) || dataAuthorityObjTypeEnum == DataAuthorityObjTypeEnum.DEFAULT) {
            return list;
        }

        DictAuthorityTypeEnum[] enums = getDictAuthorityTypeEnum(dataPermissionDef, dataAuthorityObjTypeEnum);
        for (DictAuthorityTypeEnum item : enums) {
            if (item == DictAuthorityTypeEnum.ALL) {
                return list;
            }
        }

        Long[] roleIds = sysContextHolder.getRoleIdsByPermissionCode();
        if (roleIds.length == 0) {
            return new ArrayList<>(0);
        }
        List<DataAuthorityDTO> authorityList = dataAuthorityService.queryAdminRoleDataAuthority(roleIds, dataPermissionDef.getId());
        if (CollectionUtils.isEmpty(authorityList)) {
            return new ArrayList<>(0);
        }
        Long[] authorityIds = new Long[authorityList.size()];
        int i = 0;
        for (DataAuthorityDTO authority : authorityList) {
            authorityIds[i++] = authority.getId();
        }
        List<DataAuthorityDetailDTO> detailList = dataAuthorityService.queryDetailByAuthorityIds(authorityIds);
        if (CollectionUtils.isEmpty(detailList)) {
            return new ArrayList<>(0);
        }

        List<DictItemDTO> newList = new ArrayList<>(detailList.size());
        for (DictItemDTO item : list) {
            for (DataAuthorityDetailDTO detail : detailList) {
                if (item.getCode().equals(detail.getAuthorityValue())) {
                    newList.add(item);
                }
            }
        }

        return newList;
    }

    private Tree<DictItemDTO, Long> getDictTree(DataPermissionDefDTO dataPermissionDef, DataAuthorityObjTypeEnum dataAuthorityObjTypeEnum) {
        List<DictItemDTO> list = dictService.queryEnabledItemListByDictId(dataPermissionDef.getObjId());
        Tree<DictItemDTO, Long> tree = new Tree<>();
        for (DictItemDTO dictItem : list) {
            tree.addNode(dictItem, dictItem.getId(), dictItem.getParentId());
        }
        tree.buildTree(-1L);

        boolean all = false;
        if (sysConfig.isAdmin(sysContextHolder.getUser()) || dataAuthorityObjTypeEnum == DataAuthorityObjTypeEnum.DEFAULT) {
            all = true;
        }

        if (!all) {
            DictAuthorityTypeEnum[] enums = getDictAuthorityTypeEnum(dataPermissionDef, dataAuthorityObjTypeEnum);
            for (DictAuthorityTypeEnum item : enums) {
                if (item == DictAuthorityTypeEnum.ALL) {
                    all = true;
                    break;
                }
            }
        }

        if (all) {
            List<TreeNode<DictItemDTO, Long>> roots = tree.getRoots();
            for (TreeNode<DictItemDTO, Long> node : roots) {
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
            DictItemDTO dictItem = null;
            for (DictItemDTO item : list) {
                if (item.getCode().equals(detail.getAuthorityValue())) {
                    dictItem = item;
                    break;
                }
            }

            if (dictItem != null) {
                TreeNode<DictItemDTO, Long> node = tree.getNode(dictItem.getId());
                if (CheckedTypeEnum.CHECKED.getCode() == detail.getCheckedType()) {
                    node.check();
                } else if (CheckedTypeEnum.CHECKED_ALL.getCode() == detail.getCheckedType()) {
                    node.checkAll();
                }
            }
        }

        return tree;
    }

    private Map<String, Object> convertNode(TreeNode<DictItemDTO, Long> node) {
        if (node.getCheckStatus() == TreeNode.UNCHECKED && !node.hasCheckedSubNode()) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("code", node.getData().getCode());
        map.put("name", node.getData().getName());
        if (node.getCheckStatus() == TreeNode.UNCHECKED) {
            map.put("disabled", true);
        }

        List<TreeNode<DictItemDTO, Long>> children = node.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            List<Map<String, Object>> mapList = null;
            for (TreeNode<DictItemDTO, Long> child : children) {
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
        Tree<DictItemDTO, Long> tree = getDictTree(dataPermissionDef, dataAuthorityObjTypeEnum);
        Collection<TreeNode<DictItemDTO, Long>> allNodes = tree.getAllNodes();
        List<DataAuthorityDetailDTO> newList = new ArrayList<>(detailList.size());
        for (DataAuthorityDetailDTO detail : detailList) {
            for (TreeNode<DictItemDTO, Long> node : allNodes) {
                if (node.getData().getCode().equals(detail.getAuthorityValue())) {
                    if (CheckedTypeEnum.CHECKED.getCode() == detail.getCheckedType()) {
                        node.overlyingCheck();
                    } else if (CheckedTypeEnum.CHECKED_ALL.getCode() == detail.getCheckedType()) {
                        node.overlyingCheckAll();
                    }
                    break;
                }
            }
        }

        List<TreeNode<DictItemDTO, Long>> roots = tree.getRoots();
        for (TreeNode<DictItemDTO, Long> node : roots) {
            convertCheckedNode(node, newList);
        }

        return newList;
    }

    private void convertCheckedNode(TreeNode<DictItemDTO, Long> node, List<DataAuthorityDetailDTO> newList) {
        if (node.getOverlyingCheckStatus() == TreeNode.CHECKED_ALL) {
            DataAuthorityDetailDTO detail = new DataAuthorityDetailDTO();
            detail.setAuthorityValue(node.getData().getCode());
            detail.setCheckedType(CheckedTypeEnum.CHECKED_ALL.getCode());
            newList.add(detail);
            return;
        }

        if (node.getOverlyingCheckStatus() == TreeNode.CHECKED) {
            DataAuthorityDetailDTO detail = new DataAuthorityDetailDTO();
            detail.setAuthorityValue(node.getData().getCode());
            detail.setCheckedType(CheckedTypeEnum.CHECKED.getCode());
            newList.add(detail);
        }

        List<TreeNode<DictItemDTO, Long>> children = node.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (TreeNode<DictItemDTO, Long> sub : children) {
                convertCheckedNode(sub, newList);
            }
        }
    }
}
