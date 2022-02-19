package greatlearning.week3.gradedproject;

import greatlearning.week3.gradedproject.implementation.LogImpl;
import greatlearning.week3.gradedproject.implementation.UserImpl;
import greatlearning.week3.gradedproject.thread.UserThread;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("-- MAGIC OF BOOKS --");

        // create and start log in application
        LogImpl log = LogImpl.getInstance();
        log.startLog();

        UserImpl userImpl = new UserImpl();

        // create a default admin with username: "admin", password: ""
        userImpl.registerAdmin();

        boolean isStop = false;

        // while loop to display 2 options: login and exit
        while (!isStop) {
            System.out.println("1. Press 1 to register");
            System.out.println("2. Press 2 to login");
            System.out.println("0. Press 0 to exit");

            try {
                String option = sc.nextLine();

                switch (option) {

                    // Register
                    case "1" -> {
                        System.out.println("-- REGISTER --");
                        userImpl.register();
                        System.out.println("-- *-*-*-*-* --");
                    }

                    // Login
                    case "2" -> {
                        System.out.println("-- LOGIN --");

                        // Create new thread for user
                        UserThread userThread = new UserThread();
                        Thread u = new Thread(userThread.user);
                        u.setName("user");
                        u.start();
                        u.join();
                    }

                    // Exit
                    case "0" -> {
                        log.saveCloseLog();
                        isStop = true;
                        System.out.println("Goodbye, see you later!");
                    }

                    default -> {
                        log.saveErrorLog("Invalid input");
                        System.out.println("Invalid option");
                    }
                }
            } catch (InterruptedException e) {
                System.err.println("Interrupted exception!");
            }
        }
    }
}
