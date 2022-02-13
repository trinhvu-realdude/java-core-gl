package greatlearning.week2.day2;

public interface UserDAO {

    void register(User user);

    int login();

    int login(String name);
}
