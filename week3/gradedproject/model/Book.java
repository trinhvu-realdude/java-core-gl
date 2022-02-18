package greatlearning.week3.gradedproject.model;

public class Book {

    private String name;
    private String authorName;
    private String description;
    private String genre;
    private double price;
    private int noOfCopiedSold = 0;

    public Book(String name, String authorName, String description, String genre, double price, int noOfCopiedSold) {
        this.name = name;
        this.authorName = authorName;
        this.description = description;
        this.genre = genre;
        this.price = price;
        this.noOfCopiedSold = noOfCopiedSold;
    }

    public Book() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNoOfCopiedSold() {
        return noOfCopiedSold;
    }

    public void setNoOfCopiedSold(int noOfCopiedSold) {
        this.noOfCopiedSold = noOfCopiedSold;
    }
}
