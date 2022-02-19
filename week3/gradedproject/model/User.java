package greatlearning.week3.gradedproject.model;

import java.util.ArrayList;

public class User {

    private int id;
    private String userName;
    private String password;
    private int emailId;
    private ArrayList<Book> newBook = new ArrayList<Book>();
    private ArrayList<Book> favourite = new ArrayList<Book>();
    private ArrayList<Book> completed = new ArrayList<Book>();

    public User() {

    }

    public User(UserBuilder builder) {
        this.userName = builder.userName;
        this.password = builder.password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public int getEmailId() {
        return emailId;
    }

    public String getPassword() {
        return password;
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
        private String userName;
        private String password;

        public UserBuilder(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public User build() {
            return new User(this);
        }
    }
}
