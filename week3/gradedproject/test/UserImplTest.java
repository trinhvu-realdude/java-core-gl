package greatlearning.week3.gradedproject.test;

import greatlearning.week3.gradedproject.implementation.UserImpl;
import greatlearning.week3.gradedproject.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserImplTest {

    UserImpl test = new UserImpl();

    @Test
    public void register() {

        // Register first user
        User user = new User.UserBuilder("dre", "nuthingbutagthang").build();
        test.register(user);
        int actual = test.getLength();
        int expected = 1;
        assertEquals(expected, actual);

        // Register second user
        user = new User.UserBuilder("trinh", "123").build();
        test.register(user);
        actual = test.getLength();
        expected = 2;
        assertEquals(expected, actual);

        // Register the third user after creating a default admin
        user = new User.UserBuilder("quynh", "456").build();
        test.registerAdmin();
        test.register(user);
        actual = test.getLength();
        expected = 4;
        assertNotEquals(3, actual);
        assertEquals(expected, actual);
    }

    @Test
    public void login() {
        // Login after creating default admin
        test.registerAdmin();
        User actual = test.login("admin", "");
        assertNotEquals(null, actual);

        // Login before register
        actual = test.login("trinhvu", "123");
        assertEquals(null, actual);

        // Login after register
        test.register(new User.UserBuilder("hi", "222").build());
        actual = test.login("hi", "222");
        assertNotEquals(null, actual);
    }
}