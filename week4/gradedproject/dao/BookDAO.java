package greatlearning.week4.gradedproject.dao;

import greatlearning.week4.gradedproject.dbconnect.DBConnect;
import greatlearning.week4.gradedproject.model.Book;

import java.sql.*;
import java.util.*;

public class BookDAO {

    private Connection connection = DBConnect.getConnection();

    /**
     * Inner Static Class: BookDAOHelper
     *
     * Create a new instance
     */
    private static class BookDAOHelper {
        private static final BookDAO INSTANCE = new BookDAO();
    }

    private BookDAO() {
        // private constructor
    }

    /**
     * Function: getInstance
     *
     * @return BookDAOHelper.INSTANCE
     */
    public static BookDAO getInstance() {
        return BookDAOHelper.INSTANCE;
    }

    static LogService log = LogService.getInstance();

    /**
     * Function: getAllBooks
     * <p>
     * <p>
     * Display all books in the library
     */
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM books";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String authorName = rs.getString("author");
                double price = rs.getDouble("price");
                String genre = rs.getString("genre");
                String description = rs.getString("description");
                int noCopiedOfSold = rs.getInt("noCopiedOfSold");
                books.add(new Book.BookBuilder(name, authorName, description, genre, price, noCopiedOfSold).setId(id).build());
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all books");
        }
        log.saveDisplayBookLog();
        return books;
    }

    /**
     * Function: getBookById
     *
     * @param id Search book by id, then execute a sub menu to add book to new
     *           book, favourite, completed
     * @requires id > 0
     * @testcases TCs ((actual) -> expected):
     * search(1) -> true,
     * search(3) -> true,
     * search(5) -> true,
     * search(7) -> true,
     * search(10) -> true,
     * search(11) -> false,
     */
    public Book getBookById(int id) {
        Book book = null;
        try {
            String sql = "SELECT * FROM books WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                String authorName = rs.getString("author");
                double price = rs.getDouble("price");
                String genre = rs.getString("genre");
                String description = rs.getString("description");
                int noCopiedOfSold = rs.getInt("noCopiedOfSold");
                book = new Book.BookBuilder(name, authorName, description, genre, price, noCopiedOfSold).setId(id).build();
            }
        } catch (SQLException e) {
            System.err.println("Failed to search book by id");
        }
        return book;
    }

    /**
     * Function: addBook
     *
     * @param book Admin adds a new book to the list
     * @testcases TCs ((actual) -> expected)
     * add(3, new Book) -> false,
     * add(4, new Book) -> false,
     * add(11, new Book) -> true
     */
    public int addBook(Book book) {
        int id = -1;
        try {
            String sql = "INSERT INTO books (name, author, price, genre, description, noCopiedOfSold) VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthorName());
            ps.setDouble(3, book.getPrice());
            ps.setString(4, book.getGenre());
            ps.setString(5, book.getDescription());
            ps.setInt(6, book.getNoOfCopiedSold());

            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();

            if (rs.next()) {
                id = rs.getInt(1);
            }

            System.out.println("Add a new book successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to add a new book");
        }
        return id;
    }

    /**
     * Function: deleteBook
     *
     * @param bookId Admin deletes a book from list
     * @testcases TCs ((actual) -> expected)
     * delete(2) -> true,
     * delete(9) -> true,
     * delete(11) -> false,
     * delete(2) -> false
     */
    public boolean deleteBook(int bookId) {
        boolean delete = false;
        try {
            String sql = "DELETE FROM books WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, bookId);
            delete = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Failed to delete book");
        }
        return delete;
    }

    /**
     * Function: update
     *
     * @param book Admin updates information of specific book
     * @testcases TCs ((actual) -> expected)
     * update(10, new attributes of book) -> true,
     * update(8, new attributes of book) -> true,
     * update(11, new attributes of book) -> false
     */
    public boolean updateBook(Book book) {
        boolean update = false;
        try {
            String sql = "UPDATE books SET name = ?, author = ?, price = ?, genre = ?, description = ?, noCopiedOfSold = ? WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, book.getName());
            ps.setString(2, book.getAuthorName());
            ps.setDouble(3, book.getPrice());
            ps.setString(4, book.getGenre());
            ps.setString(5, book.getDescription());
            ps.setInt(6, book.getNoOfCopiedSold());
            ps.setInt(7, book.getId());

            update = ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Failed to update book");
        }
        return update;
    }

    /**
     * Function: arrangeLowToHigh
     * <p>
     * Admin arranges the list of books in low to high orders
     */
    public List<Book> arrangeLowToHigh() {
        List<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM books ORDER BY price ASC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String authorName = rs.getString("author");
                double price = rs.getDouble("price");
                String genre = rs.getString("genre");
                String description = rs.getString("description");
                int noCopiedOfSold = rs.getInt("noCopiedOfSold");
                books.add(new Book.BookBuilder(name, authorName, description, genre, price, noCopiedOfSold).setId(id).build());
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all books");
        }
        return books;
    }

    /**
     * Function: arrangeHighToLow
     * <p>
     * Admin arranges the list of books in high to low orders
     */
    public List<Book> arrangeHighToLow() {
        List<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM books ORDER BY price DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String authorName = rs.getString("author");
                double price = rs.getDouble("price");
                String genre = rs.getString("genre");
                String description = rs.getString("description");
                int noCopiedOfSold = rs.getInt("noCopiedOfSold");
                books.add(new Book.BookBuilder(name, authorName, description, genre, price, noCopiedOfSold).setId(id).build());
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all books");
        }
        return books;
    }

    /**
     * Function: arrangeBestSelling
     * <p>
     * Admin arranges the list of books in bestselling orders
     */
    public List<Book> arrangeBestSelling() {
        List<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM books ORDER BY noCopiedOfSold DESC";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String authorName = rs.getString("author");
                double price = rs.getDouble("price");
                String genre = rs.getString("genre");
                String description = rs.getString("description");
                int noCopiedOfSold = rs.getInt("noCopiedOfSold");
                books.add(new Book.BookBuilder(name, authorName, description, genre, price, noCopiedOfSold).setId(id).build());
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all books");
        }
        return books;
    }

    /**
     * Function: countTotalOfBook
     */
    public int countTotalOfBook() {
        int total = 0;
        try {
            String sql = "SELECT COUNT(*) AS count FROM books";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                total = rs.getInt("count");
            }
        } catch (SQLException e) {
            System.err.println("Failed to count books");
        }
        return total;
    }

    /**
     * Function: getAutobiographyBook
     */
    public List<Book> getAutobiographyBook() {
        List<Book> books = new ArrayList<>();
        try {
            String sql = "SELECT * FROM books WHERE genre = 'Autobiography'";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String authorName = rs.getString("author");
                double price = rs.getDouble("price");
                String genre = rs.getString("genre");
                String description = rs.getString("description");
                int noCopiedOfSold = rs.getInt("noCopiedOfSold");
                books.add(new Book.BookBuilder(name, authorName, description, genre, price, noCopiedOfSold).setId(id).build());
            }
        } catch (SQLException e) {
            System.err.println("Failed to get all books");
        }
        return books;
    }
}
