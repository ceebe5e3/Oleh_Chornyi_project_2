package chornyi.conferences.web.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Specifies request encoding
 */

public class EncodingFilter implements Filter {

    private final static Logger logger =  LogManager.getLogger(EncodingFilter.class);

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.debug("Filter starts");
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("text/html");
        logger.debug("EncodingFilter: Request URI: " + ((HttpServletRequest)servletRequest).getRequestURI());
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
