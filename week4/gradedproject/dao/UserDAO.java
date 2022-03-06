package greatlearning.week4.gradedproject.dao;

import greatlearning.week4.gradedproject.exception.DuplicateElementException;
import greatlearning.week4.gradedproject.dbconnect.DBConnect;
import greatlearning.week4.gradedproject.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private Connection connection = DBConnect.getConnection();

    /**
     * Inner Static Class: UserDAOHelper
     *
     * Create a new instance
     */
    private static class UserDAOHelper {
        private static final UserDAO INSTANCE = new UserDAO();
    }

    private UserDAO() {
        // private constructor
    }

    /**
     * Function: getInstance
     *
     * @return UserDAOHelper.INSTANCE
     */
    public static UserDAO getInstance() {
        return UserDAOHelper.INSTANCE;
    }

    /**
     * Function: register
     *
     * @param user
     */
    public void register(User user) {
        try {
            if (!isDuplicated(user.getUserName())) {
                try {
                    String sql = "INSERT INTO users (userName, password, roleId) values (?,?,?)";

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
     *
     * @param userName
     * @param password
     * @return User if userName and password is correct, else return null
     */
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
