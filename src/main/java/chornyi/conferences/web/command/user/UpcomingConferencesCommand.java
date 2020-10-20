package chornyi.conferences.web.command.user;

import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.utils.PaginationUtil;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpcomingConferencesCommand implements Command {

    private ConferenceService conferenceService = new ConferenceService();

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");

        Pattern pattern = Pattern.compile("(past-conferences|upcoming-conferences|conferences(.*))");
        Matcher matcher = pattern.matcher(request.getRequestURI());
        if (matcher.find())
            request.getSession().setAttribute("conferencesLink", matcher.group(1));

        int conferenceCount = conferenceService.getUpcomingConferencesCount();
        Map<String, Integer> paginationAttributes =
                new PaginationUtil().getAttributes(request, conferenceCount);
        request.getSession().setAttribute("paginationAttributes", paginationAttributes);
        List<Conference> upcomingConferences = conferenceService.getUpcomingPaginatedConferences(
                paginationAttributes.get("begin"), paginationAttributes.get("recordsPerPage"), locale.toString());
        request.getSession().setAttribute("conferences", upcomingConferences);
        request.getSession().setAttribute("afterPastUpcomingConferences", true);
        return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT;
    }
}
