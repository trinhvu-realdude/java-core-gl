package GreatLearning.Week2.Day1;

public interface UserDAO {

    void register(User user);

    int login();

    int login(String name);
}
