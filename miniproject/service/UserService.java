package greatlearning.miniproject.service;

import greatlearning.miniproject.dao.UserDAO;
import greatlearning.miniproject.dbconnect.DBConnect;
import greatlearning.miniproject.exception.DuplicateElementException;
import greatlearning.miniproject.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserService implements UserDAO {
    private Connection connection = DBConnect.getConnection();

    private static class UserDAOHelper {
        private static final UserService INSTANCE = new UserService();
    }

    private UserService() {
    }

    public static UserService getInstance() {
        return UserDAOHelper.INSTANCE;
    }

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
