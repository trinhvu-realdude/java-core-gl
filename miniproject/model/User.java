package greatlearning.miniproject.model;

/**
 * Class: User
 *
 * Applied Builder Pattern for User model
 */
public class User {
    private int id;
    private String userName;
    private String password;
    private int roleId;

    public User(UserBuilder userBuilder) {
        this.id = userBuilder.id;
        this.userName = userBuilder.userName;
        this.password = userBuilder.password;
        this.roleId = userBuilder.roleId;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public int getRoleId() {
        return roleId;
    }

    // Inner static class UserBuilder
    public static class UserBuilder {
        private int id;
        private String userName;
        private String password;
        private int roleId;

        public UserBuilder(String userName, String password) {
            this.userName = userName;
            this.password = password;
        }

        public UserBuilder setId(int id) {
            this.id = id;
            return this;
        }

        public UserBuilder setRoleId(int roleId) {
            this.roleId = roleId;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
