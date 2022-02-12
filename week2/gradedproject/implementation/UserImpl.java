package greatlearning.week2.gradedproject.implementation;

import greatlearning.week2.gradedproject.model.User;

public class UserImpl {

    private static final User newUser = new User();

    public void register() {
        newUser.setUserName("trinh");
        newUser.setPassword("123");
    }

    public int login(String userName, String password) {
        if (newUser.getUserName().equals(userName) && newUser.getPassword().equals(password)) {
            return 1;
        }
        return 0;
    }

    public User getUser() {
        return newUser;
    }
}
