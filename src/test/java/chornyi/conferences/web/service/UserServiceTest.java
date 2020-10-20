package chornyi.conferences.web.service;

import chornyi.conferences.db.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private static UserService userService;
    private static final String LANGUAGE = "en_US";

    @BeforeAll
    static void init(){
        userService = new UserService();
    }

    @Test
    void getNonExistenceUserById(){
        User user = userService.getById(700, LANGUAGE);
        assertNull(user);
    }

    @Test
    void getExistenceUserById(){
        User user = userService.getById(5, LANGUAGE);
        assertEquals(user.getId(), 5);
    }

    @Test
    void isUserExistTrue(){
        boolean result = userService.isUserExist("oleg");
        assertTrue(result);
    }

    @Test
    void isUserExistFalse(){
        boolean result = userService.isUserExist("oleg141");
        assertFalse(result);
    }

    @Test
    void checkPasswordTrue(){
        boolean result = userService.checkPassword("oleg", "oleg");
        assertTrue(result);
    }

    @Test
    void checkPasswordFalse() {
        boolean result = userService.checkPassword("oleg", "oleg141");
        assertFalse(result);
    }
}