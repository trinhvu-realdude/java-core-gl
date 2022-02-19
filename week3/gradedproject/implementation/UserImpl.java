package greatlearning.week3.gradedproject.implementation;

import greatlearning.week3.gradedproject.exception.DuplicateElementException;
import greatlearning.week3.gradedproject.model.User;
import greatlearning.week3.gradedproject.seed.UserSeed;

import java.util.ArrayList;
import java.util.Scanner;

public class UserImpl {

    private static final ArrayList<User> users = new ArrayList<User>();

    public void register() {
        Scanner sc = new Scanner(System.in);

        LogImpl log = LogImpl.getInstance();

        try {
            System.out.println("Enter your username: ");
            String userName = sc.nextLine().trim();

            System.out.println("Enter your password: ");
            String password = sc.nextLine().trim();

            if (isDuplicated(userName)) {
                throw new DuplicateElementException();
            }

            User user = new User.UserBuilder(userName, password).build();

            users.add(user);

            System.out.println("Register successfully!");

            log.saveRegisterLog(user.getUserName());
        } catch (DuplicateElementException e) {
            System.err.println("Username already existed! Please try again");
        }
    }

    public User login(String userName, String password) {
        for (User user : users) {
            if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public void registerAdmin() {
        UserSeed adminSeed = new UserSeed();

        users.add(adminSeed.getAdmin());
    }

    public boolean isDuplicated(String userName) {
        for (User user : users) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
