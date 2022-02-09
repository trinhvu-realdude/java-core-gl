package GreatLearning.Week2.Day2;

public interface UserDAO {

    void register(User user);

    int login();

    int login(String name);
}
