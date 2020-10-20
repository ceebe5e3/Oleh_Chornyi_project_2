package chornyi.conferences.web.command.common;

import chornyi.conferences.db.entity.Role;
import chornyi.conferences.web.FrontController;
import chornyi.conferences.web.Resources;
import chornyi.conferences.web.service.UserService;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

class LoginCommandTest {
    @Test
    void WhenUserLoginIsNullForwardToLoginPage() throws ServletException, IOException {
        String login = null;
        String password = "password";
        String requestURI = "/visitor/login";
        String pageUrl = "/WEB-INF/login.jsp";

        final FrontController frontController = new FrontController();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestURI()).thenReturn(requestURI);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession().getAttribute("locale")).thenReturn(new Locale("en", "US"));
        when(request.getRequestDispatcher(pageUrl)).thenReturn(dispatcher);

        frontController.doPost(request, response);

        verify(request, times(1 )).getRequestDispatcher(pageUrl);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void WhenUserPasswordIsNullForwardToLoginPage() throws ServletException, IOException {
        String login = "login";
        String password = null;
        String requestURI = "/visitor/login";
        String pageUrl = "/WEB-INF/login.jsp";

        final FrontController frontController = new FrontController();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestURI()).thenReturn(requestURI);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession().getAttribute("locale")).thenReturn(new Locale("en", "US"));
        when(request.getRequestDispatcher(pageUrl)).thenReturn(dispatcher);

        frontController.doPost(request, response);

        verify(request, times(1 )).getRequestDispatcher(pageUrl);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void WhenUserLoginIsEmptyForwardToLoginPage() throws ServletException, IOException {
        String login = "";
        String password = "password";
        String requestURI = "/visitor/login";
        String pageUrl = "/WEB-INF/login.jsp";

        final FrontController frontController = new FrontController();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestURI()).thenReturn(requestURI);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession().getAttribute("locale")).thenReturn(new Locale("en", "US"));
        when(request.getRequestDispatcher(pageUrl)).thenReturn(dispatcher);

        frontController.doPost(request, response);

        verify(request, times(1 )).getRequestDispatcher(pageUrl);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void WhenUserPasswordIsEmptyForwardToLoginPage() throws ServletException, IOException {
        String login = "login";
        String password = "";
        String requestURI = "/visitor/login";
        String pageUrl = "/WEB-INF/login.jsp";

        final FrontController frontController = new FrontController();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestURI()).thenReturn(requestURI);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession().getAttribute("locale")).thenReturn(new Locale("en", "US"));
        when(request.getRequestDispatcher(pageUrl)).thenReturn(dispatcher);

        frontController.doPost(request, response);

        verify(request, times(1 )).getRequestDispatcher(pageUrl);
        verify(dispatcher).forward(request, response);
    }

    @Test
    void WhenUserLoginAndPasswordIsCorrectRedirectToRoot() throws ServletException, IOException {
        String login = "login";
        String password = "password";
        Role role = Role.MODERATOR;
        String requestURI = "/visitor/login";
        String pageUrl = "/";
        Locale locale = new Locale("en", "US");

        final FrontController frontController = new FrontController();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final UserService service = mock(UserService.class);
        final ServletContext context = mock(ServletContext.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestURI()).thenReturn(requestURI);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("login")).thenReturn(login);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getSession().getAttribute("locale")).thenReturn(locale);
        when(service.isUserExist(login)).thenReturn(true);
        when(service.checkPassword(login, password)).thenReturn(true);
        when(request.getSession().getServletContext()).thenReturn(context);
        when(service.getRole(login)).thenReturn(role);
        when(request.getRequestDispatcher(pageUrl)).thenReturn(dispatcher);

        frontController.doPost(request, response);

        verify(request, times(2)).getParameter(anyString());
        verify(request.getSession(), times(1)).setAttribute("login", login);
        verify(request.getSession(), times(1)).setAttribute("role", role.name().toLowerCase());
        verify(request, times(1)).getRequestDispatcher(pageUrl);
        verify(dispatcher).forward(request, response);
    }

}