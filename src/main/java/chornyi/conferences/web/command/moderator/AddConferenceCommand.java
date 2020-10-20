package chornyi.conferences.web.command.moderator;

import chornyi.conferences.db.entity.details.ConferenceDetails;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.Resources;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.utils.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Creates existing conference when id specified.
 */

public class AddConferenceCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddConferenceCommand.class);
    private ConferenceService conferenceService = new ConferenceService();

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        ResourceBundle message = ResourceBundle.getBundle(Resources.i18N, locale);
        ResourceBundle regex = ResourceBundle.getBundle(Resources.REGEX, locale);

        if (request.getParameter("isGetForm") != null && request.getParameter("isGetForm").equalsIgnoreCase("true"))
            return Path.ADD_CONFERENCE;

        if (!ValidationUtil.isDataValid(request, regex, message))
            return Path.ADD_CONFERENCE;

        LocalDateTime conferenceDateTime = LocalDateTime.parse(request.getParameter("datetime").replace(" ", "T"));
        if (conferenceDateTime.compareTo(LocalDateTime.now()) < 0) {
            request.setAttribute("incorrect_datetime", message.getString("incorrect.datetime.early.date"));
            return Path.ADD_CONFERENCE;
        }
        // Update
        if (request.getParameter("conferenceId") != null && !request.getParameter("conferenceId").isEmpty()) {
            conferenceService.update(
                    new ConferenceDetails.Builder()
                            .setId(Long.valueOf((request.getParameter("conferenceId"))))
                            .setNameEN(request.getParameter("conferenceNameEN"))
                            .setNameUA(request.getParameter("conferenceNameUA"))
                            .setLocationEN(request.getParameter("locationEN"))
                            .setLocationUA(request.getParameter("locationUA"))
                            .setDateTime(conferenceDateTime)
                            .build()
            );
            return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT;
        }
        // Add new
        if (!conferenceService.add(
                new ConferenceDetails.Builder()
                        .setNameEN(request.getParameter("conferenceNameEN"))
                        .setNameUA(request.getParameter("conferenceNameUA"))
                        .setLocationEN(request.getParameter("locationEN"))
                        .setLocationUA(request.getParameter("locationUA"))
                        .setDateTime(conferenceDateTime)
                        .build())) {
            throw new RuntimeException();
        }
        return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT;
    }
}
