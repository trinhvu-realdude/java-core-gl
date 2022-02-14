package greatlearning.week3.day1.service;

public interface TaskDAO {

    void create(String userName);

    void update(String userName);

    void search(String userName);

    void delete(String userName);

    void display(String userName);
}
