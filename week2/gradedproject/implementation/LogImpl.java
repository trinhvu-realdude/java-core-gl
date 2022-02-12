package greatlearning.week2.gradedproject.implementation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

public class LogImpl {

    private LocalDateTime dateTime;
    private BufferedWriter bufferedWriter;

    public LogImpl() {
        try {
            this.dateTime = LocalDateTime.now();

            String url = System.getProperty("user.dir") + "\\src\\greatlearning\\week2\\gradedproject\\log.txt";

            File file = new File(url);

            FileWriter writer = new FileWriter(file, true);

            this.bufferedWriter = new BufferedWriter(writer);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
}
