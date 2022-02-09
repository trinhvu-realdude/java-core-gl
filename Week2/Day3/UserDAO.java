package GreatLearning.Week2.Day3;

public interface UserDAO {

    void register(User user);

    int login();

    int login(String name);
}
