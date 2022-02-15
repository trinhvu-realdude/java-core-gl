package greatlearning.week3.day1.implementation;

import greatlearning.week3.day1.service.IUserLog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class UserLogImpl implements IUserLog {
    private Date dateTime;
    private BufferedWriter bufferedWriter;

    public UserLogImpl() {
        try {
            this.dateTime = new Date();

            String url = System.getProperty("user.dir") + "\\src\\greatlearning\\week3\\day1\\log.txt";

            File file = new File(url);

            FileWriter writer = new FileWriter(file, true);

            this.bufferedWriter = new BufferedWriter(writer);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void startLog() {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Initiated] - Start the application \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveRegisterLog(String user, String role) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Registration] - " + role + " " + user + " register successfully \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveLoginLog(String user, String role) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Login] - " + role + " " + user + " login successfully \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveLogoutLog(String user, String role) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Logout] - " + role + " " + user + " logout \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void saveErrorLog(String error) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Error] - " + error + " \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public void closeLog() {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Terminated] - Exit the application \n");
            this.bufferedWriter.flush();
            this.bufferedWriter.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveCreateTaskLog(String userName, String title) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Task] - " + userName + " create the task '" + title + "' \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveUpdateTaskLog(String userName, int taskId) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Task] - " + userName + " update the task " + taskId + " \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveSearchTaskLog(String userName, String title) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Task] - " + userName + " search the task '" + title + "' \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveDeleteTaskLog(String userName, int id) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Task] - " + userName + " delete the task " + id + " \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveArrangeTaskLog(String userName, String order) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Task] - " + userName + " arrange the list of task in " + order + " order \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public void saveChangeStatusLog(String name, int id) {
        try {
            this.bufferedWriter.write(this.dateTime + ": [Task] - Visitor " + name + " mark the task " + id + " as completed \n");
            this.bufferedWriter.flush();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
