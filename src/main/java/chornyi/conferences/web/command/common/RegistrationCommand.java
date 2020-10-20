package chornyi.conferences.web.command.common;

import chornyi.conferences.db.entity.Role;
import chornyi.conferences.db.entity.details.UserDetails;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.Resources;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.UserService;
import chornyi.conferences.web.utils.AccessUtil;
import chornyi.conferences.web.utils.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationCommand implements Command {

    private final static Logger logger = LogManager.getLogger(RegistrationCommand.class);
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");

        ResourceBundle regex = ResourceBundle.getBundle(Resources.REGEX);
        ResourceBundle message = ResourceBundle.getBundle(Resources.i18N, locale);

        logger.debug("locale: " + locale.toString());

        if (!ValidationUtil.isDataValid(request, regex, message))
            return Path.REGISTRATION;

        if (userService.isUserExist(request.getParameter("login"))) {
            request.setAttribute("incorrect_login", message.getString("user.already.exist"));
            return Path.REGISTRATION;
        }

        UserDetails user = new UserDetails.Builder()
                .setLogin(request.getParameter("login"))
                .setPassword(new AccessUtil().hashPassword(request.getParameter("password")))
                .setFirstNameEN(request.getParameter("firstNameEN"))
                .setFirstNameUA(request.getParameter("firstNameUA"))
                .setLastNameEN(request.getParameter("lastNameEN"))
                .setLastNameUA(request.getParameter("lastNameUA"))
                .setEmail(request.getParameter("email"))
                .setRole(Role.valueOf(request.getParameter("role")))
                .build();
        userService.register(user);

        logger.info("New user registered");

        request.getSession().setAttribute("isSuccessRegistration", true);

        return "redirect:" + Path.LOGIN_VISITOR_REDIRECT;
    }
}
