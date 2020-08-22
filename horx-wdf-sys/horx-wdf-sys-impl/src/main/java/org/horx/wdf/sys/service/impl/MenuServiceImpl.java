package org.horx.wdf.sys.service.impl;

import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.enums.SortEnum;
import org.horx.wdf.sys.converter.MenuConverter;
import org.horx.wdf.sys.domain.Menu;
import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;
import org.horx.wdf.sys.manager.MenuManager;
import org.horx.wdf.sys.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜单Service实现。
 * @since 1.0
 */
@Service("menuService")
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuManager menuManager;

    @Autowired
    private MenuConverter menuConverter;

    @Override
    public MenuDTO getById(Long id) {
        Menu menu = menuManager.getById(id);
        MenuDTO menuDTO = menuConverter.toDto(menu);
        return menuDTO;
    }

    @Override
    public List<MenuDTO> query(PaginationQuery<MenuQueryDTO> paginationQuery) {
        MenuQueryDTO menuQueryDTO = (paginationQuery == null) ? null : paginationQuery.getQuery();
        PaginationParam paginationParam = (paginationQuery == null) ? null : paginationQuery.getPaginationParam();
        if (paginationParam == null) {
            paginationParam = new PaginationParam();
        }
        if (paginationParam.getSortField() == null || paginationParam.getSortField().length == 0) {
            paginationParam.setSortField(new String[] {"displaySeq"});
            paginationParam.setSortOrder(new String[] {SortEnum.ASC.name()});
        }

        List<Menu> list = menuManager.query(menuQueryDTO, paginationParam);
        List<MenuDTO> dtoList = menuConverter.toDtoList(list);
        return dtoList;
    }

    @Override
    public Long create(MenuDTO menuDTO) {
        Menu menu = menuConverter.fromDto(menuDTO);
        menuManager.create(menu);
        return menu.getId();
    }

    @Override
    public void modify(MenuDTO menuDTO) {
        Menu menu = menuConverter.fromDto(menuDTO);
        menuManager.modify(menu);
    }

    @Override
    public void remove(Long[] ids) {
        menuManager.remove(ids);
    }
}
