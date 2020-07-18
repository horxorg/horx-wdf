package org.horx.wdf.sys.rest;

import org.horx.wdf.common.entity.Result;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.common.extension.session.SessionHandler;
import org.horx.wdf.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录API控制器。
 * @since 1.0
 */
@Controller
public class LoginApiController {

    @Autowired
    private SessionHandler sessionHandler;

    @Autowired
    private UserService userService;

    @PostMapping("/identify")
    @ResponseBody
    public Result<String> identify(HttpServletRequest request) {
        UserIdentifyDTO userIdentifyDTO = new UserIdentifyDTO();
        userIdentifyDTO.setUsername(request.getParameter("username"));
        userIdentifyDTO.setPassword(request.getParameter("password"));

        Result<String> result = new Result<>();

        UserDTO user = userService.identify(userIdentifyDTO);
        sessionHandler.login(user.getId());
        result.setData("main");

        return result;
    }

}
