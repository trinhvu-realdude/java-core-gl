package greatlearning.week2.day3.service;

public interface ILog {

    void saveRegisterLog(String user, String role);

    void saveLoginLog(String user, String role);

    void saveLogoutLog(String user, String role);

    void saveErrorLog(String error);

    void closeLog();
}
