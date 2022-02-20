package greatlearning.week3.gradedproject.implementation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

/**
 * Class: LogImpl
 * <p>
 * Applied Bill Pugh Singleton patterns
 */
public class LogImpl {

    private Date dateTime;
    private BufferedWriter bufferedWriter;

    /**
     * Inner static class: LogHelper
     * <p>
     * Create a final instance inside class LogHelper
     */
    private static class LogHelper {
        static final LogImpl INSTANCE = new LogImpl();
    }

    /**
     * Private constructor: LogImpl
     */
    private LogImpl() {
        try {
            this.dateTime = new Date();

            String url = System.getProperty("user.dir") + "\\src\\greatlearning\\week3\\gradedproject\\log.txt";

            File file = new File(url);

            FileWriter writer = new FileWriter(file, true);

            this.bufferedWriter = new BufferedWriter(writer);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Function: getInstance
     *
     * @return instance is defined in class LogHelper
     */
    public static LogImpl getInstance() {
        return LogHelper.INSTANCE;
    }

    public void startLog() {
        try {
            this.bufferedWriter.write(dateTime + ": [Initiated] - Start the application \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveRegisterLog(String user) {
        try {
            this.bufferedWriter.write(dateTime + ": [Registration] - User " + user + " register successfully \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveLoginLog(String user) {
        try {
            this.bufferedWriter.write(dateTime + ": [Login] - User " + user + " login successfully \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveErrorLog(String error) {
        try {
            this.bufferedWriter.write(dateTime + ": [Error] - " + error + " \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveLogoutLog(String user) {
        try {
            this.bufferedWriter.write(dateTime + ": [Logout] - User " + user + " logout \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveCloseLog() {
        try {
            this.bufferedWriter.write(dateTime + ": [Terminated] - Exit the application \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveDisplayBookLog() {
        try {
            this.bufferedWriter.write(dateTime + ": [Book] - Display list of books \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveDisplayBookLog(String userName, String list) {
        try {
            this.bufferedWriter.write(dateTime + ": [Book] - User " + userName + " display the " + list + " list \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveSearchBookLog(String userName, String action, int bookId) {
        try {
            this.bufferedWriter.write(dateTime + ": [Book] - User " + userName + " " + action + " book id " + bookId + " \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveAddBookToListLog(String userName, String action, String list, int bookId) {
        try {
            this.bufferedWriter.write(dateTime + ": [Book] - User " + userName + " " + action + " book id " + bookId + " to the " + list + " list \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveArrangeBookLog(String userName, String order) {
        try {
            this.bufferedWriter.write(dateTime + ": [Book] - User " + userName + " arrange the list in the " + order + " order \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
