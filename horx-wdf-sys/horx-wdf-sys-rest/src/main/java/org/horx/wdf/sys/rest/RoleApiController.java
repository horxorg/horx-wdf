package org.horx.wdf.sys.rest;

import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.lang3.StringUtils;
import org.horx.common.collection.Tree;
import org.horx.common.collection.TreeNode;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.common.utils.JsonUtils;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.annotation.ArgDataAuth;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.enums.CheckedTypeEnum;
import org.horx.wdf.sys.converter.query.RoleQueryVoConverter;
import org.horx.wdf.sys.converter.RoleVoConverter;
import org.horx.wdf.sys.dto.MenuAuthorityDTO;
import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.RoleDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.RoleWithAuthDTO;
import org.horx.wdf.sys.dto.wrapper.SaveMenuPermissionDTO;
import org.horx.wdf.sys.enums.MenuAuthorityObjTypeEnum;
import org.horx.wdf.sys.dto.query.RoleQueryDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.service.AccessPermissionService;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.service.MenuService;
import org.horx.wdf.sys.service.RoleService;
import org.horx.wdf.sys.vo.RoleVO;
import org.horx.wdf.sys.vo.query.RoleQueryVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色API控制器。
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sys/role")
public class RoleApiController {
    private static final Logger logger = LoggerFactory.getLogger(RoleApiController.class);

    @Autowired
    private RoleService roleService;

    @Autowired
    private AccessPermissionService accessPermissionService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private MsgTool msgTool;

    @Autowired
    private SysConfig sysConfig;

    @Autowired
    private RoleVoConverter roleVoConverter;

    @Autowired
    private RoleQueryVoConverter roleQueryVoConverter;


    @AccessPermission("sys.role.query")
    @PostMapping("/paginationQuery")
    public PaginationResult<RoleVO> paginationQuery(@ArgEntity RoleQueryVO query, PaginationParam paginationParam) {
        RoleQueryDTO roleQueryDTO = roleQueryVoConverter.fromVo(query);
        PaginationQuery<RoleQueryDTO> paginationQuery = new PaginationQuery<>(roleQueryDTO, paginationParam);
        PaginationResult<RoleDTO> paginationResult = roleService.paginationQuery(paginationQuery);
        PaginationResult<RoleVO> voPaginationResult = PaginationResult.copy(paginationResult);
        voPaginationResult.setData(roleVoConverter.toVoList(paginationResult.getData()));
        return voPaginationResult;
    }

    @AccessPermission("sys.role.usable")
    @PostMapping("/usable/paginationQuery")
    public PaginationResult<RoleVO> paginationQueryUsable(@ArgEntity RoleQueryVO query, PaginationParam paginationParam) {
        RoleQueryDTO roleQueryDTO = roleQueryVoConverter.fromVo(query);
        roleQueryDTO.setEnabled(true);
        PaginationQuery<RoleQueryDTO> paginationQuery = new PaginationQuery<>(roleQueryDTO, paginationParam);
        PaginationResult<RoleDTO> paginationResult = roleService.paginationQueryUsable(paginationQuery);
        PaginationResult<RoleVO> voPaginationResult = PaginationResult.copy(paginationResult);
        voPaginationResult.setData(roleVoConverter.toVoList(paginationResult.getData()));
        return voPaginationResult;
    }

    @AccessPermission("sys.role.query")
    @GetMapping("/{id}")
    public Result<RoleVO> getById(@PathVariable("id") Long id, @RequestParam(required = false) String usable, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        RoleDTO dto = ("1".equals(usable)) ? roleService.getByIdUsable(id, sysDataAuth) : roleService.getByIdAuthorized(id, sysDataAuth);
        RoleVO vo = roleVoConverter.toVo(dto);
        return new Result<>(vo);
    }

    @AccessPermission("sys.role.query")
    @GetMapping("/usable/{id}")
    public Result<RoleVO> getByIdUsable(@PathVariable("id") Long id, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        RoleDTO dto = roleService.getByIdUsable(id, sysDataAuth);
        RoleVO vo = roleVoConverter.toVo(dto);
        return new Result<>(vo);
    }

