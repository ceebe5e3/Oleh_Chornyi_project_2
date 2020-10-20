package chornyi.conferences.web;

public class Path {

    //pages
    public static final String PATH_ROOT = "/WEB-INF";
    public static final String MAIN_PAGE = "/WEB-INF/main.jsp";
    public static final String LOGIN = "/WEB-INF/login.jsp";
    public static final String REGISTRATION = "/WEB-INF/registration.jsp";
    public static final String CONFERENCES = "/WEB-INF/conferences.jsp";
    public static final String ADD_CONFERENCE = "/WEB-INF/moderator/addConference.jsp";
    public static final String CONVERSATIONS = "/WEB-INF/conversations.jsp";
    public static final String ADD_CONVERSATION = "/WEB-INF/addConversation.jsp";
    public static final String STATISTICS = "/WEB-INF/moderator/statistics.jsp";


    // redirect
    public static final String URL_ROOT = "/";
    public static final String MAIN_REDIRECT = "/main.jsp";
    public static final String CONFERENCES_REDIRECT = "/conferences";
    public static final String PAST_CONFERENCES_REDIRECT = "/past-conferences";
    public static final String UPCOMING_CONFERENCES_REDIRECT = "/upcoming-conferences";
    public static final String CONVERSATION_REDIRECT = "/conversations";
    public static final String LOGIN_REDIRECT = "/login";
    public static final String LOGIN_VISITOR_REDIRECT = "/visitor/login";
}
