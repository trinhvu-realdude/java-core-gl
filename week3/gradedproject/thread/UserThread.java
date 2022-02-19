package greatlearning.week3.gradedproject.thread;

import greatlearning.week3.gradedproject.exception.WrongCredentialsException;
import greatlearning.week3.gradedproject.implementation.LogImpl;
import greatlearning.week3.gradedproject.implementation.MagicOfBooks;
import greatlearning.week3.gradedproject.implementation.UserImpl;
import greatlearning.week3.gradedproject.model.Admin;
import greatlearning.week3.gradedproject.model.User;

import java.util.Scanner;

public class UserThread {
    static Scanner sc = new Scanner(System.in);

    static LogImpl log = LogImpl.getInstance();

    public Runnable user = () -> {
        UserImpl userImpl = new UserImpl();

        try {
            System.out.println("Username: ");
            String userName = sc.nextLine().trim();

            System.out.println("Password: ");
            String password = sc.nextLine().trim();

            User user = userImpl.login(userName, password);

            if (user != null) {
                System.out.println("Login successfully!");
                System.out.println("-- Hi, " + userName + "! --");

                log.saveLoginLog(userName);

                if (user instanceof Admin) {
                    menuAdmin(user);
                } else {
                    menuUser(user);
                }
            } else {
                // Login failed
                log.saveErrorLog("Wrong credentials");
                throw new WrongCredentialsException();
            }
        } catch (WrongCredentialsException e) {
            System.err.println("Credentials wrong! Please try again.");
        }
    };

    public static void menuUser(User user) {
        boolean isStop = false;

        // while loop to display some options in main menu
        while (!isStop) {
            System.out.println("-- USER OPTION --");
            System.out.println("1. Press 1 to display all books in library");
            System.out.println("2. Press 2 to search a book by id");
            System.out.println("3. Press 3 to display my new books");
            System.out.println("4. Press 4 to display my favourite books");
            System.out.println("5. Press 5 to display my completed books");
            System.out.println("0. Press 0 to logout");

            String option = sc.nextLine().trim();

            switch (option) {

                // Case 1: Display all books in library
                case "1" -> {
                    System.out.println("-- DISPLAY ALL BOOKS --");
                    MagicOfBooks.display();
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 2: Search book by id
                case "2" -> {
                    System.out.println("-- SEARCH BOOK BY ID --");
                    MagicOfBooks.search(user);
                    System.out.println("-- *-*-*-*-* --");
                }


                // Case 3: Display the new books list
                case "3" -> {
                    System.out.println("-- MY NEW BOOKS --");
                    MagicOfBooks.displayNewBook(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 4: Display the favourite list
                case "4" -> {
                    System.out.println("-- MY FAVOURITE LIST --");
                    MagicOfBooks.displayFavourite(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 5: Display the completed list
                case "5" -> {
                    System.out.println("-- MY COMPLETED LIST --");
                    MagicOfBooks.displayCompleted(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 0: Logout
                case "0" -> {
                    log.saveLogoutLog(user.getUserName());
                    isStop = true;
                    System.out.println("Logout successfully!");
                }

                default -> {
                    log.saveErrorLog("Invalid input");
                    System.out.println("Invalid option!");
                }
            }
        }
    }

    public static void menuAdmin(User user) {
        boolean isStop = false;

        while (!isStop) {
            System.out.println("-- ADMIN OPTION --");
            System.out.println("1. Press 1 to add a new book");
            System.out.println("2. Press 2 to delete a book");
            System.out.println("3. Press 3 to update a book");
            System.out.println("4. Press 4 to display all the books");
            System.out.println("5. Press 5 to see total count of the books");
            System.out.println("6. Press 6 to see all the books under Autobiography genre");
            System.out.println("7. Press 7 to arrange the book in the orders");
            System.out.println("0. Press 0 to logout");

            String option = sc.nextLine().trim();

            switch (option) {

                case "1" -> {
                    System.out.println("-- ADD NEW BOOK --");
                    MagicOfBooks.add(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                case "2" -> {
                    System.out.println("-- DELETE BOOK --");
                    MagicOfBooks.delete(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                case "3" -> {
                    System.out.println("-- UPDATE BOOK --");
                    MagicOfBooks.update(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                case "4" -> {
                    System.out.println("-- DISPLAY ALL BOOKS --");
                    MagicOfBooks.display();
                    System.out.println("-- *-*-*-*-* --");
                }

                case "5" -> {
                    System.out.println("-- TOTAL OF BOOKS --");
                    MagicOfBooks.countTotalOfBook(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                case "6" -> {
                    System.out.println("-- BOOK AUTOBIOGRAPHY GENRE --");
                    MagicOfBooks.displayAutobiographyBook(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                case "7" -> {
                    System.out.println("-- ARRANGE BOOK --");
                    MagicOfBooks.arrange(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                case "0" -> {
                    log.saveLogoutLog(user.getUserName());
                    isStop = true;
                    System.out.println("Logout successfully!");
                }

                default -> {
                    log.saveErrorLog("Invalid input");
                    System.out.println("Invalid option!");
                }
            }
        }
    }
}
