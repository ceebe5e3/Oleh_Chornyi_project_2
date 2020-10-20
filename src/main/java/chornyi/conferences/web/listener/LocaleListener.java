package chornyi.conferences.web.listener;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Locale;

/**
 * Checks user for locale. If locales wasn't specified, then attempts to resolve it based on request locales.
 */

public class LocaleListener implements HttpSessionAttributeListener {

    private final static Logger logger = LogManager.getLogger(LocaleListener.class);

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if (event.getName().equals("lang")) {
            String lang = (String) event.getSession().getAttribute("lang");
            if (lang.equals("en_US")) {
                logger.info("LocaleListener: Locale is set to 'en_US'");
                event.getSession().setAttribute("locale", new Locale("en", "US"));
            }
            else if (lang.equals("uk_UA")) {
                logger.info("LocaleListener: Locale is set to 'uk_UA'");
                event.getSession().setAttribute("locale", new Locale("uk", "UA"));
            }
        }
    }
}
