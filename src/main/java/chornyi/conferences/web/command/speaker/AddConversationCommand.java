package chornyi.conferences.web.command.speaker;

import chornyi.conferences.db.entity.Conference;
import chornyi.conferences.db.entity.Role;
import chornyi.conferences.db.entity.details.ConversationDetails;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.Resources;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConferenceService;
import chornyi.conferences.web.service.ConversationService;
import chornyi.conferences.web.service.UserService;
import chornyi.conferences.web.utils.ValidationUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.ResourceBundle;

public class AddConversationCommand implements Command {

    private static final Logger logger = LogManager.getLogger(AddConversationCommand.class);
    private UserService userService = new UserService();
    private ConferenceService conferenceService = new ConferenceService();
    private ConversationService conversationService = new ConversationService();

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String role = (String) request.getSession().getAttribute("role");
        String login = (String) request.getSession().getAttribute("login");
        ResourceBundle message = ResourceBundle.getBundle(Resources.i18N, locale);
        ResourceBundle regex = ResourceBundle.getBundle(Resources.REGEX, locale);

        if (request.getParameter("conferenceId") == null) {
            return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT;
        }
        if (role.equalsIgnoreCase(Role.MODERATOR.name()))
            request.setAttribute("speakers", userService.getAllSpeakers(locale.toString()));
        else
            request.setAttribute("speakers", userService.getById((int) userService.getUserId(login, locale.toString()), locale.toString()));

        request.setAttribute("conferenceId", request.getParameter("conferenceId"));

        if (request.getParameter("submitted") == null) {
            logger.trace("Not Submitted");
            return Path.ADD_CONVERSATION;
        }

        if (!ValidationUtil.isDataValid(request, regex, message))
            return Path.ADD_CONVERSATION;

        Conference conference = conferenceService.getConferenceById(Long.parseLong(request.getParameter("conferenceId")), locale.toString());
        LocalDateTime conversationDateTime = LocalDateTime.parse(request.getParameter("datetime").replace(" ", "T"));
        if (conversationDateTime.compareTo(conference.getDateTime()) < 0) {
            request.setAttribute("conversationTopicEN", request.getParameter("conversationTopicEN"));
            request.setAttribute("conversationTopicUA", request.getParameter("conversationTopicUA"));
            request.setAttribute("datetime", request.getParameter("datetime"));
            request.setAttribute("incorrect_datetime", message.getString("conversation.date.before.conference"));
            return Path.ADD_CONVERSATION;
        }

        // Update
        String conversationId = request.getParameter("conversationId");
        if (conversationId != null && !conversationId.isEmpty()) {
            conversationService.update(new ConversationDetails.Builder()
                    .setId(Long.valueOf(conversationId))
                    .setTopicEN(request.getParameter("conversationTopicEN"))
                    .setTopicUA(request.getParameter("conversationTopicUA"))
                    .setDateTime(conversationDateTime)
                    .setSpeakerId(Long.valueOf(request.getParameter("speaker")))
                    .build()
            );
            return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT + "/" + conference.getId() + Path.CONVERSATION_REDIRECT;
        }
        // Add new
        conversationService.addConversationToConference(conference.getId(), new ConversationDetails.Builder()
                .setTopicEN(request.getParameter("conversationTopicEN"))
                .setTopicUA(request.getParameter("conversationTopicUA"))
                .setDateTime(conversationDateTime)
                .setSpeakerId(Long.valueOf(request.getParameter("speaker")))
                .build());
        return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT + "/" + conference.getId() +  Path.CONVERSATION_REDIRECT;
    }
}
