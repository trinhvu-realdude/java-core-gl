package GreatLearning.Week2.Day2.Thread;

import GreatLearning.Week2.Day2.TaskDAOImpl;
import GreatLearning.Week2.Day2.UserDAOImpl;

import java.util.Scanner;

public class ClientThread implements Runnable {
    Scanner sc = new Scanner(System.in);

    @Override
    public void run() {
        UserDAOImpl userDAO = new UserDAOImpl();
        TaskDAOImpl taskDAO = new TaskDAOImpl();

        int checkLoginClient = userDAO.login();

        if (checkLoginClient == 1) {
            if (taskDAO.getLength() != 0) {
                try {
                    menu(taskDAO);
                } catch (NumberFormatException e) {
                    System.err.println("Number format exception!");
                }
            } else {
                try {
                    System.out.println("How many tasks you would like to add?");
                    int size = Integer.parseInt(sc.nextLine());

                    taskDAO = new TaskDAOImpl(size);

                    menu(taskDAO);
                } catch (NumberFormatException | NegativeArraySizeException e) {
                    if (e instanceof NumberFormatException) {
                        System.err.println("Number format exception!");
                    } else {
                        System.err.println("Negative array exception!");
                    }
                }
            }
        } else {
            System.out.println("Credentials wrong! Please try again.");
        }
    }

    public void menu(TaskDAOImpl taskDAO) {
        boolean isStop = false;

        while (!isStop) {
            System.out.println("Please choose one option!");
            System.out.println("1. Press 1 to create a task");
            System.out.println("2. Press 2 to update a specific task");
            System.out.println("3. Press 3 to search tasks");
            System.out.println("4. Press 4 to delete a task");
            System.out.println("5. Press 5 to display all task");
            System.out.println("6. Press 6 to arrange list of tasks in any order");
            System.out.println("0. Press 0 to logout");

            int option = Integer.parseInt(sc.nextLine());

            switch (option) {

                // Create task
                case 1 -> {
                    System.out.println("-- CREATE TASK --");
                    taskDAO.create();
                    System.out.println("-- *-*-*-*-* --");
                }

                // Update task
                case 2 -> {
                    System.out.println("-- UPDATE TASK --");
                    taskDAO.update();
                    System.out.println("-- *-*-*-*-* --");
                }

                // Search task
                case 3 -> {
                    System.out.println("-- SEARCH TASK --");
                    taskDAO.search();
                    System.out.println("-- *-*-*-*-* --");
                }

                // Delete task
                case 4 -> {
                    System.out.println("-- DELETE TASK --");
                    taskDAO.delete();
                    System.out.println("-- *-*-*-*-* --");
                }

                // Display task
                case 5 -> {
                    System.out.println("-- DISPLAY TASK --");
                    taskDAO.display();
                    System.out.println("-- *-*-*-*-* --");
                }

                case 6 -> {
                    System.out.println("-- ARRANGE TASK --");
                    taskDAO.arrange();
                    System.out.println("-- *-*-*-*-* --");
                }

                // Logout
                case 0 -> {
                    isStop = true;
                    System.out.println("Goodbye, see you later!");
                }

                default -> System.out.println("Invalid option!");
            }
        }
    }
}
