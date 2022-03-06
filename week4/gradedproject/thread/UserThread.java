package greatlearning.week4.gradedproject.thread;

import greatlearning.week4.gradedproject.dao.ListBookDAO;
import greatlearning.week4.gradedproject.exception.DuplicateElementException;
import greatlearning.week4.gradedproject.exception.NegativeInputException;
import greatlearning.week4.gradedproject.exception.WrongCredentialsException;
import greatlearning.week4.gradedproject.dao.LogService;
import greatlearning.week4.gradedproject.dao.BookDAO;
import greatlearning.week4.gradedproject.dao.UserDAO;
import greatlearning.week4.gradedproject.model.Book;
import greatlearning.week4.gradedproject.model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class UserThread {
    static Scanner sc = new Scanner(System.in);

    static LogService log = LogService.getInstance();

    static ListBookDAO listBookDAO = ListBookDAO.getInstance();

    static BookDAO bookDAO = BookDAO.getInstance();

    public Runnable user = () -> {
        UserDAO userImpl = UserDAO.getInstance();

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

                if (user.getRoleId() == 1) {
                    menuAdmin(user);
                }
                if (user.getRoleId() == 2) {
                    menuCustomer(user);
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

    public static void menuCustomer(User user) {
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

                    List<Book> books = bookDAO.getAllBooks();

                    for (Book book : books) {
                        System.out.println(book.getId() + ". " + book.getName());
                    }

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
                            Book book = bookDAO.getBookById(id);

                            if (book != null) {
                                System.out.println("-- DETAIL BOOK --");
                                System.out.println("- Id: " + id);
                                System.out.println("- Name: " + book.getName());
                                System.out.println("- Author: " + book.getAuthorName());
                                System.out.println("- Description: " + book.getDescription());
                                System.out.println("- Genre: " + book.getGenre());
                                System.out.println("- Price: $" + book.getPrice());

                                log.saveSearchBookLog(user.getUserName(), "search", id);

                                System.out.println("-- SUB MENU --");

                                // After displaying details of book, ask user to choose some options in sub menu
                                subMenu(id, user);
                            } else {
                                log.saveErrorLog("Book not found");
                                System.out.println("Book not found!");
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
//                    bookDAO.displayNewBook(user);
                    HashMap<Integer, Book> newBookList = listBookDAO.getPersonalList(user.getId(), 1);

                    if (newBookList.size() != 0) {
                        for (Integer id : newBookList.keySet()) {
                            System.out.println(id + ". " + newBookList.get(id).getName());
                        }
                        log.saveDisplayBookLog(user.getUserName(), "new book");
                    } else {
                        System.out.println("List is empty!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 4: Display the favourite list
                case "4" -> {
                    System.out.println("-- MY FAVOURITE LIST --");

                    HashMap<Integer, Book> favouriteList = listBookDAO.getPersonalList(user.getId(), 2);

                    if (favouriteList.size() != 0) {
                        for (Integer id : favouriteList.keySet()) {
                            System.out.println(id + ". " + favouriteList.get(id).getName());
                        }
                        log.saveDisplayBookLog(user.getUserName(), "favourite book");
                    } else {
                        System.out.println("List is empty!");
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                // Case 5: Display the completed list
                case "5" -> {
                    System.out.println("-- MY COMPLETED LIST --");

                    HashMap<Integer, Book> completedList = listBookDAO.getPersonalList(user.getId(), 3);

                    if (completedList.size() != 0) {
                        for (Integer id : completedList.keySet()) {
                            System.out.println(id + ". " + completedList.get(id).getName());
                        }
                        log.saveDisplayBookLog(user.getUserName(), "completed book");
                    } else {
                        System.out.println("List is empty!");
                    }

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

    public void menuAdmin(User user) {
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

                        int bookId = bookDAO.addBook(book);

                        log.saveAddBookToListLog(user.getUserName(), "add", "main", bookId);

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

                        Book book = bookDAO.getBookById(id);

                        if (book != null) {
                            System.out.println("-- DETAIL BOOK --");
                            System.out.println("- Id: " + id);
                            System.out.println("- Name: " + book.getName());
                            System.out.println("- Author: " + book.getAuthorName());
                            System.out.println("- Description: " + book.getDescription());
                            System.out.println("- Genre: " + book.getGenre());
                            System.out.println("- Price: $" + book.getPrice());

                            System.out.println("Are you sure to delete this book? (y/n)");
                            String choose = sc.nextLine();

                            if (choose.equals("y")) {
                                if (bookDAO.deleteBook(id)) {
                                    System.out.println("Delete successfully!");
                                    log.saveAddBookToListLog(user.getUserName(), "delete", "main", id);
                                }
                            } else {
                                System.out.println("Please try again.");
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

                case "3" -> {
                    System.out.println("-- UPDATE BOOK --");

                    try {
                        System.out.println("Which book do you want to update? (Press book id)");
                        int id = Integer.parseInt(sc.nextLine().trim());

                        if (id <= 0) {
                            throw new NegativeInputException();
                        }

                        Book book = bookDAO.getBookById(id);

                        if (book != null) {
                            System.out.println("Enter the new book " + id + " name (" + book.getName() + "):");
                            String name = sc.nextLine().trim();

                            System.out.println("Enter the new book " + id + " author name (" + book.getAuthorName() + "):");
                            String authorName = sc.nextLine().trim();

                            System.out.println("Enter the new book " + id + " description:");
                            String description = sc.nextLine().trim();

                            System.out.println("Enter the new book " + id + " genre (" + book.getGenre() + "):");
                            String genre = sc.nextLine().trim();

                            System.out.println("Enter the new book " + id + " price ($" + book.getPrice() + "):");
                            double price = Double.parseDouble(sc.nextLine().trim());

                            System.out.println("Enter the new book " + id + " no of copied of sold (" + book.getNoOfCopiedSold() + "):");
                            int noCopiedOfSold = Integer.parseInt(sc.nextLine().trim());

                            if (price <= 0 || noCopiedOfSold <= 0) {
                                throw new NegativeInputException();
                            }

                            Book newBook = new Book.BookBuilder(name, authorName, description, genre, price, noCopiedOfSold).setId(id).build();

                            if (bookDAO.updateBook(newBook)) {
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

                    List<Book> books = bookDAO.getAllBooks();

                    for (Book book : books) {
                        System.out.println(book.getId() + ". " + book.getName());
                    }

                    System.out.println("-- *-*-*-*-* --");
                }

                case "5" -> {
                    System.out.println("-- TOTAL OF BOOKS --");
                    int total = bookDAO.countTotalOfBook();

                    System.out.println(total != 0 ? "Total of books in list: " + total : "List is empty!");

                    System.out.println("-- *-*-*-*-* --");
                }

                case "6" -> {
                    System.out.println("-- BOOK AUTOBIOGRAPHY GENRE --");

                    List<Book> books = bookDAO.getAutobiographyBook();

                    if (books.size() != 0) {
                        for (Book book : books) {
                            System.out.println(book.getId() + ". " + book.getName() + " (genre: " + book.getGenre() + ")");
                        }
                    } else {
                        System.out.println("List is empty!");
                    }
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

                            List<Book> books = bookDAO.arrangeLowToHigh();

                            if (books.size() != 0) {
                                for (Book book : books) {
                                    System.out.println(book.getId() + ". " + book.getName() + " $(" + book.getPrice() + ")");
                                }
                            } else {
                                System.out.println("List is empty!");
                            }

                            log.saveArrangeBookLog(user.getUserName(), "low to high");
                        }

                        // High to low
                        case "2" -> {
                            System.out.println("-- HIGH TO LOW --");

                            List<Book> books = bookDAO.arrangeHighToLow();

                            if (books.size() != 0) {
                                for (Book book : books) {
                                    System.out.println(book.getId() + ". " + book.getName() + " $(" + book.getPrice() + ")");
                                }
                            } else {
                                System.out.println("List is empty!");
                            }

                            log.saveArrangeBookLog(user.getUserName(), "high to low");
                        }

                        // Best-selling
                        case "3" -> {
                            System.out.println("-- BEST SELLING --");

                            List<Book> books = bookDAO.arrangeBestSelling();

                            if (books.size() != 0) {
                                for (Book book : books) {
                                    System.out.println(book.getId() + ". " + book.getName() + " (" + book.getNoOfCopiedSold() + " copies sold)");
                                }
                            } else {
                                System.out.println("List is empty!");
                            }

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

    public static void subMenu(int bookId, User user) {
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
                        if (listBookDAO.isDuplicated(user.getId(), bookId, 1)) {
                            throw new DuplicateElementException();
                        }

                        System.out.println("Do you want to add this book to your new book collection? (y/n) ");
                        String choose = sc.nextLine();

                        if (choose.equals("y")) {
                            listBookDAO.addBookToList(user.getId(), bookId, 1);

                            log.saveAddBookToListLog(user.getUserName(), "add", "new book", bookId);

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
                        if (listBookDAO.isDuplicated(user.getId(), bookId, 2)) {
                            throw new DuplicateElementException();
                        }

                        System.out.println("Do you want to add this book to your favourite collection? (y/n) ");
                        String choose = sc.nextLine();

                        if (choose.equals("y")) {
                            listBookDAO.addBookToList(user.getId(), bookId, 2);

                            log.saveAddBookToListLog(user.getUserName(), "add", "favourite book", bookId);

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
                        if (listBookDAO.isDuplicated(user.getId(), bookId, 3)) {
                            throw new DuplicateElementException();
                        }

                        System.out.println("Have you finished reading this book yet? (y/n) ");
                        String choose = sc.nextLine();

                        if (choose.equals("y")) {
                            listBookDAO.addBookToList(user.getId(), bookId, 3);

                            log.saveAddBookToListLog(user.getUserName(), "add", "completed book", bookId);

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
