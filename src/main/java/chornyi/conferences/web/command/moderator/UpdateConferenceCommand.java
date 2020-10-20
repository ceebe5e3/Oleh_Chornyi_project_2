package chornyi.conferences.web.command.moderator;

import chornyi.conferences.db.entity.details.ConferenceDetails;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.Resources;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.utils.PaginationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Adds conference with specified id to request attributes and returns conference form view for editing.
 */

public class UpdateConferenceCommand implements Command {

    private final static Logger LOG = LogManager.getLogger(UpdateConferenceCommand.class);
    private ConferenceService conferenceService = new ConferenceService();

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle messages = ResourceBundle.getBundle(Resources.i18N, locale);

        Pattern pattern = Pattern.compile("(past-conferences|upcoming-conferences|conferences)");
        Matcher matcher = pattern.matcher(request.getParameter("conferencesLink"));
        if (matcher.find())
            request.getSession().setAttribute("conferencesLink", matcher.group(1));

        int conferenceCount = conferenceService.getConferencesCount();
        Map<String, Integer> paginationAttributes =
                new PaginationUtil().getAttributes(request, conferenceCount);
        request.getSession().setAttribute("paginationAttributes", paginationAttributes);
        request.getSession().setAttribute("isRedirect", true);
        if (request.getParameter("conferenceId") == null)
            return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT;

        ConferenceDetails conference = conferenceService.getConferenceDetailsById(Long.valueOf(request.getParameter("conferenceId")));

        if (conference == null)
            throw new RuntimeException((messages.getString("error.conference.not.found")));

        request.setAttribute("conferenceId", conference.getId());
        request.setAttribute("conferenceNameEN", conference.getNameEN());
        request.setAttribute("conferenceNameUA", conference.getNameUA());
        request.setAttribute("datetime", conference.getDateTime());
        request.setAttribute("locationEN", conference.getLocationEN());
        request.setAttribute("locationUA", conference.getLocationUA());

        return Path.ADD_CONFERENCE;
    }
}
