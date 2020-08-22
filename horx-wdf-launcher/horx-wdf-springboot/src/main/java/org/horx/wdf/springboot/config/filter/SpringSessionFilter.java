package org.horx.wdf.springboot.config.filter;

import org.springframework.core.annotation.Order;
import org.springframework.session.SessionRepository;
import org.springframework.session.web.http.SessionRepositoryFilter;

import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "springSessionFilter", urlPatterns = {"/api/*", "/page/*", "/public/*", "/login", "/main", "/logout"})
@Order(-2147483598)
public class SpringSessionFilter extends SessionRepositoryFilter {
    public SpringSessionFilter(SessionRepository sessionRepository) {
        super(sessionRepository);
    }
}
