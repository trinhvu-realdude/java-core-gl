package greatlearning.week1.GradedProject;

public class User {

    private int id;
    private String userName;
    private String password;
    private int emailId;
    private Book[] newBook;
    private Book[] favourite;
    private Book[] completed;

    public User(int id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.newBook = new Book[100];
        this.favourite = new Book[100];
        this.completed = new Book[100];
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

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getEmailId() {
        return emailId;
    }

    public void setEmailId(int emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public Book[] getNewBook() {
        return newBook;
    }

    public void setNewBook(Book[] newBook) {
        this.newBook = newBook;
    }

    public Book[] getFavourite() {
        return favourite;
    }

    public void setFavourite(Book[] favourite) {
        this.favourite = favourite;
    }

    public Book[] getCompleted() {
        return completed;
    }

    public void setCompleted(Book[] completed) {
        this.completed = completed;
    }
}
