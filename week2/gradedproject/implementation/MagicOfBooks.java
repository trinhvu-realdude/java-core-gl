package greatlearning.week2.gradedproject.implementation;

import greatlearning.week2.gradedproject.exception.DuplicateElementException;
import greatlearning.week2.gradedproject.exception.NegativeInputException;
import greatlearning.week2.gradedproject.model.Book;
import greatlearning.week2.gradedproject.model.User;
import greatlearning.week2.gradedproject.seed.BookSeed;

import java.util.ArrayList;
import java.util.Scanner;

public class MagicOfBooks {

    static Scanner sc = new Scanner(System.in);

    static LogImpl log = new LogImpl();

    // Declare 3 new arrays to store 3 types of book: new book, favourite, completed
    private static ArrayList<Book> newBookList = new ArrayList<Book>();

    private static ArrayList<Book> favouriteList = new ArrayList<Book>();

    private static ArrayList<Book> completedList = new ArrayList<Book>();

    private static BookSeed books = new BookSeed();

    private static ArrayList<Book> list = books.getList();

    /**
     * Function: display
     * <p>
     * <p>
     * Display all books in the library
     */
    public static void display() {
        for (Book book : list) {
            System.out.println(book.getId() + ". " + book.getName());
        }
        log.saveDisplayBookLog();
    }

