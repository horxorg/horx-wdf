package org.horx.wdf.sys.rest;

import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.entity.extension.EntityExtension;
import org.horx.wdf.common.exception.ErrorCodeException;
import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.horx.wdf.common.spring.ExceptionResolver;
import org.horx.wdf.common.tools.WebTool;
import org.horx.wdf.sys.config.SysConfig;
import org.horx.wdf.sys.dto.UserDTO;
import org.horx.wdf.sys.dto.UserIdentifyDTO;
import org.horx.wdf.common.extension.session.SessionHandler;
import org.horx.wdf.sys.extension.identification.UserIdentificationConfigHandler;
import org.horx.wdf.sys.service.UserService;
import org.horx.wdf.sys.dto.UserIdentifyResultDTO;
import org.horx.wdf.sys.vo.LoginInitVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录API控制器。
 * @since 1.0
 */
@RestController
public class LoginApiController {
    private final static Logger LOGGER = LoggerFactory.getLogger(LoginApiController.class);

    private static final String NEED_VCODE_KEY = "needVcode";

    @Autowired
    private SessionHandler sessionHandler;

    @Autowired
    private UserService userService;

    @Autowired(required = false)
    private UserIdentificationConfigHandler userIdentificationConfigHandler;

    @Autowired
    private ExceptionResolver exceptionResolver;

    @Autowired
    private ThreadContextHolder threadContextHolder;

    @Autowired
    private EntityExtension entityExtension;

    @Autowired
    private WebTool webTool;

    @Autowired
    private SysConfig sysConfig;

    @GetMapping("/public/api/loginInit")
    public Result<LoginInitVO> loginInit() {
        LoginInitVO loginInitVO = new LoginInitVO();
        loginInitVO.setVcodeUrl(webTool.getResourceAbsolutePath(sysConfig.getVcodeUrl()));
        loginInitVO.setIdentifyUrl(webTool.getResourceAbsolutePath(sysConfig.getIdentifyUrl()));
        loginInitVO.setReturnUrl(webTool.getResourceAbsolutePath(sysConfig.getMainUrl()));

        int vcodeAllowedErrorTimes = sysConfig.getVcodeAllowedErrorTimes();
        if (vcodeAllowedErrorTimes == 0) {
            loginInitVO.setNeedVcode(true);
        } else if (vcodeAllowedErrorTimes > 0) {
            Object obj = sessionHandler.getSessionAttr(NEED_VCODE_KEY);
            Boolean needVcode = (obj == null) ? false : (Boolean)obj;
            loginInitVO.setNeedVcode(needVcode);
        }

        return new Result<>(loginInitVO);
    }

    @PostMapping("/public/api/identify")
    public Result<UserIdentifyResultDTO> identify(HttpServletRequest request) {
        UserIdentifyDTO userIdentifyDTO = entityExtension.newEntity(UserIdentifyDTO.class);
        userIdentifyDTO.setUsername(request.getParameter("username"));
        userIdentifyDTO.setPassword(request.getParameter("password"));

        Result<UserIdentifyResultDTO> result = new Result<>();
        UserIdentifyResultDTO userIdentifyResultDTO = entityExtension.newEntity(UserIdentifyResultDTO.class);
        result.setData(userIdentifyResultDTO);

        if (userIdentificationConfigHandler != null) {
            boolean isContinue = userIdentificationConfigHandler.beforeIdentify(userIdentifyDTO, result);
            if (!isContinue) {
                return result;
            }
        }

        UserDTO user = null;
        try {
            user = userService.identify(userIdentifyDTO);
            sessionHandler.login(user.getId());
        } catch (ErrorCodeException e) {
            LOGGER.error(request.getRequestURI(), e);

            threadContextHolder.setEx(e);
            Result exResult = exceptionResolver.convertEx(e);
            result.setCode(exResult.getCode());
            result.setMsg(exResult.getMsg());
        }

        if (userIdentificationConfigHandler != null) {
            boolean isContinue = userIdentificationConfigHandler.afterIdentify(user, result);
            if (!isContinue) {
                return result;
            }
        }

        return result;
    }

}
