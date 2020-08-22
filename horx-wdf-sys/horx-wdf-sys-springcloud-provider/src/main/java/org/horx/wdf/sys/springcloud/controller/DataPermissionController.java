package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.entity.PaginationQuery;
import org.horx.wdf.common.entity.PaginationResult;
import org.horx.wdf.sys.dto.DataPermissionDefDTO;
import org.horx.wdf.sys.dto.query.DataPermissionQueryDTO;
import org.horx.wdf.sys.service.DataPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 数据权限控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/dataPermission")
@RestController
public class DataPermissionController {

    @Autowired
    private DataPermissionService dataPermissionService;

    @GetMapping("/getById")
    public DataPermissionDefDTO getById(@RequestParam Long id) {
        return dataPermissionService.getById(id);
    }

    @GetMapping("/getByCode")
    public DataPermissionDefDTO getByCode(@RequestParam String code) {
        return dataPermissionService.getByCode(code);
    }

    @PostMapping("/paginationQuery")
    public PaginationResult<DataPermissionDefDTO> paginationQuery(@RequestBody PaginationQuery<DataPermissionQueryDTO> paginationQuery) {
        return dataPermissionService.paginationQuery(paginationQuery);
    }

    @GetMapping("/queryForAuthorityObj")
    public List<DataPermissionDefDTO> queryForAuthorityObj(@RequestParam String authorityObjType, @RequestParam Long authorityObjId) {
        return dataPermissionService.queryForAuthorityObj(authorityObjType, authorityObjId);
    }

    @PostMapping("/create")
    public Long create(@RequestBody DataPermissionDefDTO dataPermissionDTO) {
        return dataPermissionService.create(dataPermissionDTO);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody DataPermissionDefDTO dataPermissionDTO) {
        dataPermissionService.modify(dataPermissionDTO);
    }

    @PostMapping("/remove")
    public void remove(@RequestParam Long[] ids) {
        dataPermissionService.remove(ids);
    }
}
