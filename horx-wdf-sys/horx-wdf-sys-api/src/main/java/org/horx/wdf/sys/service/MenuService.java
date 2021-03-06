package org.horx.wdf.sys.service;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.sys.dto.MenuDTO;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;

import java.util.List;

/**
 * 菜单Service。
 * @since 1.0
 */
public interface MenuService {

    MenuDTO getById(Long id);

    List<MenuDTO> query(PaginationQuery<MenuQueryDTO> paginationQuery);

    Long create(MenuDTO menuDTO);

    void modify(MenuDTO menuDTO);

    void remove(Long[] ids);
}
