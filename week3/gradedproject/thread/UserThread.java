package greatlearning.week3.gradedproject.thread;

import greatlearning.week3.gradedproject.exception.DuplicateElementException;
import greatlearning.week3.gradedproject.exception.NegativeInputException;
import greatlearning.week3.gradedproject.exception.WrongCredentialsException;
import greatlearning.week3.gradedproject.implementation.LogImpl;
import greatlearning.week3.gradedproject.implementation.MagicOfBooks;
import greatlearning.week3.gradedproject.implementation.UserImpl;
import greatlearning.week3.gradedproject.model.Admin;
import greatlearning.week3.gradedproject.model.Book;
import greatlearning.week3.gradedproject.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class UserThread {
    static Scanner sc = new Scanner(System.in);

    static LogImpl log = LogImpl.getInstance();

    static int bookId = 11;

    static MagicOfBooks bookDAO = new MagicOfBooks();

    static HashMap<Integer, Book> list = bookDAO.getList();

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
                    bookDAO.display();
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 2: Search book by id
                case "2" -> {
                    System.out.println("-- SEARCH BOOK BY ID --");

                    try {
                        System.out.println("Enter the book id you want to search: ");
                        int id = Integer.parseInt(sc.nextLine());

                        if (id <= 0) {
                            throw new NegativeInputException();
                        } else {
                            if (bookDAO.search(id)) {
                                log.saveSearchBookLog(user.getUserName(), "search", id);

                                System.out.println("-- SUB MENU --");

                                // After displaying details of book, ask user to choose some options in sub menu
                                subMenu(id, user);
                            }
                        }
                    } catch (NegativeInputException | NumberFormatException e) {
                        log.saveErrorLog("Invalid input");
                        System.err.println("Please enter the positive number!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }


                // Case 3: Display the new books list
                case "3" -> {
                    System.out.println("-- MY NEW BOOKS --");
                    bookDAO.displayNewBook(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 4: Display the favourite list
                case "4" -> {
                    System.out.println("-- MY FAVOURITE LIST --");
                    bookDAO.displayFavourite(user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 5: Display the completed list
                case "5" -> {
                    System.out.println("-- MY COMPLETED LIST --");
                    bookDAO.displayCompleted(user);
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

                    try {
                        System.out.println("Enter the book name: ");
                        String name = sc.nextLine().trim();

                        System.out.println("Enter the author name: ");
                        String authorName = sc.nextLine().trim();

                        System.out.println("Enter the description: ");
                        String description = sc.nextLine().trim();

                        System.out.println("Enter the book genre: ");
                        String genre = sc.nextLine().trim();

                        System.out.println("Enter the book price: ");
                        double price = Double.parseDouble(sc.nextLine().trim());

                        System.out.println("Enter the no copied of sold for this book:");
                        int noCopiedOfSold = Integer.parseInt(sc.nextLine().trim());

                        if (price <= 0 || noCopiedOfSold <= 0) {
                            throw new NegativeInputException();
                        }

                        Book book = new Book.BookBuilder(name, authorName, description, genre, price, noCopiedOfSold).build();


                        if (bookDAO.add(bookId, book)) {
                            System.out.println("Add a new book successfully!");

                            log.saveAddBookToListLog(user.getUserName(), "add", "main", bookId);

                            bookId++;
                        }
                    } catch (NumberFormatException | NegativeInputException e) {
                        log.saveErrorLog("Invalid input");
                        System.err.println("Please enter right format of number!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "2" -> {
                    System.out.println("-- DELETE BOOK --");

                    try {
                        System.out.println("Enter the book id you want to delete: ");
                        int id = Integer.parseInt(sc.nextLine().trim());

                        if (id <= 0) {
                            throw new NegativeInputException();
                        }

                        if (bookDAO.delete(id)) {
                            System.out.println("Delete successfully!");

                            log.saveAddBookToListLog(user.getUserName(), "delete", "main", id);
                        }

                    } catch (NegativeInputException | NumberFormatException e) {
                        log.saveErrorLog("Invalid input");
                        System.err.println("Please enter right format of number!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "3" -> {
                    System.out.println("-- UPDATE BOOK --");

                    try {
                        System.out.println("Which book do you want to update? (Press book id)");
                        int id = Integer.parseInt(sc.nextLine().trim());

                        if (id <= 0) {
                            throw new NegativeInputException();
                        }

                        if (id <= list.size() && list.get(id) != null) {
                            System.out.println("Enter the new book " + id + " name (" + list.get(id).getName() + "):");
                            String name = sc.nextLine().trim();

                            System.out.println("Enter the new book " + id + " author name (" + list.get(id).getAuthorName() + "):");
                            String authorName = sc.nextLine().trim();

                            System.out.println("Enter the new book " + id + " description:");
                            String description = sc.nextLine().trim();

                            System.out.println("Enter the new book " + id + " genre (" + list.get(id).getGenre() + "):");
                            String genre = sc.nextLine().trim();

                            System.out.println("Enter the new book " + id + " price ($" + list.get(id).getPrice() + "):");
                            double price = Double.parseDouble(sc.nextLine().trim());

                            System.out.println("Enter the new book " + id + " no of copied of sold (" + list.get(id).getNoOfCopiedSold() + "):");
                            int noCopiedOfSold = Integer.parseInt(sc.nextLine().trim());

                            if (price <= 0 || noCopiedOfSold <= 0) {
                                throw new NegativeInputException();
                            }

                            if (bookDAO.update(id, name, authorName, description, genre, price, noCopiedOfSold)) {
                                System.out.println("Update successfully!");

                                log.saveAddBookToListLog(user.getUserName(), "update", "main", id);
                            }
                        } else {
                            log.saveErrorLog("Book not found");
                            System.out.println("Book id " + id + " not found");
                        }
                    } catch (NegativeInputException | NumberFormatException e) {
                        log.saveErrorLog("Invalid input");
                        System.err.println("Please enter right format of number!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "4" -> {
                    System.out.println("-- DISPLAY ALL BOOKS --");
                    bookDAO.display();
                    System.out.println("-- *-*-*-*-* --");
                }

                case "5" -> {
                    System.out.println("-- TOTAL OF BOOKS --");
                    bookDAO.countTotalOfBook();
                    System.out.println("-- *-*-*-*-* --");
                }

                case "6" -> {
                    System.out.println("-- BOOK AUTOBIOGRAPHY GENRE --");
                    bookDAO.displayAutobiographyBook();
                    System.out.println("-- *-*-*-*-* --");
                }

                case "7" -> {
                    System.out.println("-- ARRANGE BOOK --");

                    System.out.println("1. Price low to high");
                    System.out.println("2. Price high to low");
                    System.out.println("3. Best-selling");

                    String choose = sc.nextLine();

                    switch (choose) {

                        // Low to high
                        case "1" -> {
                            System.out.println("-- LOW TO HIGH --");

                            bookDAO.arrangeLowToHigh();

                            log.saveArrangeBookLog(user.getUserName(), "low to high");
                        }

                        // High to low
                        case "2" -> {
                            System.out.println("-- HIGH TO LOW --");

                            bookDAO.arrangeHighToLow();

                            log.saveArrangeBookLog(user.getUserName(), "high to low");
                        }

                        // Best-selling
                        case "3" -> {
                            System.out.println("-- BEST SELLING --");

                            bookDAO.arrangeBestSelling();

                            log.saveArrangeBookLog(user.getUserName(), "best selling");
                        }

                        default -> {
                            log.saveErrorLog("Invalid input");
                            System.out.println("Invalid input");
                        }
                    }
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

    public static void subMenu(int id, User user) {
        boolean isStop = false;

        while (!isStop) {
            System.out.println("1. Add to my new book list");
            System.out.println("2. Add to my favourite list");
            System.out.println("3. Add to my completed list");
            System.out.println("0. Exit");

            String option = sc.nextLine();

            switch (option) {

                // Add book to new book list
                case "1" -> {
                    System.out.println("-- ADD NEW BOOK LIST --");

                    try {
                        ArrayList<Book> newBookList = user.getNewBook();

                        System.out.print("Do you want to add this book to your new book collection? (y/n) ");
                        String choose = sc.nextLine();

                        if (choose.equals("y")) {
                            bookDAO.addNewBook(id, user, newBookList);

                            log.saveAddBookToListLog(user.getUserName(), "add", "new book", id);

                        } else if (choose.equals("n")) {
                            System.out.println("Have a wonderful adventure!");
                        } else {
                            System.out.println("Please try again!");
                        }
                    } catch (DuplicateElementException e) {
                        System.err.println("This book already existed in list");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                // Add book to favourite list
                case "2" -> {
                    System.out.println("-- ADD FAVOURITE LIST --");

                    try {
                        ArrayList<Book> favouriteBookList = user.getFavourite();

                        System.out.print("Do you want to add this book to your favourite collection? (y/n) ");
                        String choose = sc.nextLine();

                        if (choose.equals("y")) {
                            bookDAO.addFavourite(id, user, favouriteBookList);

                            log.saveAddBookToListLog(user.getUserName(), "add", "favourite book", id);

                        } else if (choose.equals("n")) {
                            System.out.println("Hope you can find your favourite book!");
                        } else {
                            System.out.println("Please try again!");
                        }
                    } catch (DuplicateElementException e) {
                        System.err.println("This book already existed in list");
                    }

                    System.out.println("-- *-*-*-*-* --");

                }

                // Add book to completed list
                case "3" -> {
                    System.out.println("-- ADD COMPLETED LIST --");
                    try {
                        ArrayList<Book> completedBookList = user.getCompleted();

                        System.out.print("Have you finished reading this book yet? (y/n) ");
                        String choose = sc.nextLine();

                        if (choose.equals("y")) {
                            bookDAO.addCompleted(id, user, completedBookList);

                            log.saveAddBookToListLog(user.getUserName(), "add", "completed book", id);

                        } else if (choose.equals("n")) {
                            System.out.println("Keep your momentum!");
                        } else {
                            System.out.println("Please try again!");
                        }
                    } catch (DuplicateElementException e) {
                        System.err.println("This book already existed in list");
                    }
                    System.out.println("-- *-*-*-*-* --");
                }

                // Exit
                case "0" -> {
                    isStop = true;
                }

                default -> {
                    log.saveErrorLog("Invalid input");
                    System.out.println("Invalid option!");
                }
            }
        }
    }
}
