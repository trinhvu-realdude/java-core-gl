package greatlearning.week4.gradedproject.model;

import java.util.ArrayList;

/**
 * Class: User
 *
 * Applied Builder Pattern
 */
public class User {

    private int id;
    private String userName;
    private String password;
    private int roleId;
    private ArrayList<Book> newBook = new ArrayList<Book>();
    private ArrayList<Book> favourite = new ArrayList<Book>();
    private ArrayList<Book> completed = new ArrayList<Book>();

    public User() {

    }

    public User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.userName = userBuilder.userName;
        this.password = userBuilder.password;
        this.roleId = userBuilder.roleId;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() {
        return roleId;
    }

    public ArrayList<Book> getNewBook() {
        return newBook;
    }

    public void setNewBook(ArrayList<Book> newBook) {
        this.newBook = newBook;
    }

    public ArrayList<Book> getFavourite() {
        return favourite;
    }

    public void setFavourite(ArrayList<Book> favourite) {
        this.favourite = favourite;
    }

    public ArrayList<Book> getCompleted() {
        return completed;
    }

    public void setCompleted(ArrayList<Book> completed) {
        this.completed = completed;
    }

    public static class UserBuilder {
        private int id;
        private String userName;
        private String password;
        private int roleId;

        public UserBuilder(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder setRoleId(int roleId) {
            this.roleId = roleId;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
