package greatlearning.miniproject.dao;

import greatlearning.miniproject.model.User;

public interface UserDAO {

    void register(User user);

    User login(String userName, String password);
}
