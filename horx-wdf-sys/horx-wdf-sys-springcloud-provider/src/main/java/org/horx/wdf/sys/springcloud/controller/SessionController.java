package org.horx.wdf.sys.springcloud.controller;

import org.horx.wdf.common.extension.session.SessionAttrDTO;
import org.horx.wdf.common.extension.session.SessionDTO;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 会话控制器。
 * 用于SpringCloud服务提供。
 * @since 1.0
 */
@RequestMapping("/sys/session")
@RestController
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping("/create")
    public Long create(@RequestBody SessionDTO sessionDTO) {
        return sessionService.create(sessionDTO);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SessionDTO sessionDTO) {
        sessionService.modify(sessionDTO);
    }

    @PostMapping("/removeBySessionKey")
    public void removeBySessionKey(@RequestParam String sessionKey) {
        sessionService.removeBySessionKey(sessionKey);
    }

    @PostMapping("/removeExpired")
    public void removeExpired() {
        sessionService.removeExpired();
    }

    @PostMapping("/createAttr")
    public Long createAttr(@RequestBody SessionAttrDTO sessionAttrDTO) {
        return sessionService.createAttr(sessionAttrDTO);
    }

    @PostMapping("/modifyAttr")
    public void modifyAttr(@RequestBody SessionAttrDTO sessionAttrDTO) {
        sessionService.modifyAttr(sessionAttrDTO);
    }

    @PostMapping("/removeAttrByKey")
    public void removeAttrByKey(@RequestParam Long sessionId, @RequestParam String attrKey) {
        sessionService.removeAttrByKey(sessionId, attrKey);
    }

    @GetMapping("/getBySessionKey")
    public SessionDTO getBySessionKey(@RequestParam String sessionKey) {
        return sessionService.getBySessionKey(sessionKey);
    }

    @GetMapping("/queryBySessionId")
    public List<SessionAttrDTO> queryAttrBySessionId(@RequestParam Long sessionId) {
        return sessionService.queryAttrBySessionId(sessionId);
    }

    @GetMapping("/getUserById")
    public UserDTO getUserById(@RequestParam Long userId) {
        return sessionService.getUserById(userId);
    }

    @GetMapping("/getOrgById")
    public OrgDTO getOrgById(@RequestParam Long orgId) {
        return sessionService.getOrgById(orgId);
    }

    @GetMapping("/getRoleIdsByPermissionCode")
    public Long[] getRoleIdsByPermissionCode(@RequestParam Long userId, @RequestParam String permissionCode) {
        return sessionService.getRoleIdsByPermissionCode(userId, permissionCode);
    }
}
