package org.horx.wdf.springcloud.consumer.config.filter;

import org.horx.wdf.sys.support.filter.SysAccessLogFilter;
import org.springframework.core.annotation.Order;

import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "accessLogWebFilter", urlPatterns = {"/api/*", "/page/*", "/login", "/identify", "/main", "/logout"})
@Order(2)
public class SysAccessLogWebFilter extends SysAccessLogFilter {
}
