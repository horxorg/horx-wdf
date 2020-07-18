package org.horx.wdf.sys.manager.impl;

import org.apache.commons.lang3.StringUtils;
import org.horx.common.collection.Tree;
import org.horx.wdf.common.entity.PagingParam;
import org.horx.wdf.common.enums.SortEnum;
import org.horx.wdf.common.mybatis.entity.PagingRowBounds;
import org.horx.wdf.sys.consts.SysConstants;
import org.horx.wdf.sys.domain.Menu;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;
import org.horx.wdf.common.extension.level.LevelCodeGenerator;
import org.horx.wdf.sys.manager.MenuManager;
import org.horx.wdf.sys.mapper.MenuMapper;
import org.horx.wdf.sys.support.datalog.DataLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 菜单Manager实现。
 * @since 1.0
 */
@Component("menuManager")
public class MenuManagerImpl implements MenuManager {
    private static final Long ROOT_ID = -1L;
    private static final String MENU_LEVEL_CODE = "wdfMenu";

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private LevelCodeGenerator levelCodeGenerator;

    @Override
    public Menu getById(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public List<Menu> query(MenuQueryDTO menuQuery, PagingParam pagingParam) {
        return menuMapper.select(menuQuery, new PagingRowBounds(pagingParam));
    }

    @Override
    public Tree<Menu, Long> queryForTree(MenuQueryDTO menuQuery) {
        PagingParam pagingParam = new PagingParam();
        pagingParam.setPageSize(-1);
        pagingParam.setCurrPage(1);
        pagingParam.setSortField(new String[] {"displaySeq"});
        pagingParam.setSortOrder(new String[] {SortEnum.ASC.name()});
        PagingRowBounds pagingRowBounds = new PagingRowBounds(pagingParam);

        List<Menu> list = menuMapper.select(menuQuery, pagingRowBounds);
        Tree<Menu, Long> tree = new Tree<>();
        for (Menu menu : list) {
            tree.addNode(menu, menu.getId(), menu.getParentId());
        }

        tree.buildTree(ROOT_ID);

        return tree;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.menu}")
    public void create(Menu menu) {
        if (StringUtils.isEmpty(menu.getCode())) {
            menu.setDelRid(-System.nanoTime());
        }

        if (menu.getParentId() == null) {
            menu.setParentId(ROOT_ID);
        }

        int levelNum = 1;
        Menu parentMenu = null;
        if (!ROOT_ID.equals(menu.getParentId())) {
            parentMenu = menuMapper.selectById(menu.getParentId());
            if (parentMenu == null) {
                throw new RuntimeException("父节点" + menu.getParentId() + "不存在");
            }

            levelNum = parentMenu.getLevelNum() + 1;
        }

        menu.setLevelNum(levelNum);
        boolean levelCodeAfterInsert = levelCodeGenerator.generateAfterInsert(MENU_LEVEL_CODE, menu.getId());
        if (levelCodeAfterInsert) {
            menu.setLevelCode(levelCodeGenerator.getTempLevelCode(MENU_LEVEL_CODE, menu.getParentId(),
                    (parentMenu == null) ? null : parentMenu.getLevelCode()));
        } else {
            menu.setLevelCode(levelCodeGenerator.getLevelCode(MENU_LEVEL_CODE, menu.getParentId(),
                    (parentMenu == null) ? null : parentMenu.getLevelCode(), menu.getId()));
        }

        menuMapper.insert(menu);

        if (StringUtils.isEmpty(menu.getCode()) || levelCodeAfterInsert) {
            Menu menuModify = new Menu();
            menuModify.setId(menu.getId());
            if (StringUtils.isEmpty(menu.getCode())) {
                menuModify.setDelRid(menu.getId());
            }
            if (levelCodeAfterInsert) {
                menuModify.setLevelCode(levelCodeGenerator.getLevelCode(MENU_LEVEL_CODE, menu.getParentId(),
                        (parentMenu == null) ? null : parentMenu.getLevelCode(), menu.getId()));
            }
            menuMapper.update(menuModify);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.menu}")
    public void modify(Menu menu) {
        if (StringUtils.isEmpty(menu.getCode())) {
            menu.setDelRid(menu.getId());
        } else {
            menu.setDelRid(0L);
        }
        menuMapper.update(menu);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    @DataLog(bizType = SysConstants.DICT_BIZ_TYPE_SYS, bizName = "${sys.menu}")
    public void remove(Long[] ids) {
        if (ids == null || ids.length == 0) {
            return;
        }

        for (Long id : ids) {
            menuMapper.logicalDelete(id);
        }
    }
}
