package greatlearning.week1.Day5;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("-- TO DO MANAGER --");

        boolean isStop = false;

        while (!isStop) {
            System.out.println("1. Press 1 to register");
            System.out.println("2. Press 2 to login");
            System.out.println("0. Press 0 to exit");

            String input = sc.nextLine();
            int option = Integer.parseInt(input);

            switch (option) {

                // Register
                case 1 -> {
                    System.out.println("-- REGISTRATION --");
                    System.out.println("1. Press 1 to register as a client");
                    System.out.println("2. Press 2 to register as a visitor");

                    String in = sc.nextLine();
                    int registerOption = Integer.parseInt(in);

                    UserDAOImpl userDAO = new UserDAOImpl();

                    if (registerOption == 1) {
                        Client client = new Client();
                        userDAO.register(client);
                    } else if (registerOption == 2) {
                        Visitor visitor = new Visitor();
                        userDAO.register(visitor);
                    } else {
                        System.out.println("Please try again.");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                // Login
                case 2 -> {
                    System.out.println("-- LOGIN --");
                    System.out.println("1. Press 1 to login as a client");
                    System.out.println("2. Press 2 to login as a visitor");

                    String in = sc.nextLine();
                    int loginOption = Integer.parseInt(in);

                    UserDAOImpl userDAO = new UserDAOImpl();

                    if (loginOption == 1) {

                        int checkLoginClient = userDAO.login();

                        if (checkLoginClient == 1) {
                            menuClient();
                        } else {
                            System.out.println("Credentials wrong! Please try again.");
                        }
                    } else if (loginOption == 2) {

                        System.out.println("Enter your name: ");
                        String name = sc.nextLine();

                        int checkLoginVisitor = userDAO.login(name);

                        if (checkLoginVisitor == 1) {
                            menuVisitor(name);
                        } else {
                            System.out.println("Something wrong! Please try again.");
                        }
                    } else {
                        System.out.println("Please try again.");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                // Exit
                case 0 -> {
                    isStop = true;
                    System.out.println("Goodbye, see you later!");
                }

                default -> System.out.println("Invalid option");
            }
        }
    }

    public static void menuClient() {
        Scanner sc = new Scanner(System.in);

        System.out.println("How many tasks you would like to add?");
        int size = sc.nextInt();

        TaskDAOImpl taskDAO = new TaskDAOImpl(size);

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

            int option = sc.nextInt();

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

    public static void menuVisitor(String name) {
        Scanner sc = new Scanner(System.in);
        boolean isStop = false;

        TaskDAOImpl taskDAO = new TaskDAOImpl();

        while (!isStop) {
            System.out.println("Please choose one option!");
            System.out.println("1. Press 1 to display all tasks assigned to you");
            System.out.println("2. Press 2 to change the status of a task");
            System.out.println("3. Press 3 to display the completed tasks");
            System.out.println("4. Press 4 to display the incomplete tasks");
            System.out.println("0. Press 0 to logout");

            int option = sc.nextInt();

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
