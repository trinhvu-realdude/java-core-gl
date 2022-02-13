package greatlearning.week2.day5.service;

import greatlearning.week2.day5.model.User;

public interface UserDAO {

    void register(User user);

    int login(String userName, String password);

    int login(String name);
}