    @AccessPermission("sys.role.create")
    @PostMapping()
    public Result<Long> create(@ArgEntity(create = true) RoleVO roleVO,
                               @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        Result<Long> result = checkOrgId(roleVO);
        if (result != null) {
            return result;
        }

        RoleWithAuthDTO roleWithAuthDTO = new RoleWithAuthDTO();
        roleWithAuthDTO.setRole(roleVoConverter.fromVo(roleVO));
        roleWithAuthDTO.setSysDataAuth(sysDataAuth);

        Long id = roleService.create(roleWithAuthDTO);
        return new Result(id);
    }

    @AccessPermission("sys.role.modify")
    @PutMapping("/{id}")
    public Result modify(@ArgEntity(modify = true) RoleVO roleVO,
                         @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        Result<Long> result = checkOrgId(roleVO);
        if (result != null) {
            return result;
        }

        RoleWithAuthDTO roleWithAuthDTO = new RoleWithAuthDTO();
        roleWithAuthDTO.setRole(roleVoConverter.fromVo(roleVO));
        roleWithAuthDTO.setSysDataAuth(sysDataAuth);

        roleService.modify(roleWithAuthDTO);
        return new Result();
    }

    @AccessPermission("sys.role.remove")
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        BatchWithSysAuthDTO batchWithAuthDTO = new BatchWithSysAuthDTO();
        batchWithAuthDTO.setIds(ids);
        batchWithAuthDTO.setSysDataAuth(sysDataAuth);
        roleService.remove(batchWithAuthDTO);
        return new Result();
    }

    @AccessPermission("sys.role.usable")
    @GetMapping("/queryMenu")
    public Result<List<Map<String, Object>>> queryMenu(@RequestParam Long id, @RequestParam(required = false) String usable,
                                                       @RequestParam(required = false) String objType, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        Result<List<Map<String, Object>>> result = new Result<>();
        RoleDTO roleDTO = ("1".equals(usable)) ? roleService.getByIdUsable(id, sysDataAuth) : roleService.getByIdAuthorized(id, sysDataAuth);
        if (roleDTO == null) {
            result.setCode(ErrorCodeEnum.A0300.getCode());
            result.setMsg(msgTool.getMsg("common.err.forbidden"));
            return result;
        }

        Tree<MenuDTO, Long> tree = genMenuTree(id, objType);
        List<TreeNode<MenuDTO, Long>> roots = tree.getRoots();
        List<Map<String, Object>> list = new ArrayList<>(roots.size());

        for (TreeNode<MenuDTO, Long> node : roots) {
            Map<String, Object> map = convertMenuNode(node);
            if (map != null) {
                list.add(map);
            }
        }

        result.setData(list);
        return result;
    }



    @AccessPermission("sys.role.edit")
    @PutMapping("/menu/{roleId}")
    public Result saveMenuPermission(@PathVariable Long roleId, @RequestParam String objType,
                         @RequestParam("menuPermissions") String menuPermissions,
                         @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        Map<Long, Integer> menuMap = parseMenuMap(menuPermissions);

        if (!CollectionUtils.isEmpty(menuMap)) {
            // 检查权限
            Map<Long, Integer> menuMapNew = new HashMap<>();
            Tree<MenuDTO, Long> tree = genMenuTree(roleId, objType);
            for (Map.Entry<Long, Integer> entry : menuMap.entrySet()) {
                if (entry.getValue() == CheckedTypeEnum.UNCHECKED.getCode()) {
                    continue;
                }

                TreeNode<MenuDTO, Long> node = tree.getNode(entry.getKey());
                if (node == null) {
                    continue;
                }

                if (entry.getValue() == CheckedTypeEnum.CHECKED.getCode()) {
                    node.overlyingCheck();
                } else if (entry.getValue() == CheckedTypeEnum.CHECKED_ALL.getCode()) {
                    node.overlyingCheckAll();
                }
            }

            for (TreeNode<MenuDTO, Long> node : tree.getRoots()) {
                convertCheckedNode(node, menuMapNew);
            }

            menuMap = menuMapNew;
        }

        MenuAuthorityObjTypeEnum menuAuthorityObjTypeEnum = (MenuAuthorityObjTypeEnum.ADMIN_ROLE.getCode().equals(objType)) ? MenuAuthorityObjTypeEnum.ADMIN_ROLE : MenuAuthorityObjTypeEnum.ROLE;
        SaveMenuPermissionDTO saveMenuPermissionDTO = new SaveMenuPermissionDTO();
        saveMenuPermissionDTO.setMenuAuthorityObjType(menuAuthorityObjTypeEnum.getCode());
        saveMenuPermissionDTO.setObjId(roleId);
        saveMenuPermissionDTO.setMenuMap(menuMap);
        saveMenuPermissionDTO.setSysDataAuth(sysDataAuth);
        roleService.saveMenuPermission(saveMenuPermissionDTO);
        return new Result();
    }

    @AccessPermission("sys.role.query")
    @GetMapping("/menu/{roleId}")
    public Result<Map<Long, Integer>> queryForMenu(@PathVariable Long roleId, @RequestParam(required = false) String usable, @RequestParam String objType, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        Result<Map<Long, Integer>> result = new Result<>();
        if (roleId == null) {
            logger.error("roleId不允许为空");
            result.setCode(ErrorCodeEnum.A0430.getCode());
            result.setMsg(msgTool.getMsg("common.err.param"));
            return result;
        }

        RoleDTO roleDTO = ("1".equals(usable)) ? roleService.getByIdUsable(roleId, sysDataAuth) : roleService.getByIdAuthorized(roleId, sysDataAuth);
        if (roleDTO == null) {
            result.setCode(ErrorCodeEnum.A0300.getCode());
            result.setMsg(msgTool.getMsg("common.err.forbidden"));
            return result;
        }

        MenuAuthorityObjTypeEnum menuAuthorityObjTypeEnum = (MenuAuthorityObjTypeEnum.ADMIN_ROLE.getCode().equals(objType)) ? MenuAuthorityObjTypeEnum.ADMIN_ROLE : MenuAuthorityObjTypeEnum.ROLE;
        List<MenuAuthorityDTO> list = roleService.queryForMenu(new Long[] {roleId}, menuAuthorityObjTypeEnum.getCode());
        Map<Long, Integer> map = new HashMap<>();
        for (MenuAuthorityDTO item : list) {
            map.put(item.getMenuId(), item.getCheckedType());
        }
        result.setData(map);
        return result;
    }

    @AccessPermission("sys.role.detail")
    @GetMapping("/permission/adminRole/{roleId}")
    public Result<Map<String, Boolean>> queryAdminRolePermission(@PathVariable Long roleId, @ArgDataAuth SysDataAuthDTO sysDataAuth) {
        Result<Map<String, Boolean>> result = new Result<>();
        if (roleId == null) {
            logger.error("roleId不允许为空");
            result.setCode(ErrorCodeEnum.A0430.getCode());
            result.setMsg(msgTool.getMsg("common.err.param"));
            return result;
        }

        RoleDTO roleDTO = roleService.getByIdAuthorized(roleId, sysDataAuth);
        if (roleDTO == null) {
            result.setCode(ErrorCodeEnum.A0300.getCode());
            result.setMsg(msgTool.getMsg("common.err.forbidden"));
            return result;
        }

        Map<String, Boolean> map = new HashMap<>();
        boolean[] arr = accessPermissionService.isPermissionAllowedForRoleBatch(roleId, new String[]{"sys.role.edit"});
        map.put("sys.role.edit", arr[0]);

        boolean dataAuthority = accessPermissionService.isPermissionAllowedForUser(sysContextHolder.getUserId(), "sys.dataAuthority.role");
        map.put("sys.dataAuthority.role", dataAuthority);

        result.setData(map);
        return result;
    }

    private Map<Long, Integer> parseMenuMap(String menuPermissions) {
        Map<Long, Integer> menuMap = null;
        if (StringUtils.isNotEmpty(menuPermissions)) {
            menuMap = JsonUtils.fromJson(menuPermissions, new TypeReference<Map<Long, Integer>>(){});
        }
        return menuMap;
    }

    private Result<Long> checkOrgId(RoleVO role) {
        Long orgId = sysContextHolder.getUserOrgId();
        if (orgId != null && role.getOrgId() == null) {
            return new Result<>(ErrorCodeEnum.A0430.getCode(), msgTool.getMsg("sys.role.org") + ":" + msgTool.getMsg("common.err.param.notEmpty", ""));
        }

        return null;
    }

    private Tree<MenuDTO, Long> genMenuTree(Long id, String objType) {
        List<MenuDTO> list = menuService.query(null);
        Tree<MenuDTO, Long> tree = new Tree<>();
        for (MenuDTO menu : list) {
            tree.addNode(menu, menu.getId(), menu.getParentId());
        }
        tree.buildTree(-1L);
        List<TreeNode<MenuDTO, Long>> roots = tree.getRoots();

        if (CollectionUtils.isEmpty(roots)) {
            return tree;
        }

        if (sysConfig.isAdmin(sysContextHolder.getUser())) {
            for (TreeNode<MenuDTO, Long> node : roots) {
                node.checkAll();
            }
        } else {
            MenuAuthorityObjTypeEnum menuAuthorityObjTypeEnum = (MenuAuthorityObjTypeEnum.ADMIN_ROLE.getCode().equals(objType)) ? MenuAuthorityObjTypeEnum.ADMIN_ROLE : MenuAuthorityObjTypeEnum.ROLE;
            List<MenuAuthorityDTO> menuAuthorityList = roleService.queryForMenu(new Long[]{id}, menuAuthorityObjTypeEnum.getCode());
            if (CollectionUtils.isEmpty(menuAuthorityList)) {
                return tree;
            }

            for (MenuAuthorityDTO menuAuthority : menuAuthorityList) {
                if (CheckedTypeEnum.CHECKED.getCode() == menuAuthority.getCheckedType()) {
                    TreeNode<MenuDTO, Long> node = tree.getNode(menuAuthority.getMenuId());
                    if (node != null) {
                        node.check();
                    }
                } else if (CheckedTypeEnum.CHECKED_ALL.getCode() == menuAuthority.getCheckedType()) {
                    TreeNode<MenuDTO, Long> node = tree.getNode(menuAuthority.getMenuId());
                    if (node != null) {
                        node.checkAll();
                    }
                }
            }
        }

        return tree;
    }

    private Map<String, Object> convertMenuNode(TreeNode<MenuDTO, Long> node) {
        if (node.getCheckStatus() == TreeNode.UNCHECKED && !node.hasCheckedSubNode()) {
            return null;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("id", node.getId());
        map.put("name", node.getData().getName());
        if (node.getCheckStatus() == TreeNode.UNCHECKED) {
            map.put("disabled", true);
        }

        List<TreeNode<MenuDTO, Long>> children = node.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            List<Map<String, Object>> mapList = null;
            for (TreeNode<MenuDTO, Long> child : children) {
                Map<String, Object> childMap = convertMenuNode(child);
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

    private void convertCheckedNode(TreeNode<MenuDTO, Long> node, Map<Long, Integer> newMap) {
        if (node.getOverlyingCheckStatus() == TreeNode.CHECKED_ALL) {
            newMap.put(node.getId(), CheckedTypeEnum.CHECKED_ALL.getCode());
            return;
        }

        if (node.getOverlyingCheckStatus() == TreeNode.CHECKED) {
            newMap.put(node.getId(), CheckedTypeEnum.CHECKED.getCode());
        }

        List<TreeNode<MenuDTO, Long>> children = node.getChildren();
        if (!CollectionUtils.isEmpty(children)) {
            for (TreeNode<MenuDTO, Long> sub : children) {
                convertCheckedNode(sub, newMap);
            }
        }
    }
}
