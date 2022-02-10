package greatlearning.week1.Day5;

public interface UserDAO {

    void register(User user);

    int login();

    int login(String name);
}
