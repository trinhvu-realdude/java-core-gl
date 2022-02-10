package greatlearning.week1.Day3;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("-- TO DO MANAGER --");
        System.out.println("Enter your name: ");
        String user = sc.nextLine();

        System.out.println("-- Hi, " + user + "! --");

        System.out.println("How many tasks you would like to add?");
        int size = sc.nextInt();

        Task[] tasks = new Task[size];

        boolean isStop = false;

        while (!isStop) {
            System.out.println("Please choose one option!");
            System.out.println("1. Press 1 to create a task");
            System.out.println("2. Press 2 to update a specific task");
            System.out.println("3. Press 3 to search tasks");
            System.out.println("4. Press 4 to delete a task");
            System.out.println("5. Press 5 to display all task");
            System.out.println("0. Press 0 to exit the application");

            int option = sc.nextInt();

            switch (option) {

                case 1 -> {
                    System.out.println("-- CREATE TASK --");
                    DAO.create(tasks);
                    System.out.println("-- *-*-*-*-* --");
                }

                case 2 -> {
                    System.out.println("-- UPDATE TASK --");
                    DAO.update(tasks);
                    System.out.println("-- *-*-*-*-* --");
                }

                case 3 -> {
                    System.out.println("-- SEARCH TASK --");
                    DAO.search(tasks);
                    System.out.println("-- *-*-*-*-* --");
                }

                case 4 -> {
                    System.out.println("-- DELETE TASK --");
                    DAO.delete(tasks);
                    System.out.println("-- *-*-*-*-* --");
                }

                case 5 -> {
                    System.out.println("-- DISPLAY TASK --");
                    DAO.display(tasks);
                    System.out.println("-- *-*-*-*-* --");
                }

                case 0 -> {
                    isStop = true;
                    System.out.println("Goodbye, see you later!");
                }

                default -> System.out.println("Invalid option!");
            }

        }
    }
}
