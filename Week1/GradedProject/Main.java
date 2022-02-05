package GreatLearning.Week1.GradedProject;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Assign a default user with id, username, password
        User user = new User(1, "trinhvu", "123abc");

        System.out.println("-- MAGIC OF BOOKS --");

        boolean isStop = false;

        // while loop to display 2 options: login and exit
        while (!isStop) {
            System.out.println("1. Press 1 to login");
            System.out.println("0. Press 0 to exit");

            int option = Integer.parseInt(sc.nextLine());

            switch (option) {

                // Login
                case 1 -> {
                    System.out.println("-- LOGIN --");

                    System.out.println("Username: ");
                    String userName = sc.nextLine();

                    System.out.println("Password: ");
                    String password = sc.nextLine();

                    if (user.getUserName().equals(userName) && user.getPassword().equals(password)) {
                        System.out.println("Login successfully!");
                        System.out.println("-- Hi, " + user.getUserName() + "! --");

                        // After logging in successfully, user is directed to main menu
                        menu(user);
                    } else {
                        // Login failed
                        System.out.println("Credentials wrong! Please try again.");
                    }
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

    public static void menu(User user) {
        Scanner sc = new Scanner(System.in);

        boolean isStop = false;

        // while loop to display some options in main menu
        while (!isStop) {
            System.out.println("Please choose one option!");
            System.out.println("1. Press 1 to display all books in library");
            System.out.println("2. Press 2 to search a book by id");
            System.out.println("3. Press 3 to display my new books");
            System.out.println("4. Press 4 to display my favourite books");
            System.out.println("5. Press 5 to display my completed books");
            System.out.println("0. Press 0 to logout");

            int option = Integer.parseInt(sc.nextLine());

            switch (option) {

                // Case 1: Display all books in library
                case 1 -> {
                    System.out.println("-- DISPLAY ALL BOOKS --");
                    MagicOfBooks.display();
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 2: Search book by id
                case 2 -> {
                    System.out.println("-- SEARCH BOOK BY ID --");
                    MagicOfBooks.search(user);
                    System.out.println("-- *-*-*-*-* --");
                }


                // Case 3: Display the new books list
                case 3 -> {
                    System.out.println("-- MY NEW BOOKS --");
                    MagicOfBooks.displayNewBook(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 4: Display the favourite list
                case 4 -> {
                    System.out.println("-- MY FAVOURITE LIST --");
                    MagicOfBooks.displayFavourite(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 5: Display the completed list
                case 5 -> {
                    System.out.println("-- MY COMPLETED LIST --");
                    MagicOfBooks.displayCompleted(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 0: Logout
                case 0 -> {
                    isStop = true;
                    System.out.println("Logout successfully!");
                }

                default -> System.out.println("Invalid option!");
            }
        }
    }
}
