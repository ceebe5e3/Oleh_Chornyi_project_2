package chornyi.conferences.web.command.speaker;

import chornyi.conferences.web.Path;
import chornyi.conferences.web.command.Command;
import chornyi.conferences.web.service.ConversationService;

import javax.servlet.http.HttpServletRequest;

public class DeleteConversationCommand implements Command {

    private ConversationService conversationService = new ConversationService();

    @Override
    public String execute(HttpServletRequest request) {
        String conferenceId = request.getParameter("conferenceId");
        request.setAttribute("conferenceId", conferenceId);

        if (request.getParameter("conversationId") == null)
            return "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT + "/" + conferenceId + Path.CONVERSATION_REDIRECT;

        if (!conversationService.delete(Long.valueOf(request.getParameter("conversationId"))))
            throw new RuntimeException();

        return  "redirect:/" + request.getSession().getAttribute("role") + Path.CONFERENCES_REDIRECT + "/" + conferenceId + Path.CONVERSATION_REDIRECT;
    }
}
