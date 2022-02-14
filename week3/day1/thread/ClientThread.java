package greatlearning.week3.day1.thread;

import greatlearning.week3.day1.exception.NegativeInputException;
import greatlearning.week3.day1.exception.WrongCredentialsException;
import greatlearning.week3.day1.implementation.TaskDAOImpl;
import greatlearning.week3.day1.implementation.UserDAOImpl;
import greatlearning.week3.day1.implementation.UserLogImpl;

import java.util.Scanner;

public class ClientThread {
    Scanner sc = new Scanner(System.in);

    UserLogImpl userLog = new UserLogImpl();

    public Runnable client = () -> {
        UserDAOImpl userDAO = new UserDAOImpl();
        TaskDAOImpl taskDAO = new TaskDAOImpl();

        try {
            System.out.println("Username: ");
            String userName = sc.nextLine().trim();

            System.out.println("Password: ");
            String password = sc.nextLine().trim();

            int checkLoginClient = userDAO.login(userName, password);

            if (checkLoginClient == 1) {

                userLog.saveLoginLog(userName, "Client");

                if (taskDAO.getLength() != 0) {
                    menu(taskDAO, userName);
                } else {
                    try {
                        System.out.println("How many tasks you would like to add?");
                        int size = Integer.parseInt(sc.nextLine());

                        if (size <= 0) {
                            throw new NegativeInputException();
                        }

                        taskDAO = new TaskDAOImpl(size);

                        menu(taskDAO, userName);
                    } catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Please enter the positive number");
                    }
                }
            } else {
                userLog.saveErrorLog("Wrong credentials");
                throw new WrongCredentialsException();
            }
        } catch (WrongCredentialsException e) {
            System.err.println("Credentials wrong! Please try again.");
        }
    };

    private void menu(TaskDAOImpl taskDAO, String userName) {
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

            String option = sc.nextLine();

            switch (option) {

                // Create task
                case "1" -> {
                    System.out.println("-- CREATE TASK --");
                    taskDAO.create(userName);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Update task
                case "2" -> {
                    System.out.println("-- UPDATE TASK --");
                    taskDAO.update(userName);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Search task
                case "3" -> {
                    System.out.println("-- SEARCH TASK --");
                    taskDAO.search(userName);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Delete task
                case "4" -> {
                    System.out.println("-- DELETE TASK --");
                    taskDAO.delete(userName);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Display task
                case "5" -> {
                    System.out.println("-- DISPLAY TASK --");
                    taskDAO.display(userName);
                    System.out.println("-- *-*-*-*-* --");
                }

                case "6" -> {
                    System.out.println("-- ARRANGE TASK --");
                    taskDAO.arrange(userName);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Logout
                case "0" -> {
                    userLog.saveLogoutLog(userName, "Client");
                    isStop = true;
                    System.out.println("Goodbye, see you later!");
                }

                default -> {
                    userLog.saveErrorLog("Invalid input");
                    System.out.println("Invalid option!");
                }
            }
        }
    }
}
