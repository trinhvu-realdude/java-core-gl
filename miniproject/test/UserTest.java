package greatlearning.miniproject.test;

import greatlearning.miniproject.model.User;
import greatlearning.miniproject.service.UserService;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    UserService test = UserService.getInstance();

    @Test
    void register() {
        User user = new User.UserBuilder("user_test", "test").setRoleId(2).build();
        int before = test.getAllUsers().size();
        test.register(user);
        int after = test.getAllUsers().size();
        assertNotEquals(before, after);
    }

    @Test
    void login() {
        User actual = test.login("user_test", "test");
        assertNotEquals(null, actual);

        actual = test.login("admin", "");
        assertNotEquals(null, actual);
    }

    @Test
    void getAllUsers() {
        List<User> expected = new ArrayList<>();
        expected.add(new User.UserBuilder("admin", "").build());
        expected.add(new User.UserBuilder("trinh", "123").build());
        expected.add(new User.UserBuilder("hieu", "000").build());
        expected.add(new User.UserBuilder("user_test", "test").build());

        List<User> actual = test.getAllUsers();
        assertEquals(expected.size(), actual.size());
    }

    @Test
    void getUserNameByOrderId() {
        HashMap<Integer, String> expected = new HashMap<>();
        expected.put(2, "hieu");
        expected.put(3, "hieu");

        HashMap<Integer, String> actual = test.getUserNameByOrderId(2, Date.valueOf("2022-03-02"));
        assertEquals(expected.get(2), actual.get(2));

        actual = test.getUserNameByOrderId(3, Date.valueOf("2022-03-02"));
        assertEquals(expected.get(3), actual.get(3));
    }
}