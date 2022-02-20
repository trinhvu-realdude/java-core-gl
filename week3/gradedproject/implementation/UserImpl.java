package greatlearning.week3.gradedproject.implementation;

import greatlearning.week3.gradedproject.model.User;
import greatlearning.week3.gradedproject.seed.UserSeed;

import java.util.ArrayList;

public class UserImpl {

    private static final ArrayList<User> users = new ArrayList<User>();

    /**
     * Function: register
     *
     * @param user
     */
    public void register(User user) {
        users.add(user);
    }

    /**
     * Function: login
     *
     * @param userName
     * @param password
     * @return User if userName and password is correct, else return null
     */
    public User login(String userName, String password) {
        for (User user : users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    /**
     * Function: registerAdmin
     *
     * Creat default admin
     */
    public void registerAdmin() {
        UserSeed adminSeed = new UserSeed();

        users.add(adminSeed.getAdmin());
    }

    /**
     * Function: getLength
     *
     * @return size of users list
     */
    public int getLength() {
        return users.size();
    }
}
