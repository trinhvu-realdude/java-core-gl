package greatlearning.week3.gradedproject.model;

public class Admin extends User {

    public Admin(AdminBuilder builder) {
        super(builder);
    }

    public static class AdminBuilder extends User.UserBuilder {

        public AdminBuilder(String userName, String password) {
            super(userName, password);
        }

        public Admin build() {
            return new Admin(this);
        }
    }

}
