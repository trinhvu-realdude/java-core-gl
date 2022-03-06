package greatlearning.week4.gradedproject.model;

/**
 * Class: Book
 * <p>
 * Applied Builder Pattern and Prototype Pattern
 */
public class Book {

    private int id;
    private String name;
    private String authorName;
    private String description;
    private String genre;
    private double price;
    private int noOfCopiedSold;

    public Book(BookBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.authorName = builder.authorName;
        this.description = builder.description;
        this.genre = builder.genre;
        this.price = builder.price;
        this.noOfCopiedSold = builder.noOfCopiedSold;
    }

    public Book(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public double getPrice() {
        return price;
    }

    public int getNoOfCopiedSold() {
        return noOfCopiedSold;
    }

    /**
     * Inner static class: BookBuilder
     */
    public static class BookBuilder {
        private int id;
        private String name;
        private String authorName;
        private String description;
        private String genre;
        private double price;
        private int noOfCopiedSold;

        public BookBuilder(String name, String authorName, String description, String genre, double price, int noOfCopiedSold) {
            this.name = name;
            this.authorName = authorName;
            this.description = description;
            this.genre = genre;
            this.price = price;
            this.noOfCopiedSold = noOfCopiedSold;
        }

        public BookBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }
}
