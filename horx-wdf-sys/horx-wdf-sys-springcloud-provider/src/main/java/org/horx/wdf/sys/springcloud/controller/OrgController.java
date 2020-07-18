package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.entity.SortQuery;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.dataauth.OrgAuthDTO;
import org.horx.wdf.sys.dto.dataauth.SysDataAuthDTO;
import org.horx.wdf.sys.dto.query.OrgQueryDTO;
import org.horx.wdf.sys.dto.wrapper.BatchWithSysAuthDTO;
import org.horx.wdf.sys.dto.wrapper.OrgWithAuthDTO;
import org.horx.wdf.sys.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 机构控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/org")
@RestController
public class OrgController {

    @Autowired
    private OrgService orgService;

    @GetMapping("/getById")
    public OrgDTO getById(@RequestParam Long id) {
        return orgService.getById(id);
    }

    @PostMapping("/getByIdAuthorized")
    public OrgDTO getByIdAuthorized(@RequestParam Long id, @RequestBody SysDataAuthDTO sysDataAuth) {
        return orgService.getByIdAuthorized(id, sysDataAuth);
    }

    @PostMapping("/query")
    public List<OrgDTO> query(@RequestBody SortQuery<OrgQueryDTO> sortQuery) {
        return orgService.query(sortQuery);
    }

    @PostMapping("/create")
    public Long create(@RequestBody OrgWithAuthDTO orgWithAuthDTO) {
        return orgService.create(orgWithAuthDTO);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody OrgWithAuthDTO orgWithAuthDTO) {
        orgService.modify(orgWithAuthDTO);
    }

    @PostMapping("/remove")
    public void remove(@RequestBody BatchWithSysAuthDTO batchWithAuthDTO) {
        orgService.remove(batchWithAuthDTO);
    }

    @PostMapping("/validate")
    public boolean validate(@RequestBody OrgAuthDTO orgAuth, @RequestParam Long orgId) {
        return orgService.validate(orgAuth, orgId);
    }
}
