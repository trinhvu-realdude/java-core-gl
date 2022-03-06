package greatlearning.week4.gradedproject.dao;

import greatlearning.week4.gradedproject.dbconnect.DBConnect;
import greatlearning.week4.gradedproject.model.Book;

import java.sql.*;
import java.util.HashMap;

public class ListBookDAO {

    private Connection connection = DBConnect.getConnection();

    /**
     * Inner Static Class: BookDAOHelper
     *
     * Create a new instance
     */
    private static class ListDAOHelper {
        private static final ListBookDAO INSTANCE = new ListBookDAO();
    }

    private ListBookDAO() {
        // private constructor
    }

    /**
     * Function: getInstance
     *
     * @return BookDAOHelper.INSTANCE
     */
    public static ListBookDAO getInstance() {
        return ListBookDAO.ListDAOHelper.INSTANCE;
    }

    public void addBookToList(int userId, int bookId, int listId) {
        try {
            String sql = "INSERT INTO list_details (userId, bookId, listId) VALUES (?,?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, bookId);
            ps.setInt(3, listId);

            ps.executeUpdate();

            System.out.println("Add book to list successfully!");
        } catch (SQLException e) {
            System.err.println("Failed to add book to the list");
        }
    }

    public HashMap<Integer, Book> getPersonalList(int userId, int listId) {
        HashMap<Integer, Book> books = new HashMap<>();
        int id = 1;
        try {
            String sql = "SELECT b.name \n" +
                    "FROM list_details ld \n" +
                    "INNER JOIN lists l, users u, books b \n" +
                    "WHERE ld.userId = u.id \n" +
                    "AND ld.listId = l.id \n" +
                    "AND ld.bookId = b.id \n" +
                    "AND l.id = ? \n" +
                    "AND u.id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, listId);
            ps.setInt(2, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                books.put(id, new Book(name));
                id++;
            }
        } catch (SQLException e) {
            System.err.println("Failed to get personal list");
        }
        return books;
    }

    public boolean isDuplicated(int userId, int bookId, int listId) {
        Book book = null;
        try {
            String sql = "SELECT b.name \n" +
                    "FROM list_details ld \n" +
                    "INNER JOIN lists l, users u, books b \n" +
                    "WHERE ld.userId = u.id \n" +
                    "AND ld.listId = l.id \n" +
                    "AND ld.bookId = b.id \n" +
                    "AND l.id = ? \n" +
                    "AND b.id = ?\n" +
                    "AND u.id = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, listId);
            ps.setInt(2, bookId);
            ps.setInt(3, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("name");
                book = new Book(name);
            }
        } catch (SQLException e) {
            System.err.println("Failed to check duplicated");
        }

        return book != null;
    }
}
