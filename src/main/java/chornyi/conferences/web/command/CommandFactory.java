package chornyi.conferences.web.command;

import chornyi.conferences.web.command.common.*;
import chornyi.conferences.web.command.speaker.AddConversationCommand;
import chornyi.conferences.web.command.user.*;
import chornyi.conferences.web.command.speaker.DeleteConversationCommand;
import chornyi.conferences.web.command.speaker.UpdateConversationCommand;
import chornyi.conferences.web.command.moderator.AddConferenceCommand;
import chornyi.conferences.web.command.moderator.DeleteConferenceCommand;
import chornyi.conferences.web.command.moderator.StatisticsCommand;
import chornyi.conferences.web.command.moderator.UpdateConferenceCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class CommandFactory {
    private final static Logger logger = LogManager.getLogger(CommandFactory.class);

    public static Optional<Command> getCommandFromRequest(HttpServletRequest request) {
        String[] splitUri = request.getRequestURI().split("/");
        String uriCommand = splitUri[splitUri.length - 1];

        logger.debug("The command from the request uri is: " + uriCommand);

        return Optional.ofNullable(getCommand(uriCommand));
    }

    private static Command getCommand(String type){
        if (type.equalsIgnoreCase("BASE")){
            return new BaseCommand();
        } else if (type.equalsIgnoreCase("LOGIN")){
            return new LoginCommand();
        } else if (type.equalsIgnoreCase("REGISTRATION")){
            return new RegistrationCommand();
        } else if (type.equalsIgnoreCase("LOGOUT")){
            return new LogoutCommand();
        } else if (type.equalsIgnoreCase("LANGUAGE")){
            return new LanguageCommand();
        } else if (type.equalsIgnoreCase("CONFERENCES")){
            return new ConferencesCommand();
        } else if (type.equalsIgnoreCase("ADD-CONFERENCE")){
            return new AddConferenceCommand();
        } else if (type.equalsIgnoreCase("DELETE-CONFERENCE")){
            return new DeleteConferenceCommand();
        } else if (type.equalsIgnoreCase("UPDATE-CONFERENCE")) {
            return new UpdateConferenceCommand();
        } else if (type.equalsIgnoreCase("CONVERSATIONS") ){
            return new ConversationsCommand();
        } else if (type.equalsIgnoreCase("ADD-CONVERSATION")){
            return new AddConversationCommand();
        } else if (type.equalsIgnoreCase("UPDATE-CONVERSATION")){
            return new UpdateConversationCommand();
        } else if (type.equalsIgnoreCase("DELETE-CONVERSATION")){
            return new DeleteConversationCommand();
        } else if (type.equalsIgnoreCase("REGISTER-UNREGISTER")){
            return new RegUnRegConferenceCommand();
        } else if (type.equalsIgnoreCase("PAST-CONFERENCES")){
            return new PastConferencesCommand();
        } else if (type.equalsIgnoreCase("UPCOMING-CONFERENCES")){
            return new UpcomingConferencesCommand();
        } else if (type.equalsIgnoreCase("STATISTICS")){
            return new StatisticsCommand();
        }else if (type.equalsIgnoreCase("MAIL")) {
            return new SendEmailCommand();
        }
        return null;
    }
}
