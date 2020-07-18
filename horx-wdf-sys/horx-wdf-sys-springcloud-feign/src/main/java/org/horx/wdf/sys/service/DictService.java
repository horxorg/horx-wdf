package org.horx.wdf.sys.service;

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
import org.horx.wdf.sys.springcloud.SpringcloudConsumerContextInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 字典Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/dict")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider", configuration = SpringcloudConsumerContextInterceptor.class)
public interface DictService {

    @GetMapping("/getById")
    DictDTO getById(@RequestParam Long id);

    @PostMapping("/getByIdAuthorized")
    DictDTO getByIdAuthorized(@RequestParam Long id, @RequestBody DictDataAuthDTO dictDataAuth);

    @GetMapping("/getByCode")
    DictDTO getByCode(@RequestParam String code);

    @PostMapping("/pagingQuery")
    PagingResult<DictDTO> pagingQuery(@RequestBody PagingQuery<DictQueryDTO> pagingQuery);

    @PostMapping("/create")
    Long create(@RequestBody DictWithAuthDTO dictWithAuthDTO);

    @PostMapping("/modify")
    void modify(@RequestBody DictWithAuthDTO dictWithAuthDTO);

    @PostMapping("/remove")
    void remove(@RequestBody BatchWithDictAuthDTO batchWithDictAuthDTO);

    @GetMapping("/getItemById")
    DictItemDTO getItemById(@RequestParam Long id);

    @PostMapping("/pagingQueryItem")
    PagingResult<DictItemDTO> pagingQueryItem(@RequestBody PagingQuery<DictItemQueryDTO> pagingQuery);

    @PostMapping("/createItem")
    Long createItem(@RequestBody DictItemDTO dictItem);

    @PostMapping("/modifyItem")
    void modifyItem(@RequestBody DictItemDTO dictItem);

    @PostMapping("/removeItem")
    void removeItem(@RequestParam Long[] ids);

    @GetMapping("/queryEnabledItemList")
    List<DictItemDTO> queryEnabledItemList(@RequestParam String dictCode);

    @GetMapping("/queryEnabledItemListByDictId")
    List<DictItemDTO> queryEnabledItemListByDictId(@RequestParam Long dictId);

    @PostMapping("/queryEnabledItemListAuthorized")
    List<DictItemDTO> queryEnabledItemListAuthorized(@RequestParam String dictCode, @RequestBody DictItemAuthDTO dictItemAuth);
}
