package org.horx.wdf.springboot.config.filter;

import org.horx.wdf.common.support.filter.RequestContextFilter;
import org.springframework.core.annotation.Order;

import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "requestContextFilter", urlPatterns = {"/api/*", "/page/*", "/public/*", "/login", "/main", "/logout"})
@Order(2)
public class RequestContextWebFilter extends RequestContextFilter {
}
