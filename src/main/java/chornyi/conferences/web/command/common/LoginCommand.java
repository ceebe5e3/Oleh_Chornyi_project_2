package chornyi.conferences.web.command.common;

import chornyi.conferences.web.Path;
import chornyi.conferences.web.Resources;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.ResourceBundle;

public class LoginCommand implements Command {

    private final static Logger logger = LogManager.getLogger(LoginCommand.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request)  {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Locale locale = (Locale) request.getSession().getAttribute("locale");

        logger.trace("LoginCommand: locale " + locale);

        ResourceBundle message = ResourceBundle.getBundle(Resources.i18N, locale);

        if (StringUtils.isBlank(login) || StringUtils.isBlank(password)) {
            logger.error("AppException: Login/password cannot be empty");
            if (request.getSession().getAttribute("isSuccessRegistration") != null) {
                request.setAttribute("isSuccessRegistration", true);
                request.removeAttribute("isSuccessRegistration");
            }
            return Path.LOGIN;
        }

        if (login.isEmpty()) {
            logger.info("LoginCommand: Login is empty");
            request.setAttribute("incorrect_login", message.getString("validation.empty.login"));
            return Path.LOGIN;
        }
        if (password.isEmpty()) {
            logger.info("LogInCommand: Password is empty");
            request.setAttribute("incorrect_password", message.getString("validation.empty.password"));
            return Path.LOGIN;
        }
        if (!userService.isUserExist(login)) {
            logger.info("LogInCommand: User with such login: " + login + ", does`nt exist");
            request.setAttribute("incorrect_login", message.getString("validation.no.user.with.login"));
            return Path.LOGIN;
        }
        if (userService.checkPassword(login, password)) {
            request.getSession().setAttribute("login", login);
            request.getSession().setAttribute("role", userService.getRole(login).name().toLowerCase());
            request.getSession().getServletContext().setAttribute(login, request.getSession());
            logger.info("LoginCommand: User with login: " + login + ", signed in");
            return Path.URL_ROOT;
        } else {
            logger.warn("LoginCommand : Non-existent password");
            request.setAttribute("incorrect_password", message.getString("validation.error.check.password"));
        }
        return Path.LOGIN;
    }
}
