package greatlearning.week3.day1.service;

import greatlearning.week3.day1.model.User;

public interface UserDAO {

    void register(User<String, String, String> user);

    int login(String userName, String password);

    int login(String name);
}
