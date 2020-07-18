package org.horx.wdf.sys.service;

import org.horx.wdf.common.extension.session.SessionAttrDTO;
import org.horx.wdf.common.extension.session.SessionDTO;
import org.horx.wdf.sys.dto.OrgDTO;
import org.horx.wdf.sys.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 会话Service。
 * 用于SpringCloud Feign Client。
 * @since 1.0
 */
@RequestMapping("/sys/session")
@Component
@FeignClient(value = "horx-wdf-springcloud-provider")
public interface SessionService {

    @PostMapping("/create")
    Long create(@RequestBody SessionDTO sessionDTO);

    @PostMapping("/modify")
    void modify(@RequestBody SessionDTO sessionDTO);

    @PostMapping("/removeBySessionKey")
    void removeBySessionKey(@RequestParam String sessionKey);

    @PostMapping("/removeExpired")
    void removeExpired();

    @PostMapping("/createAttr")
    Long createAttr(@RequestBody SessionAttrDTO sessionAttrDTO);

    @PostMapping("/modifyAttr")
    void modifyAttr(@RequestBody SessionAttrDTO sessionAttrDTO);

    @PostMapping("/removeAttrByKey")
    void removeAttrByKey(@RequestParam Long sessionId, @RequestParam String attrKey);

    @GetMapping("/getBySessionKey")
    SessionDTO getBySessionKey(@RequestParam String sessionKey);

    @GetMapping("/queryBySessionId")
    List<SessionAttrDTO> queryAttrBySessionId(@RequestParam Long sessionId);

    @GetMapping("/getUserById")
    UserDTO getUserById(@RequestParam Long userId);

    @GetMapping("/getOrgById")
    OrgDTO getOrgById(@RequestParam Long orgId);

    @GetMapping("/getRoleIdsByPermissionCode")
    Long[] getRoleIdsByPermissionCode(@RequestParam Long userId, @RequestParam String permissionCode);
}
