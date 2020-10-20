package chornyi.conferences.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;

/**
 * To track the total number of active sessions in the web application
 */

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        se.getSession().setAttribute("locale", new Locale("en", "US"));
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        se.getSession().getServletContext().removeAttribute((String) se.getSession().getAttribute("login"));
    }
}
