package chornyi.conferences.web.command.common;

import chornyi.conferences.web.Path;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.servlet.http.HttpServletRequest;

import static org.mockito.Mockito.verify;

class LogoutCommandTest {

    @Mock
    private HttpServletRequest request;

    private LogoutCommand command = new LogoutCommand();

    @Test
    public void shouldInvalidateSessionWhenExecute() {
        command.execute(request);
        verify(request.getSession()).invalidate();
    }
}