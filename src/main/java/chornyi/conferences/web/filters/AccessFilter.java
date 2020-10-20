package chornyi.conferences.web.filters;

import chornyi.conferences.db.entity.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class AccessFilter implements Filter {

    private final static Logger logger =  LogManager.getLogger(AccessFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpServletRequest.getSession();
        if (session.getAttribute("role") == null) {
            logger.debug("AccessFilter: Request URI:  " + httpServletRequest.getRequestURI());
            session.setAttribute("role", Role.VISITOR.name().toLowerCase());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        logger.debug("Filter initialization starts");
        logger.debug("Filter initialization finished");
    }

    @Override
    public void destroy() {
        logger.debug("Filter destruction starts");
        logger.debug("Filter destruction finished");
    }
}
