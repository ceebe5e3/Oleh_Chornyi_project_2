package chornyi.conferences.web.command.common;

import chornyi.conferences.web.command.Command;

import javax.servlet.http.HttpServletRequest;

public class LanguageCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().setAttribute("lang", request.getParameter("lang"));
        return "redirect:/";
    }
}
