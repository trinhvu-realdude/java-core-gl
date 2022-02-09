package GreatLearning.Week2.Day2.Thread;

import GreatLearning.Week2.Day2.TaskDAOImpl;
import GreatLearning.Week2.Day2.UserDAOImpl;

import java.util.Scanner;

public class VisitorThread implements Runnable {

    @Override
    public void run() {
        Scanner sc = new Scanner(System.in);

        UserDAOImpl userDAO = new UserDAOImpl();
        TaskDAOImpl taskDAO = new TaskDAOImpl();

        System.out.println("Enter your name: ");
        String name = sc.nextLine();

        int checkLoginVisitor = userDAO.login(name);

        if (checkLoginVisitor == 1) {
            try {
                menu(name, taskDAO);
            } catch (NumberFormatException e) {
                System.err.println("Number format exception!");
            }
        } else {
            System.out.println("Something wrong! Please try again.");
        }
    }

    public void menu(String name, TaskDAOImpl taskDAO) {
        Scanner sc = new Scanner(System.in);

        boolean isStop = false;

        while (!isStop) {
            System.out.println("Please choose one option!");
            System.out.println("1. Press 1 to display all tasks assigned to you");
            System.out.println("2. Press 2 to change the status of a task");
            System.out.println("3. Press 3 to display the completed tasks");
            System.out.println("4. Press 4 to display the incomplete tasks");
            System.out.println("0. Press 0 to logout");

            int option = Integer.parseInt(sc.nextLine());

            switch (option) {

                // Display all tasks assigned to visitor
                case 1 -> {
                    System.out.println("-- DISPLAY TASK --");
                    taskDAO.displayAssignedTo(name);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Change status
                case 2 -> {
                    System.out.println("-- CHANGE STATUS OF TASK --");
                    taskDAO.changeStatus(name);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Display completed tasks
                case 3 -> {
                    System.out.println("-- COMPLETED TASKS --");
                    taskDAO.displayTaskByStatus(name, "Completed");
                    System.out.println("-- *-*-*-*-* --");
                }

                // Display incomplete tasks
                case 4 -> {
                    System.out.println("-- INCOMPLETE TASKS --");
                    taskDAO.displayTaskByStatus(name, "Incomplete");
                    System.out.println("-- *-*-*-*-* --");
                }

                // Logout
                case 0 -> {
                    isStop = true;
                    System.out.println("Logout successfully!");
                }

                default -> System.out.println("Invalid option!");
            }
        }
    }
}
