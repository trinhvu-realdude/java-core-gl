package greatlearning.miniproject.dao;

import greatlearning.miniproject.model.User;

import java.util.HashMap;
import java.util.List;

public interface UserDAO {

    void register(User user);

    User login(String userName, String password);

    List<User> getAllUsers();

    HashMap<Integer, String> getUserNameByOrderId(int orderId, java.sql.Date today);
}
