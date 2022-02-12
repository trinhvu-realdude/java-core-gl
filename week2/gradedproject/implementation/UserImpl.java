package greatlearning.week2.gradedproject.implementation;

import greatlearning.week2.gradedproject.exception.DuplicateElementException;
import greatlearning.week2.gradedproject.model.User;

import java.util.ArrayList;
import java.util.Scanner;

public class UserImpl {

    private static final ArrayList<User> users = new ArrayList<User>();

    public void register(User user) {
        Scanner sc = new Scanner(System.in);

        try {

            System.out.println("Enter your username: ");
            String userName = sc.nextLine().trim();

            System.out.println("Enter your password: ");
            String password = sc.nextLine().trim();

            if (isDuplicated(userName)) {
                throw new DuplicateElementException();
            }

            user.setUserName(userName);
            user.setPassword(password);

            users.add(user);

            System.out.println("Register successfully!");
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

    public boolean isDuplicated(String userName) {
        for (User user: users) {
            if (user.getUserName().equals(userName)) {
                return true;
            }
        }
        return false;
    }
}
