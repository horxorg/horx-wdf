package org.horx.wdf.sys.rest;

import org.horx.common.collection.Tree;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.arg.annotation.ArgEntity;
import org.horx.wdf.sys.annotation.AccessPermission;
import org.horx.wdf.sys.converter.query.MenuQueryVoConverter;
import org.horx.wdf.sys.converter.MenuVoConverter;
import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;
import org.horx.wdf.sys.service.AccessPermissionService;
import org.horx.wdf.sys.extension.context.SysContextHolder;
import org.horx.wdf.sys.service.MenuService;
import org.horx.wdf.sys.vo.MenuVO;
import org.horx.wdf.sys.vo.query.MenuQueryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单API控制器。
 * @since 1.0
 */
@RestController
@RequestMapping("/api/sys/menu")
public class MenuApiController {
    private static final Long ROOT_ID = -1L;

    @Autowired
    private MenuService menuService;

    @Autowired
    private AccessPermissionService accessPermissionService;

    @Autowired
    private SysContextHolder sysContextHolder;

    @Autowired
    private MenuVoConverter menuVoConverter;

    @Autowired
    private MenuQueryVoConverter menuQueryVoConverter;

    @AccessPermission("sys.menu.query")
    @PostMapping("/query")
    public Result<List<MenuVO>> query(@ArgEntity MenuQueryVO query, PaginationParam paginationParam) {
        MenuQueryDTO menuQueryDTO = menuQueryVoConverter.fromVo(query);
        PaginationQuery<MenuQueryDTO> paginationQuery = new PaginationQuery<>(menuQueryDTO, paginationParam);
        List<MenuDTO> list = menuService.query(paginationQuery);
        List<MenuVO> voList = menuVoConverter.toVoList(list);
        return new Result<>(voList);
    }

    @PostMapping("/queryForTree")
    public Result<List<MenuVO>> queryForTree(@ArgEntity MenuQueryVO query, PaginationParam paginationParam) {
        Result<List<MenuVO>> result = query(query, paginationParam);
        Tree<MenuVO, Long> tree = new Tree<>();
        for (MenuVO menu : result.getData()) {
            tree.addNode(menu, menu.getId(), menu.getParentId());
        }

        tree.buildTree(ROOT_ID);
        tree.upgradeNoRarentNode();

        List<MenuVO> list = tree.convertToTreeData();
        result.setData(list);

        return result;
    }

    @GetMapping("/queryForAuthorizedMenu")
    public Result<List<MenuVO>> queryForAuthorizedMenu() {
        MenuQueryDTO menuQuery = new MenuQueryDTO();
        menuQuery.setType("01");
        menuQuery.setVisible(true);
        menuQuery.setEnabled(true);
        List<MenuDTO> dtoList = accessPermissionService.queryAuthorizedMenuForUser(sysContextHolder.getUserId(), menuQuery);

        Tree<MenuVO, Long> tree = new Tree<>();
        for (MenuDTO menuDTO : dtoList) {
            MenuVO menuVO = menuVoConverter.toVo(menuDTO);
            tree.addNode(menuVO, menuVO.getId(), menuVO.getParentId());
        }
        tree.buildTree(ROOT_ID);
        List<MenuVO> voList = tree.convertToTreeData();

        return new Result<>(voList);
    }

    @AccessPermission("sys.menu.query")
    @GetMapping("/{id}")
    public Result<MenuVO> getById(@PathVariable("id") Long id) {
        MenuDTO menuDTO = menuService.getById(id);
        MenuVO menuVO = menuVoConverter.toVo(menuDTO);
        return new Result<>(menuVO);
    }

    @AccessPermission("sys.menu.create")
    @PostMapping()
    public Result<Long> create(@ArgEntity(create = true) MenuVO menuVO) {
        MenuDTO menuDTO = menuVoConverter.fromVo(menuVO);
        Long menuId = menuService.create(menuDTO);
        return new Result(menuId);
    }

    @AccessPermission("sys.menu.modify")
    @PutMapping("/{id}")
    public Result modify(@ArgEntity(modify = true) MenuVO menuVO) {
        MenuDTO menuDTO = menuVoConverter.fromVo(menuVO);
        menuService.modify(menuDTO);
        return new Result();
    }

    @AccessPermission("sys.menu.remove")
    @DeleteMapping("/{ids}")
    public Result remove(@PathVariable Long[] ids) {
        menuService.remove(ids);
        return new Result();
    }
}