    /**
     * Function: search
     *
     * @param user Search book by id, then execute a sub menu to add book to new book, favourite, completed
     */
    public static void search(User user) {
        try {
            System.out.println("Enter the book id you want to search: ");
            int id = Integer.parseInt(sc.nextLine());

            if (id <= 0) {
                throw new NegativeInputException();
            } else {
                if (id > list.size()) {
                    log.saveErrorLog("Book not found");
                    System.out.println("Book not found");
                } else {
                    for (Book book : list) {
                        if (book.getId() == id) {
                            System.out.println("-- DETAIL BOOK --");
                            System.out.println("- Id: " + book.getId());
                            System.out.println("- Name: " + book.getName());
                            System.out.println("- Author: " + book.getAuthorName());
                            System.out.println("- Description: " + book.getDescription());
                        }
                    }

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
    }

    /**
     * Function: subMenu
     *
     * @param id
     * @param user Display sub menu to add book to new book, favourite, completed collection
     */
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
                    addNewBook(id, user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Add book to favourite list
                case "2" -> {
                    System.out.println("-- ADD FAVOURITE LIST --");
                    addFavourite(id, user);
                    System.out.println("-- *-*-*-*-* --");

                }

                // Add book to completed list
                case "3" -> {
                    System.out.println("-- ADD COMPLETED LIST --");
                    addCompleted(id, user);
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

    /**
     * Function: addNewBook
     *
     * @param id
     * @param user Add book to user's new book list
     */
    public static void addNewBook(int id, User user) {
        try {
            System.out.print("Do you want to add this book to your new book collection? (y/n) ");
            String option = sc.nextLine();

            if (option.equals("y")) {
                for (Book book : list) {
                    if (book.getId() == id) {
                        if (isDuplicated(book, user.getNewBook())) {
                            throw new DuplicateElementException();
                        } else {
                            newBookList.add(book);

                            user.setNewBook(newBookList);

                            System.out.println("Add the book " + book.getName() + " to New Book list successfully!");
                        }
                    }
                }

                log.saveAddBookToListLog(user.getUserName(), "add", "new book", id);

            } else if (option.equals("n")) {
                System.out.println("Have a wonderful adventure!");
            } else {
                System.out.println("Please try again!");
            }
        } catch (DuplicateElementException e) {
            System.err.println("This book already existed in list");
        }
    }

    /**
     * Function: addFavourite
     *
     * @param id
     * @param user Add book to user's favourite list
     */
    public static void addFavourite(int id, User user) {
        try {
            System.out.print("Do you want to add this book to your favourite collection? (y/n) ");
            String option = sc.nextLine();

            if (option.equals("y")) {
                for (Book book : list) {
                    if (book.getId() == id) {
                        if (isDuplicated(book, user.getFavourite())) {
                            throw new DuplicateElementException();
                        } else {
                            favouriteList.add(book);

                            user.setFavourite(favouriteList);

                            System.out.println("Add the book " + book.getName() + " to Favourite list successfully!");
                        }
                    }
                }

                log.saveAddBookToListLog(user.getUserName(), "add", "favourite book", id);

            } else if (option.equals("n")) {
                System.out.println("Hope you can find your favourite book!");
            } else {
                System.out.println("Please try again!");
            }
        } catch (DuplicateElementException e) {
            System.err.println("This book already existed in list");
        }
    }

    /**
     * Function: addCompleted
     *
     * @param id
     * @param user Add book to user's completed list
     */
    public static void addCompleted(int id, User user) {
        try {
            System.out.print("Have you finished reading this book yet? (y/n) ");
            String option = sc.nextLine();

            if (option.equals("y")) {
                for (Book book : list) {
                    if (book.getId() == id) {
                        if (isDuplicated(book, user.getCompleted())) {
                            throw new DuplicateElementException();
                        } else {
                            completedList.add(book);

                            user.setCompleted(completedList);

                            System.out.println("Well done!");
                        }
                    }
                }

                log.saveAddBookToListLog(user.getUserName(), "add", "completed book", id);

            } else if (option.equals("n")) {
                System.out.println("Keep your momentum!");
            } else {
                System.out.println("Please try again!");
            }
        } catch (DuplicateElementException e) {
            System.err.println("This book already existed in list");
        }
    }

    /**
     * Function: displayNewBook
     *
     * @param user Display books in user's new book list
     */
    public static void displayNewBook(User user) {
        displayPersonalList(user.getNewBook(), "new book", user.getUserName());
    }

    /**
     * Function: displayFavourite
     *
     * @param user Display books in user's favourite list
     */
    public static void displayFavourite(User user) {
        displayPersonalList(user.getFavourite(), "favourite book", user.getUserName());
    }

    /**
     * Function: displayCompleted
     *
     * @param user Display books in user's completed list
     */
    public static void displayCompleted(User user) {
        displayPersonalList(user.getCompleted(), "completed book", user.getUserName());
    }

    /**
     * Function: displayPersonalList
     *
     * @param bookList
     * @param list
     * @param userName
     *
     * Display the personal list
     */
    public static void displayPersonalList(ArrayList<Book> bookList, String list, String userName) {
        if (isEmpty(bookList)) {
            System.out.println("List is empty!");
        } else {
            for (int i = 0; i < bookList.size(); i++) {
                Book book = bookList.get(i);
                int index = i + 1;
                if (book == null) {
                    break;
                } else {
                    System.out.println(index + ". " + book.getName());
                }
            }

            log.saveDisplayBookLog(userName, list);
        }
    }

    /**
     * Function: isEmpty
     *
     * @param bookList
     * @return true if the list is empty and vice versa
     * <p>
     * Check list whether this is empty
     */
    public static boolean isEmpty(ArrayList<Book> bookList) {
        int check = 0;

        while (check < bookList.size()) {
            if (bookList.get(check) == null) {
                break;
            }
            check++;
        }
        if (check == 0) {
            log.saveErrorLog("List is empty");
            return true;
        }
        return false;
    }

    /**
     * Function: isDuplicated
     *
     * @param book
     * @param bookList
     * @return true if the book added to list already existed and vice versa
     * <p>
     * Check duplicated book in list
     */
    public static boolean isDuplicated(Book book, ArrayList<Book> bookList) {
        for (Book value : bookList) {
            if (value == null) {
                break;
            } else {
                if (book.getName().equals(value.getName())) {
                    log.saveErrorLog("Duplicated book in list");
                    return true;
                }
            }
        }
        return false;
    }
}
