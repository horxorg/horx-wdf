package org.horx.wdf.sys.rest;

import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.horx.wdf.common.extension.session.SessionHandler;
import org.horx.wdf.sys.config.SysConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码API控制器。
 * @since 1.0.1
 */
@Controller
public class VcodeApiController {
    private static final String VCODE_KEY = "vcode";

    @Autowired
    private SessionHandler sessionHandler;

    @Autowired
    private SysConfig sysConfig;

    @RequestMapping("/public/api/vcode")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // 设置请求头为输出图片类型
        response.setContentType("image/gif");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        // 三个参数分别为宽、高、位数
        SpecCaptcha specCaptcha = new SpecCaptcha(sysConfig.getVcodeWidth(), sysConfig.getVcodeHeight(), sysConfig.getVcodeCharLength());
        // 设置字体
        specCaptcha.setFont(Captcha.FONT_1);  // 有默认字体，可以不用设置
        // 设置类型，纯数字、纯字母、字母数字混合
        specCaptcha.setCharType(Captcha.TYPE_NUM_AND_UPPER);

        // 验证码存入session
        sessionHandler.setSessionAttr(VCODE_KEY, specCaptcha.text() + "," + System.currentTimeMillis());

        // 输出图片流
        specCaptcha.out(response.getOutputStream());
    }
}
