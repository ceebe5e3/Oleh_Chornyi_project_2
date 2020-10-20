package chornyi.conferences.web.command.user;

import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.db.entity.Conversation;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.service.UserService;
import chornyi.conferences.web.utils.AccessUtil;
import chornyi.conferences.web.utils.PaginationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConferencesCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ConferencesCommand.class);
    private UserService userService = new UserService();
    private ConferenceService conferenceService = new ConferenceService();

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String login = (String) request.getSession().getAttribute("login");

        List<Conference> conferences;
        Map<String, Integer> paginationAttributes;
        if (request.getSession().getAttribute("isRedirect") != null){
            request.setAttribute("conferencesLink", request.getSession().getAttribute("conferencesLink"));
            paginationAttributes = (HashMap<String, Integer>) request.getSession().getAttribute("paginationAttributes");
            conferences = conferenceService.getPaginatedConferences(
                    paginationAttributes.get("begin"), paginationAttributes.get("recordsPerPage"), locale.toString());
            request.getSession().removeAttribute("conferencesLink");
            request.getSession().removeAttribute("paginationAttributes");
            request.getSession().removeAttribute("isRedirect");
        } else if (request.getSession().getAttribute("afterPastUpcomingConferences") != null){
            request.setAttribute("conferencesLink", request.getSession().getAttribute("conferencesLink"));
            paginationAttributes = (HashMap<String, Integer>) request.getSession().getAttribute("paginationAttributes");
            conferences = (ArrayList<Conference>) request.getSession().getAttribute("conferences");
            request.getSession().removeAttribute("conferencesLink");
            request.getSession().removeAttribute("paginationAttributes");
            request.getSession().removeAttribute("afterPastUpcomingConferences");
        } else {
            Pattern pattern = Pattern.compile("(past-conferences|upcoming-conferences|conferences)");
            Matcher matcher = pattern.matcher(request.getRequestURI());
            if (matcher.find())
                request.setAttribute("conferencesLink", matcher.group(1));

            int conferenceCount = conferenceService.getConferencesCount();
            paginationAttributes = new PaginationUtil().getAttributes(request, conferenceCount);
            conferences = conferenceService.getPaginatedConferences(
                    paginationAttributes.get("begin"), paginationAttributes.get("recordsPerPage"), locale.toString());
        }

        request.setAttribute("paginationAttributes", paginationAttributes);
        request.setAttribute("conferences", conferences);

        request.setAttribute("now", LocalDateTime.now());

        if (AccessUtil.isVisitor(request))
            return Path.CONFERENCES;

        long userId = (int) userService.getUserId(login, locale.toString());
        List<Long> conferencesIds = userService.getConferencesIdsUser(userId);

        List<Boolean> isRegister = new ArrayList<>();
        for (Conference conference : conferences){
            if (conferencesIds.contains(conference.getId()))
                isRegister.add(true);
            else
                isRegister.add(false);
        }
        request.setAttribute("isRegister", isRegister);
        request.setAttribute("userId", userId);
        return Path.CONFERENCES;
    }
}
