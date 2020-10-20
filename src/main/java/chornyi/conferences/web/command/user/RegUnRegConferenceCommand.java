package chornyi.conferences.web.command.user;

import chornyi.conferences.web.Path;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.service.UserService;
import chornyi.conferences.web.utils.PaginationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegUnRegConferenceCommand implements Command {

    private final static Logger logger = LogManager.getLogger(RegUnRegConferenceCommand.class);
    private ConferenceService conferenceService = new ConferenceService();
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String login = (String) request.getSession().getAttribute("login");

        Pattern pattern = Pattern.compile("(past-conferences|upcoming-conferences|conferences)");
        Matcher matcher = pattern.matcher(request.getParameter("conferencesLink"));
        if (matcher.find())
            request.getSession().setAttribute("conferencesLink", matcher.group(1));

        int conferenceCount = conferenceService.getConferencesCount();
        Map<String, Integer> paginationAttributes =
                new PaginationUtil().getAttributes(request, conferenceCount);
        request.getSession().setAttribute("paginationAttributes", paginationAttributes);

        long conferenceId = Long.valueOf(request.getParameter("conferenceId"));
        long userId = userService.getUserId(login, locale.toString());

        if (request.getParameter("command").equals("register"))
            userService.registerForConference(userId, conferenceId);

        if (request.getParameter("command").equals("unregister"))
            userService.unregisterFromConference(userId, conferenceId);

        if (matcher.group(1).equals("past-conferences"))
            return "redirect:/" + request.getSession().getAttribute("role") + Path.PAST_CONFERENCES_REDIRECT;

        if (matcher.group(1).equals("upcoming-conferences"))
            return "redirect:/" + request.getSession().getAttribute("role") + Path.UPCOMING_CONFERENCES_REDIRECT;

        request.getSession().setAttribute("isRedirect", true);

        return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT;
    }
}
