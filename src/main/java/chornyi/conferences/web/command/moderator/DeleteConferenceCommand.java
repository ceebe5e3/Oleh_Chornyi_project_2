package chornyi.conferences.web.command.moderator;

import chornyi.conferences.web.Path;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.utils.PaginationUtil;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Removes conference with id specified in request parameter.
 */

public class DeleteConferenceCommand implements Command {

    private ConferenceService conferenceService = new ConferenceService();

    @Override
    public String execute(HttpServletRequest request) {
        if (!conferenceService.delete(Long.valueOf(request.getParameter("conferenceId"))))
            throw new RuntimeException();

        Pattern pattern = Pattern.compile("(past-conferences|upcoming-conferences|conferences)");
        Matcher matcher = pattern.matcher(request.getParameter("conferencesLink"));
        if (matcher.find())
            request.getSession().setAttribute("conferencesLink", matcher.group(1));

        int conferenceCount = conferenceService.getConferencesCount();
        Map<String, Integer> paginationAttributes = new PaginationUtil().getAttributes(request, conferenceCount);
        request.getSession().setAttribute("paginationAttributes", paginationAttributes);

        request.getSession().setAttribute("isRedirect", true);

        return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT;

    }
}
