package greatlearning.week3.gradedproject.seed;

import greatlearning.week3.gradedproject.model.Admin;

public class UserSeed {

    private Admin admin = new Admin();

    public UserSeed() {
        admin.setUserName("admin");
        admin.setPassword("");
    }

    public Admin getAdmin() {
        return admin;
    }
}
