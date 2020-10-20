package chornyi.conferences.web.command;

import javax.servlet.http.HttpServletRequest;

/**
 * Contains logic to process user request.
 */

public interface Command {

    /**
     * Processes request and specifies query string.
     * @param request object that contains the request the client has made of the servlet
     * @return query string
     */

    String execute(HttpServletRequest request);

}
