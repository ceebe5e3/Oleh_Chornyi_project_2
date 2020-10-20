package chornyi.conferences.web;

import chornyi.conferences.exception.NotFoundException;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.command.CommandFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * General application controller. Processes all GET POST DELETE HTTP requests.
 */

public class FrontController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(FrontController.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        execute(req, resp);
    }

    private void execute(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Optional<Command> commandFromRequest = CommandFactory.getCommandFromRequest(request);

        if (!commandFromRequest.isPresent()) {
            logger.warn("Command Not Found");
            throw new NotFoundException();
        }

        String pageUrl = commandFromRequest.get().execute(request);

        if (pageUrl.contains("redirect:")) {
            logger.info("FrontController: Redirect to page: " + pageUrl);
            response.sendRedirect(request.getContextPath() + pageUrl.replace("redirect:", ""));
        } else {
            logger.info("FrontController: Forward to page: " + pageUrl);
            request.getRequestDispatcher(pageUrl).forward(request, response);
        }
    }
}
