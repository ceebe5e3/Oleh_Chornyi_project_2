package chornyi.conferences.web.command.user;

import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.db.entity.Conversation;
import chornyi.conferences.db.entity.User;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.service.ConversationService;
import chornyi.conferences.web.service.UserService;
import chornyi.conferences.web.utils.AccessUtil;
import chornyi.conferences.web.utils.PaginationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ConversationsCommand implements Command {

    private static final Logger logger = LogManager.getLogger(ConversationsCommand.class);
    private ConferenceService conferenceService = new ConferenceService();
    private UserService userService = new UserService();
    private ConversationService conversationService = new ConversationService();

    @Override
    public String execute(HttpServletRequest request) {
        long conferenceId = 0;
        if (request.getParameter("conferenceId") == null){
            String[] words = request.getRequestURI().split("/");
            for (int i = 0; i < words.length; i++)
                if (words[i].equalsIgnoreCase("conferences"))
                    conferenceId = Long.valueOf(words[i + 1]);
        } else {
            conferenceId = Long.valueOf(request.getParameter("conferenceId"));
        }

        String login = (String) request.getSession().getAttribute("login");
        Locale locale = (Locale) request.getSession().getAttribute("locale");

        int conversationCount = conversationService.getConversationsCountLinkedToConference(conferenceId);
        Map<String, Integer> paginationAttributes = new PaginationUtil().getAttributes(request, conversationCount);
        request.setAttribute("paginationAttributes", paginationAttributes);
        List<Conversation> conversations = conversationService.getPaginatedConversationsLinkedToConference(
                conferenceId,
                paginationAttributes.get("begin"),
                paginationAttributes.get("recordsPerPage"),
                locale.toString()
        );
        request.setAttribute("conversations", conversations);

        List<User> speakers = new ArrayList<>();
        for (Conversation conversation : conversations)
            speakers.add(userService.getById(conversation.getSpeakerId(), locale.toString()));
        request.setAttribute("speakers", speakers);

        if (!AccessUtil.isVisitor(request)) {
            long userId = userService.getUserId(login, locale.toString());
            request.setAttribute("userId", userId);
        }
        request.setAttribute("conferenceId", conferenceId);

        Conference conference = conferenceService.getConferenceById(conferenceId, locale.toString());
        if (conference.getDateTime().isBefore(LocalDateTime.now()))
            request.setAttribute("isAddConversationButtonDisable", true);

        return Path.CONVERSATIONS;
    }
}
