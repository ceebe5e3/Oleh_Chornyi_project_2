package chornyi.conferences.web.command.common;

import chornyi.conferences.web.Path;
import chornyi.conferences.web.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {

    private static final Logger logger = LogManager.getLogger(LogoutCommand.class);

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            logger.info("LogoutCommand: User with login: " + session.getAttribute("login") + " logged out");
            session.getServletContext().removeAttribute((String) session.getAttribute("login"));
            session.invalidate();
        }
        return  "redirect:" + Path.MAIN_REDIRECT;
    }
}
