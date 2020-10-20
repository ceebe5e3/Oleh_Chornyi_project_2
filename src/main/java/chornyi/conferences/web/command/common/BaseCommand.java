package chornyi.conferences.web.command.common;

import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class BaseCommand implements Command {

    private UserService userService = new UserService();
    private ConferenceService conferenceService = new ConferenceService();
    private static final int DAYS_TO_ANNOUNCEMENT_BY_DEFAULT = 5;

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String login = (String) request.getSession().getAttribute("login");

        if (login == null || login.isEmpty())
            return Path.MAIN_REDIRECT;

        int daysToAnnouncement;
        if (request.getParameter("daysToAnnouncement") == null)
            daysToAnnouncement = DAYS_TO_ANNOUNCEMENT_BY_DEFAULT;
        else
            daysToAnnouncement = Integer.parseInt(request.getParameter("daysToAnnouncement"));

        long userId = userService.getUserId(login, locale.toString());
        List<Long> conferencesIds = userService.getConferencesIdsUser(userId);

        List<Conference> conferences = new ArrayList<>();
        for (Long conferenceId : conferencesIds) {
            Conference conference = conferenceService.getConferenceById(conferenceId, locale.toString());
            if (conference.getDateTime().isAfter(LocalDateTime.now()) &&
                    conference.getDateTime().isBefore(LocalDateTime.now().plusDays(daysToAnnouncement)))
                conferences.add(conference);
        }

        List<String> remainingTimes = new ArrayList<>();
        LocalDateTime toDateTime = LocalDateTime.now();
        for (Conference conference : conferences){
            LocalDateTime tempDateTime = conference.getDateTime();
            long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
            tempDateTime = tempDateTime.plusDays(days);
            long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
            tempDateTime = tempDateTime.plusHours(hours);
            long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
            remainingTimes.add(Math.abs(days) + " : " + Math.abs(hours) + " : " + Math.abs(minutes));
        }
        request.setAttribute("conferences", conferences);
        request.setAttribute("remainingTimes", remainingTimes);
        request.setAttribute("daysToAnnouncement", daysToAnnouncement);

        return Path.MAIN_REDIRECT;
    }
}
