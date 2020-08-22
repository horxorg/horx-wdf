package org.horx.wdf.sys.manager;

import org.horx.common.collection.Tree;
import org.horx.wdf.common.entity.PaginationParam;
import org.horx.wdf.sys.domain.Menu;
import org.horx.wdf.sys.dto.query.MenuQueryDTO;

import java.util.List;

/**
 * 菜单Manager。
 * @since 1.0
 */
public interface MenuManager {

    Menu getById(Long id);

    List<Menu> query(MenuQueryDTO menuQuery, PaginationParam paginationParam);

    Tree<Menu, Long> queryForTree(MenuQueryDTO menuQuery);

    void create(Menu menu);

    void modify(Menu menu);

    void remove(Long[] ids);
}
