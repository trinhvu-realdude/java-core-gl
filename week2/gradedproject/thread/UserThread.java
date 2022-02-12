package greatlearning.week2.gradedproject.thread;

import greatlearning.week2.gradedproject.implementation.MagicOfBooks;
import greatlearning.week2.gradedproject.exception.WrongCredentialsException;
import greatlearning.week2.gradedproject.implementation.LogImpl;
import greatlearning.week2.gradedproject.implementation.UserImpl;
import greatlearning.week2.gradedproject.model.User;

import java.util.Scanner;

public class UserThread implements Runnable {
    static Scanner sc = new Scanner(System.in);

    static LogImpl log = new LogImpl();

    @Override
    public void run() {
        UserImpl user = new UserImpl();

        try {
            System.out.println("Username: ");
            String userName = sc.nextLine();

            System.out.println("Password: ");
            String password = sc.nextLine();

            int loginCheck = user.login(userName, password);

            if (loginCheck == 1) {
                System.out.println("Login successfully!");
                System.out.println("-- Hi, " + userName + "! --");

                log.saveLoginLog(userName);

                menu(user.getUser());
            } else {
                // Login failed
                log.saveErrorLog("Wrong credentials");
                throw new WrongCredentialsException();
            }
        } catch (WrongCredentialsException e) {
            System.err.println("Credentials wrong! Please try again.");
        }
    }

    public static void menu(User user) {
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

            String option = sc.nextLine();

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

                default ->{
                    log.saveErrorLog("Invalid input");
                    System.out.println("Invalid option!");
                }
            }
        }
    }
}
