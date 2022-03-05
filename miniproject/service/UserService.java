package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.UserDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.exception.DuplicateElementException;
import greatlearning.miniproject.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Class: UserService implements UserDAO interface
 *
 * Applied Bill Pugh Singleton Pattern for User service
 */
public class UserService implements UserDAO {
    private Connection connection = DBConnect.getConnection();

    /**
     * Inner Static Class: UserDAOHelper
     *
     * Create a new instance
     */
    private static class UserDAOHelper {
        private static final UserService INSTANCE = new UserService();
    }

    private UserService() {
        // private constructor
    }

    /**
     * Function: getInstance
     *
     * @return UserDAOHelper.INSTANCE
     */
    public static UserService getInstance() {
        return UserDAOHelper.INSTANCE;
    }

    /**
     * Function: register
     * @param user
     * Insert a new user into users table in database
     */
    @Override
    public void register(User user) {
        try {
            if (!isDuplicated(user.getUserName())) {
                try {
                    String sql = "INSERT INTO users (userName, password, roleId) VALUES (?,?,?)";

                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, user.getUserName());
                    ps.setString(2, user.getPassword());
                    ps.setInt(3, user.getRoleId());
                    ps.executeUpdate();

                    System.out.println("Register successfully!");
                } catch (SQLException e) {
                    System.err.println("Failed to register!");
                }
            } else {
                throw new DuplicateElementException();
            }
        } catch (DuplicateElementException e) {
            System.err.println("User already existed! Try again.");
        }
    }

    /**
     * Function: login
     * @param userName
     * @param password
     * @return user if user exists in users table, null if this user is not found
     */
    @Override
    public User login(String userName, String password) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Function: getAllUsers
     * @return a list of all users in users table
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try {
            String sql = "SELECT * FROM users";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                int roleId = rs.getInt("roleId");
                users.add(new User.UserBuilder(userName, password).setId(id).setRoleId(roleId).build());
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return users;
    }

    /**
     * Function: getUserNameByOrderId
     * @param orderId
     * @param today
     * @return a hash map with Integer key and username of user value
     */
    @Override
    public HashMap<Integer, String> getUserNameByOrderId(int orderId, java.sql.Date today) {
        HashMap<Integer, String> userNames = new HashMap<>();

        try {
            String sql = "SELECT o.id, u.userName \n" +
                    "FROM orders o \n" +
                    "INNER JOIN users u " +
                    "WHERE u.id = o.userId \n" +
                    "AND o.id = ? \n" +
                    "AND o.orderDate = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, orderId);
            ps.setDate(2, today);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String userName = rs.getString("userName");
                userNames.put(id, userName);
            }
        } catch (SQLException e) {
            System.err.println("Data error!");
        }
        return userNames;
    }

    /**
     * Function: isDuplicated
     * @param userName
     * @return true if username already existed in users table, and vice versa
     */
    public boolean isDuplicated(String userName) {
        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
