package greatlearning.week2.day3.implementation;

import greatlearning.week2.day3.service.ILog;

import java.io.*;
import java.time.LocalDateTime;

public class LogImpl implements ILog {
    private LocalDateTime dateTime;
    private BufferedWriter bufferedWriter;

    public LogImpl() {
        try {
            this.dateTime = LocalDateTime.now();

            String url = System.getProperty("user.dir") + "\\src\\greatlearning\\week2\\day3\\log.txt";

            File file = new File(url);

            FileWriter writer = new FileWriter(file, true);

            this.bufferedWriter = new BufferedWriter(writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void saveRegisterLog(String user, String role) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [" + role + " registration] - " + role + " " + user + " register successfully \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println("Input/output exception!");
        }
    }

    @Override
    public void saveLoginLog(String user, String role) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [" + role + " login] - " + role + " " + user + " login successfully \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println("Input/output exception!");
        }
    }

    @Override
    public void saveLogoutLog(String user, String role) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [" + role + " logout] - " + role + " " + user + " logout \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println("Input/output exception!");
        }
    }

    @Override
    public void saveErrorLog(String error) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Error log] - " + error + " \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println("Input/output exception!");
        }
    }

    @Override
    public void closeLog() {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Terminated] - Exit the application \n");
            this.bufferedWriter.flush();
            this.bufferedWriter.close();
        } catch (IOException e) {
            System.err.println("Input/output exception!");
        }
    }
}
