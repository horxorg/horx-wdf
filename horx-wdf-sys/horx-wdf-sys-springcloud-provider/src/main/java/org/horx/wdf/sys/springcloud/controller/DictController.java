package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.entity.PagingQuery;
import org.horx.wdf.common.entity.PagingResult;
import org.horx.wdf.sys.dto.DictDTO;
import org.horx.wdf.sys.dto.DictItemDTO;
import org.horx.wdf.sys.dto.dataauth.DictDataAuthDTO;
import org.horx.wdf.sys.dto.dataauth.DictItemAuthDTO;
import org.horx.wdf.sys.dto.query.DictItemQueryDTO;
import org.horx.wdf.sys.dto.query.DictQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithDictAuthDTO;
import org.horx.wdf.sys.dto.wrapper.DictWithAuthDTO;
import org.horx.wdf.sys.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/dict")
@RestController
public class DictController {

    @Autowired
    private DictService dictService;

    @GetMapping("/getById")
    public DictDTO getById(@RequestParam Long id) {
        return dictService.getById(id);
    }

    @PostMapping("/getByIdAuthorized")
    public DictDTO getByIdAuthorized(@RequestParam Long id, @RequestBody DictDataAuthDTO dictDataAuth) {
        return dictService.getByIdAuthorized(id, dictDataAuth);
    }

    @GetMapping("/getByCode")
    public DictDTO getByCode(@RequestParam String code) {
        return dictService.getByCode(code);
    }

    @PostMapping("/pagingQuery")
    public PagingResult<DictDTO> pagingQuery(@RequestBody PagingQuery<DictQueryDTO> pagingQuery) {
        return dictService.pagingQuery(pagingQuery);
    }

    @PostMapping("/create")
    public Long create(@RequestBody DictWithAuthDTO dictWithAuthDTO) {
        return dictService.create(dictWithAuthDTO);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody DictWithAuthDTO dictWithAuthDTO) {
        dictService.modify(dictWithAuthDTO);
    }

    @PostMapping("/remove")
    public void remove(@RequestBody BatchWithDictAuthDTO batchWithDictAuthDTO) {
        dictService.remove(batchWithDictAuthDTO);
    }

    @GetMapping("/getItemById")
    public DictItemDTO getItemById(@RequestParam Long id) {
        return dictService.getItemById(id);
    }

    @PostMapping("/pagingQueryItem")
    public PagingResult<DictItemDTO> pagingQueryItem(@RequestBody PagingQuery<DictItemQueryDTO> pagingQuery) {
        return dictService.pagingQueryItem(pagingQuery);
    }

    @PostMapping("/createItem")
    public Long createItem(@RequestBody DictItemDTO dictItem) {
        return dictService.createItem(dictItem);
    }

    @PostMapping("/modifyItem")
    public void modifyItem(@RequestBody DictItemDTO dictItem) {
        dictService.modifyItem(dictItem);
    }

    @PostMapping("/removeItem")
    public void removeItem(@RequestParam Long[] ids) {
        dictService.removeItem(ids);
    }

    @GetMapping("/queryEnabledItemList")
    public List<DictItemDTO> queryEnabledItemList(@RequestParam String dictCode) {
        return dictService.queryEnabledItemList(dictCode);
    }

    @GetMapping("/queryEnabledItemListByDictId")
    public List<DictItemDTO> queryEnabledItemListByDictId(@RequestParam Long dictId) {
        return dictService.queryEnabledItemListByDictId(dictId);
    }

    @PostMapping("/queryEnabledItemListAuthorized")
    public List<DictItemDTO> queryEnabledItemListAuthorized(@RequestParam String dictCode, @RequestBody DictItemAuthDTO dictItemAuth) {
        return dictService.queryEnabledItemListAuthorized(dictCode, dictItemAuth);
    }
}
