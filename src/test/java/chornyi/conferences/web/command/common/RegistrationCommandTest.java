package chornyi.conferences.web.command.common;

import chornyi.conferences.web.FrontController;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;

import static org.mockito.Mockito.*;

class RegistrationCommandTest {

    @Test
    void WhenFormDataIsNotValid() throws ServletException, IOException {
        String requestURI = "/visitor/registration";
        String pageUrl = "/WEB-INF/registration.jsp";
        Locale locale = new Locale("en", "US");

        final FrontController frontController = new FrontController();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getRequestURI()).thenReturn(requestURI);
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("locale")).thenReturn(locale);
        when(request.getRequestDispatcher(pageUrl)).thenReturn(dispatcher);

        frontController.doPost(request, response);

        verify(request, times(1 )).getRequestDispatcher(pageUrl);
        verify(dispatcher).forward(request, response);
    }

}