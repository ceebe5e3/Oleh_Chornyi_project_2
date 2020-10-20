package chornyi.conferences.web.command.speaker;

import chornyi.conferences.db.entity.Role;
import chornyi.conferences.db.entity.details.ConversationDetails;
import chornyi.conferences.web.Path;
import chornyi.conferences.web.Resources;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConversationService;
import chornyi.conferences.web.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;

import java.util.Locale;
import java.util.ResourceBundle;

public class UpdateConversationCommand implements Command {

    private final static Logger LOG = LogManager.getLogger(UpdateConversationCommand.class);
    private ConversationService conversationService = new ConversationService();
    private UserService userService = new UserService();

    @Override
    public String execute(HttpServletRequest request) {
        Locale locale = (Locale) request.getSession().getAttribute("locale");
        String role = (String) request.getSession().getAttribute("role");
        String login = (String) request.getSession().getAttribute("login");
        ResourceBundle messages = ResourceBundle.getBundle(Resources.i18N, locale);

        if (request.getParameter("conversationId") == null || request.getParameter("conferenceId") == null)
            return "redirect:/" + request.getSession().getAttribute("role")
                    + Path.CONFERENCES_REDIRECT + "/" + request.getParameter("conferenceId") + Path.CONVERSATION_REDIRECT;

        ConversationDetails conversationDetails = conversationService.getConversationDetailsById(Long.valueOf(request.getParameter("conversationId")));

        if (conversationDetails == null)
            throw new RuntimeException((messages.getString("error.conversation.not.found")));

        request.setAttribute("conferenceId", request.getParameter("conferenceId"));
        request.setAttribute("conversationId", conversationDetails.getId());
        request.setAttribute("conversationTopicEN", conversationDetails.getTopicEN());
        request.setAttribute("conversationTopicUA", conversationDetails.getTopicUA());
        request.setAttribute("datetime", conversationDetails.getDateTime());
        request.setAttribute("speakerId", conversationDetails.getSpeakerId());

        if (role.equalsIgnoreCase(Role.MODERATOR.name()))
            request.setAttribute("speakers", userService.getAllSpeakers(locale.toString()));
        else
            request.setAttribute("speakers", userService.getById((int) userService.getUserId(login, locale.toString()), locale.toString()));

        return Path.ADD_CONVERSATION;
    }
}
