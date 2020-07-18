package org.horx.wdf.common.spring;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.ErrorCode;
import org.horx.wdf.common.entity.ErrorCodeEntity;
import org.horx.wdf.common.exception.ClientException;
import org.horx.wdf.common.exception.RpcException;
import org.horx.wdf.common.exception.SystemException;
import org.horx.wdf.common.exception.ThirdPartyServiceException;
import org.horx.wdf.common.enums.ErrorCodeEnum;
import org.horx.wdf.common.extension.context.ThreadContextHolder;
import org.horx.wdf.common.tools.MsgTool;
import org.horx.wdf.common.tools.WebTool;
import org.horx.wdf.common.entity.Result;
import org.horx.wdf.common.exception.ErrorCodeException;
import org.horx.common.utils.JsonUtils;
import org.horx.wdf.common.extension.result.ResultConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 全局异常解析。
 * @since 1.0
 */
public class ExceptionResolver extends AbstractHandlerExceptionResolver {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionResolver.class);

    @Autowired
    protected MsgTool msgTool;

    @Autowired
    protected WebTool webTool;

    @Autowired
    protected ThreadContextHolder threadContextHolder;

    @Value("${common.errorView:/error}")
    protected String exViewName;

    protected ErrorCode defaultErrorCode;

    protected Map<String, ErrorCode> exMap;

    protected ResultConverter resultConverter;

    public ExceptionResolver() {
        // 修改异常拦截优先级，如果不修改，无法拦截400、405等异常，DefaultHandlerExceptionResolver优先级为2
        setOrder(-1);
    }

    /**
     * 异常解析。
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @return
     */
    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.error(request.getRequestURI(), ex);

        threadContextHolder.setEx(ex);
        Object convertedResult = convertExByResultConverter(ex);

        if (handler != null && handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod)handler;
            Method method = handlerMethod.getMethod();
            if (!method.isAnnotationPresent(ResponseBody.class) && !method.getDeclaringClass().isAnnotationPresent(RestController.class)) {
                ModelAndView mav = new ModelAndView(exViewName);
                mav.addObject("result", convertedResult);
                mav.addObject("staticVer", webTool.getStaticVer());
                mav.addObject("loadJsSrc", webTool.isLoadJsSrc());
                return mav;
            }
        }

        response.setStatus(HttpStatus.OK.value()); //设置状态码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //设置ContentType
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Cache-Control", "no-cache");

        try {
            String json = JsonUtils.toJson(convertedResult);
            response.getWriter().write(json);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return new ModelAndView();
    }

    /**
     * 设置异常视图名称。
     * @param exViewName
     */
    public void setExViewName(String exViewName) {
        this.exViewName = exViewName;
    }

    /**
     * 设置默认错误码。
     * @param defaultErrorCode
     */
    public void setDefaultErrorCode(ErrorCode defaultErrorCode) {
        this.defaultErrorCode = defaultErrorCode;
    }

    /**
     * 设置异常与结果代码的关系。
     * @param exMap key为异常类型，value为结果代码。
     */
    public void setExMap(Map<String, ErrorCode> exMap) {
        this.exMap = exMap;
    }

    /**
     * 设置结果转换。
     * @param resultConverter
     */
    public void setResultConverter(ResultConverter resultConverter) {
        this.resultConverter = resultConverter;
    }

    /**
     * 转换异常到结果。
     * @param ex
     * @return
     */
    public Result convertEx(Exception ex) {
        Result result = new Result();
        if (ex == null) {
            result.setCode(ErrorCodeEnum.B0001.getCode());
            result.setMsg(msgTool.getMsg("common.err.unknown"));
            return result;
        }

        ErrorCode resultCode = null;
        if (ex instanceof ErrorCodeException) {
            ErrorCodeException ecex = (ErrorCodeException)ex;
            if (StringUtils.isNotEmpty(ecex.getCode()) && StringUtils.isNotEmpty(ecex.getMsgKey())) {
                resultCode = new ErrorCodeEntity(ecex.getCode(), ecex.getMsgKey());
            } else if (ex instanceof ClientException) {
                resultCode = ErrorCodeEnum.A0001;
            } else if (ex instanceof SystemException) {
                resultCode = ErrorCodeEnum.B0001;
            } else if (ex instanceof RpcException) {
                resultCode = ErrorCodeEnum.C0110;
            } else if (ex instanceof ThirdPartyServiceException) {
                resultCode = ErrorCodeEnum.C0001;
            }

        }

        if (resultCode == null && exMap != null) {
            String exName = ex.getClass().getName();
            resultCode = exMap.get(exName);
        }

        if (resultCode == null) {
            resultCode = defaultErrorCode;
        }

        if (resultCode != null) {
            result.setCode(resultCode.getCode());
            result.setMsg(msgTool.getMsg(resultCode.getMsgKey()));
        } else {
            result.setCode(ErrorCodeEnum.B0001.getCode());
            result.setMsg(msgTool.getMsg("common.err.unknown"));
        }
        return result;
    }

    /**
     * 转换异常并进行用户自定义异常转换。
     * @param ex
     * @return
     */
    public Object convertExByResultConverter(Exception ex) {
        Result result = convertEx(ex);
        Object convertedResult = result;

        if (resultConverter != null) {
            convertedResult = resultConverter.convertResult(result);
        }

        processEx(ex, convertedResult);
        return result;
    }

    /**
     * 处理异常，用于子类扩展。
     * @param ex
     * @param convertedResult
     */
    protected void processEx(Exception ex, Object convertedResult) {

    }
}
