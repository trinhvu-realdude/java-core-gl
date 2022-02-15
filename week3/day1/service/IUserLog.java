package greatlearning.week3.day1.service;

public interface IUserLog {

    void startLog();

    <T2> void saveRegisterLog(T2 user, String role);

    void saveLoginLog(String user, String role);

    void saveLogoutLog(String user, String role);

    void saveErrorLog(String error);

    void closeLog();
}
