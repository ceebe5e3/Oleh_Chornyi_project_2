package chornyi.conferences.web.command.moderator;

import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.db.entity.Conversation;
import chornyi.conferences.db.entity.User;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.service.UserService;
import chornyi.conferences.web.utils.PaginationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class StatisticsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(StatisticsCommand.class);
    private ConferenceService conferenceService = new ConferenceService();


    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");

        int conferenceCount = conferenceService.getConferencesCount();
        Map<String, Integer> paginationAttributes = new PaginationUtil().getAttributes(request, conferenceCount);
        request.getSession().setAttribute("paginationAttributes", paginationAttributes);
        List<Conference> conferences = conferenceService.getPaginatedConferences(
                paginationAttributes.get("begin"), paginationAttributes.get("recordsPerPage"), locale.toString());

        Map<Long, Integer> conferenceIdSpeakersCount = conferenceService.getNumberSpeakers();
        Map<Long, Integer> conferenceIdConversationsCount = conferenceService.getNumberConversations();
        Map<Long, Integer> conferenceIdUsersCount = conferenceService.getNumberUsers();
        Map<Long, Integer> conferenceIdVisitors = new HashMap<>();

        for (Conference conference : conferences){
            if (conferenceIdUsersCount.get(conference.getId()) == null)
                continue;
            conferenceIdVisitors.put(conference.getId(), new Random().nextInt(conferenceIdUsersCount.get(conference.getId())));
        }

        request.setAttribute("conferences", conferences);
        request.setAttribute("conferenceIdSpeakersCount", conferenceIdSpeakersCount);
        request.setAttribute("conferenceIdConversationsCount", conferenceIdConversationsCount);
        request.setAttribute("conferenceIdUsersCount", conferenceIdUsersCount);
        request.setAttribute("conferenceIdVisitors", conferenceIdVisitors);

        return Path.STATISTICS;
    }
}
