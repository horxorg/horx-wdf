package org.horx.wdf.common.tools;

import org.apache.commons.lang3.StringUtils;
import org.horx.wdf.common.entity.PagingParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.ContextLoader;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * web工具。
 * @since 1.0
 */
public class WebTool {

    @Value("${common.loadJsSrc}")
    private boolean loadJsSrc;

    @Value("${common.staticVer}")
    private String staticVer;

    @Autowired
    private ServletContext servletContext;

    /**
     * 获取Locale。
     * @return
     */
    public Locale getLocale() {
        return LocaleContextHolder.getLocale();
    }

    /**
     * 解析分页参数对象。
     * @param request
     * @return
     */
    public PagingParam genPagingParam(HttpServletRequest request) {
        PagingParam pagingParam = new PagingParam();
        genPaging(request, pagingParam);
        genSort(request, pagingParam);
        return pagingParam;
    }

    /**
     * 是否加载js源文件。
     * @return
     */
    public boolean isLoadJsSrc() {
        return loadJsSrc;
    }

    /**
     * 获取静态文件版本。
     * @return
     */
    public String getStaticVer() {
        String result = loadJsSrc ? String.valueOf(System.currentTimeMillis()) : staticVer;
        return result;
    }

    /**
     * 获取
     * @param request
     * @return
     */
    public String getClientIp(HttpServletRequest request) {
        String strClientIp = request.getHeader("x-forwarded-for");

        if (strClientIp == null || strClientIp.length() == 0 || "unknown".equalsIgnoreCase(strClientIp)) {
            strClientIp = request.getHeader("Proxy-Client-IP");

            if (strClientIp == null || strClientIp.length() == 0 || "unknown".equalsIgnoreCase(strClientIp)) {
                strClientIp = request.getHeader("WL-Proxy-Client-IP");

                if (strClientIp == null || strClientIp.length() == 0 || "unknown".equalsIgnoreCase(strClientIp)) {
                    strClientIp = request.getRemoteAddr();
                }
            }
        }

        if(strClientIp == null || strClientIp.length() == 0 || "unknown".equalsIgnoreCase(strClientIp)) {
            strClientIp = null;
        } else if (strClientIp.contains(",")) {
            String[] arr = strClientIp.split(",");
            for (String str : arr) {
                if(!("unknown".equalsIgnoreCase(str))) {
                    strClientIp = str.trim();
                    break;
                }
            }
        } else {
            strClientIp = strClientIp.trim();
        }
        return strClientIp;
    }

    public String getAppRealPath() {
        return servletContext.getRealPath("/");
    }

    /**
     * 设置是否加载js源文件。
     * @param loadJsSrc 是否加载js源文件。
     */
    public void setLoadJsSrc(boolean loadJsSrc) {
        this.loadJsSrc = loadJsSrc;
    }

    /**
     * 设置静态文件版本。
     * @param staticVer 静态文件版本。
     */
    public void setStaticVer(String staticVer) {
        this.staticVer = staticVer;
    }

    /**
     * 生成分页参数。
     * @param request
     * @param pagingParam
     */
    protected void genPaging(HttpServletRequest request, PagingParam pagingParam) {
        String str = request.getParameter("pageSize");
        if (StringUtils.isEmpty(str)) {
            pagingParam.setPageSize(10);
        } else {
            pagingParam.setPageSize(Integer.valueOf(str));
        }
        str = request.getParameter("currPage");
        if (StringUtils.isEmpty(str)) {
            pagingParam.setCurrPage(1);
        } else {
            pagingParam.setCurrPage(Integer.valueOf(str));
        }
    }

    /**
     * 生成排序参数。
     * @param request
     * @param pagingParam
     */
    protected void genSort(HttpServletRequest request, PagingParam pagingParam) {
        pagingParam.setSortField(getArrForRequest(request, "sortField"));
        pagingParam.setSortOrder(getArrForRequest(request, "sortOrder"));
    }

    private String[] getArrForRequest(HttpServletRequest request, String key) {
        String[] arr = request.getParameterValues(key);
        if (arr == null || arr.length == 0) {
            String str = request.getParameter(key);
            if (str == null || str.length() == 0) {
                arr = new String[0];
            } else if (str.contains(",")) {
                arr = str.split(",");
            } else {
                arr = new String[]{str};
            }
        }

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (arr[i] == null) ? null : arr[i].trim();
        }

        return arr;
    }


}
