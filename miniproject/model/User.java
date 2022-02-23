package greatlearning.miniproject.model;

public class User {
    private int id;
    private String userName;
    private String password;
    private int roleId;

    public User(String userName, String password, int roleId) {
        this.userName = userName;
        this.password = password;
        this.roleId = roleId;
    }

    public User(int id, String userName, String password, int roleId) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
