package greatlearning.week4.gradedproject.test;

import greatlearning.week4.gradedproject.model.User;
import greatlearning.week4.gradedproject.dao.UserDAO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    UserDAO test = UserDAO.getInstance();

    @Test
    void register() {
        User user = new User.UserBuilder("test", "test").setRoleId(2).build();
        int before = test.getAllUsers().size();
        test.register(user);
        int after = test.getAllUsers().size();
        assertNotEquals(before, after);
    }

    @Test
    void login() {
        User actual = test.login("test", "test");
        assertNotEquals(null, actual);

        actual = test.login("admin", "");
        assertNotEquals(null, actual);
    }

    @Test
    void getAllUsers() {
        List<User> expected = new ArrayList<>();
        expected.add(new User.UserBuilder("admin", "").build());
        expected.add(new User.UserBuilder("trinh", "123").build());
        expected.add(new User.UserBuilder("test", "test").build());

        List<User> actual = test.getAllUsers();
        assertEquals(expected.size(), actual.size());
    }
}