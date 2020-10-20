package chornyi.conferences.web.utils;

import chornyi.conferences.db.entity.Role;
import org.mindrot.jbcrypt.BCrypt;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AccessUtil {
    private static final String IGNORE = "(.*\\.css$|.*\\.js$|.*\\.jpg$|.*\\.png$|.*\\.gif$|.*\\.jpeg$|.*\\.ico$)$";

    private static final Map<String, List<String>> roleCommands = new HashMap<>();
    private static final List<String> permitAllCommands = new ArrayList<>();

    static {
        init();
    }

    private static void init() {

        permitAllCommands.add("/");
        permitAllCommands.add("/main.jsp");
        permitAllCommands.add("/conferences");

        List<String> visitorCommands = getVisitorCommands();

        List<String> userCommands = getUserCommands();

        List<String> speakerCommands = Stream.of(getUserCommands(), getSpeakerCommands())
                .flatMap(Collection::stream).collect(Collectors.toList());

        List<String> moderatorCommands = Stream.of(getUserCommands(), getSpeakerCommands(), getModeratorCommands())
                .flatMap(Collection::stream).collect(Collectors.toList());

        roleCommands.put("visitor", visitorCommands);
        roleCommands.put("user", userCommands);
        roleCommands.put("speaker", speakerCommands);
        roleCommands.put("moderator", moderatorCommands);
    }

    private static List<String> getVisitorCommands() {
        List<String> visitorCommands = new ArrayList<>();
        visitorCommands.add("base");
        visitorCommands.add("login");
        visitorCommands.add("registration");
        visitorCommands.add("conferences");
        visitorCommands.add("past-conferences");
        visitorCommands.add("upcoming-conferences");
        visitorCommands.add("set-language");
        visitorCommands.add("conversations");
        return visitorCommands;
    }

    private static List<String> getUserCommands() {
        List<String> userCommands = new ArrayList<>();
        userCommands.add("base");
        userCommands.add("logout");
        userCommands.add("conferences");
        userCommands.add("past-conferences");
        userCommands.add("upcoming-conferences");
        userCommands.add("set-language");
        userCommands.add("conversations");
        userCommands.add("register-unregister");
        return userCommands;
    }

    private static List<String> getSpeakerCommands() {
        List<String> speakerCommands = new ArrayList<>();
        speakerCommands.add("add-conversation");
        speakerCommands.add("update-conversation");
        speakerCommands.add("delete-conversation");
        return speakerCommands;
    }

    private static List<String> getModeratorCommands() {
        List<String> moderatorCommands = new ArrayList<>();
        moderatorCommands.add("add-conference");
        moderatorCommands.add("update-conference");
        moderatorCommands.add("delete-conference");
        moderatorCommands.add("statistics");
        return moderatorCommands;
    }

    public static boolean isAllowed(String urlPattern, String role) {
        if (!isPermitAll(urlPattern))
            return roleCommands.get(role).contains(urlPattern.replaceFirst(".*/", ""));
        return true;
    }

    private static boolean isPermitAll(String command) {
        return permitAllCommands.contains(command);
    }

    public String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean isIgnore(String urlPattern) {
        return urlPattern.matches(IGNORE);
    }

    public static boolean isVisitor(HttpServletRequest request) {
        return request.getSession().getAttribute("role").equals(Role.VISITOR.name().toLowerCase());
    }
}
