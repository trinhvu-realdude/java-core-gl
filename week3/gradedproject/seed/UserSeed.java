package greatlearning.week3.gradedproject.seed;

import greatlearning.week3.gradedproject.model.Admin;

public class UserSeed {

    private Admin admin;

    public UserSeed() {
        admin = new Admin.AdminBuilder("admin", "").build();
    }

    public Admin getAdmin() {
        return admin;
    }
}
