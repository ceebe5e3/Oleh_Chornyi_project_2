package chornyi.conferences.web.filters;

import chornyi.conferences.exception.NoAuthorityException;
import chornyi.conferences.exception.NotFoundException;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.command.CommandFactory;
import chornyi.conferences.web.utils.AccessUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

/**
 * When user wasn't authenticated and requested page not allowed then he will be redirected to login page.
 */

public class AuthorizationFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(AuthorizationFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Filter starts");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession session = request.getSession();
        String uri = request.getRequestURI();
        String userRole = (String) session.getAttribute("role");
        AccessUtil accessUtil = new AccessUtil();

        if (accessUtil.isIgnore(uri) || accessUtil.isAllowed(uri, userRole)) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Optional<Command> commandFromRequest = CommandFactory.getCommandFromRequest(request);
        if (!commandFromRequest.isPresent()) {
            logger.warn("Requested page Not Found.");
            throw new NotFoundException();
        } else {
            String login = (String) request.getSession().getAttribute("login");
            logger.warn("Unauthorized access attempt. User login: " + (login != null ? login : "unknown (visitor)"));
            throw new NoAuthorityException();
        }
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
