package greatlearning.week2.day3.service;

public interface TaskDAO {

    void create(String userName);

    void update(String userName);

    void search(String userName);

    void delete(String userName);

    void display(String userName);
}
