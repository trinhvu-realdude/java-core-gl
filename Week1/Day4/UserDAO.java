package GreatLearning.Week1.Day4;

public interface UserDAO {

    void register(User user);

    int login();

    int login(String name);
}
