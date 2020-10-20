package chornyi.conferences.web;

import chornyi.conferences.web.command.common.BaseCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *Based on incoming query it can redirect, forward to another page or render specified view.
 */

public class Dispatcher extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(Dispatcher.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pageUrl = new BaseCommand().execute(request);

        if (pageUrl.contains("redirect:")) {
            logger.info("FrontController: Redirect to page: " + pageUrl);
            response.sendRedirect(request.getContextPath() + pageUrl.replace("redirect:", ""));
        } else {
            logger.info("FrontController: Forward to page: " + pageUrl);
            request.getRequestDispatcher(pageUrl).forward(request, response);
        }
    }
}
