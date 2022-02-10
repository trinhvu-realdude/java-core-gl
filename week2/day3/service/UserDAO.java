package greatlearning.week2.day3.service;

import greatlearning.week2.day3.model.User;

import java.io.IOException;

public interface UserDAO {

    void register(User user);

    int login(String userName, String password) throws IOException;

    int login(String name) throws IOException;
}
