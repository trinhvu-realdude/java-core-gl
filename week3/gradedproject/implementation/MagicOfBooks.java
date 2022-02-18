package greatlearning.week3.gradedproject.implementation;

import greatlearning.week3.gradedproject.exception.DuplicateElementException;
import greatlearning.week3.gradedproject.exception.NegativeInputException;
import greatlearning.week3.gradedproject.model.Book;
import greatlearning.week3.gradedproject.model.User;
import greatlearning.week3.gradedproject.seed.BookSeed;

import java.util.*;

public class MagicOfBooks {

    static Scanner sc = new Scanner(System.in);

    static LogImpl log = new LogImpl();

    private static BookSeed books = new BookSeed();

    private static HashMap<Integer, Book> list = books.getList();

    static int bookId = 11;

    /**
     * Function: display
     * <p>
     * <p>
     * Display all books in the library
     */
    public static void display() {
        for (Integer i : list.keySet()) {
            System.out.println(i + ". " + list.get(i).getName());
        }
        log.saveDisplayBookLog();
    }

    /**
     * Function: search
     *
     * @param user Search book by id, then execute a sub menu to add book to new
     *             book, favourite, completed
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
                    System.out.println("-- DETAIL BOOK --");
                    System.out.println("- Id: " + id);
                    System.out.println("- Name: " + list.get(id).getName());
                    System.out.println("- Author: " + list.get(id).getAuthorName());
                    System.out.println("- Description: " + list.get(id).getDescription());
                    System.out.println("- Genre: " + list.get(id).getGenre());
                    System.out.println("- Price: $" + list.get(id).getPrice());

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
     * @param user Display sub menu to add book to new book, favourite, completed
     *             collection
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
            ArrayList<Book> newBookList = user.getNewBook();

            System.out.print("Do you want to add this book to your new book collection? (y/n) ");
            String option = sc.nextLine();

            if (option.equals("y")) {
                for (Integer i : list.keySet()) {
                    if (i == id) {
                        if (isDuplicated(list.get(i), newBookList)) {
                            throw new DuplicateElementException();
                        } else {

                            newBookList.add(list.get(i));

                            user.setNewBook(newBookList);

                            System.out.println("Add the book " + list.get(i).getName() + " to New Book list successfully!");
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
            ArrayList<Book> favouriteBookList = user.getFavourite();

            System.out.print("Do you want to add this book to your favourite collection? (y/n) ");
            String option = sc.nextLine();

            if (option.equals("y")) {
                for (Integer i : list.keySet()) {
                    if (i == id) {
                        if (isDuplicated(list.get(i), favouriteBookList)) {
                            throw new DuplicateElementException();
                        } else {

                            favouriteBookList.add(list.get(i));

                            user.setFavourite(favouriteBookList);

                            System.out.println("Add the book " + list.get(i).getName() + " to Favourite list successfully!");
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
            ArrayList<Book> completedBookList = user.getCompleted();

            System.out.print("Have you finished reading this book yet? (y/n) ");
            String option = sc.nextLine();

            if (option.equals("y")) {
                for (Integer i : list.keySet()) {
                    if (i == id) {
                        if (isDuplicated(list.get(i), completedBookList)) {
                            throw new DuplicateElementException();
                        } else {
                            completedBookList.add(list.get(i));

                            user.setCompleted(completedBookList);

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
     * @param userName Display the personal list
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

    public static void add(User user) {
        try {
            Book book = new Book();

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

            book.setName(name);
            book.setAuthorName(authorName);
            book.setDescription(description);
            book.setGenre(genre);
            book.setPrice(price);
            book.setNoOfCopiedSold(noCopiedOfSold);

            list.put(bookId, book);

            bookId++;
        } catch (NumberFormatException | NegativeInputException e) {
            System.err.println("Please enter right format of number!");
        }
    }

    public static void delete(User user) {
        try {
            System.out.println("Enter the book id you want to delete: ");
            int id = Integer.parseInt(sc.nextLine().trim());

            if (id <= 0) {
                throw new NegativeInputException();
            }

            if (id <= list.size() && list.get(id) != null) {
                System.out.println("-- DETAIL BOOK --");
                System.out.println("- Id: " + id);
                System.out.println("- Name: " + list.get(id).getName());
                System.out.println("- Author: " + list.get(id).getAuthorName());
                System.out.println("- Description: " + list.get(id).getDescription());
                System.out.println("- Genre: " + list.get(id).getGenre());
                System.out.println("- Price: $" + list.get(id).getPrice());
                System.out.println("- No copied of sold: " + list.get(id).getNoOfCopiedSold());

                System.out.print("Are you sure to delete the book [" + id + "]? (y/n) ");
                String option = sc.nextLine();

                if (option.equals("y")) {
                    list.remove(id);

                    System.out.println("Delete successfully!");
                }
            } else {
                System.out.println("Book id " + id + " not found");
            }
        } catch (NegativeInputException | NumberFormatException e) {
            System.err.println("Please enter right format of number!");
        }
    }

    public static void update(User user) {
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

                list.get(id).setName(name);
                list.get(id).setAuthorName(authorName);
                list.get(id).setDescription(description);
                list.get(id).setGenre(genre);
                list.get(id).setPrice(price);
                list.get(id).setNoOfCopiedSold(noCopiedOfSold);

                System.out.println("Update successfully!");

            } else {
                System.out.println("Book id " + id + " not found");
            }
        } catch (NegativeInputException | NumberFormatException e) {
            System.err.println("Please enter right format of number!");
        }
    }

    public static void arrange(User user) {
        HashMap<Integer, Book> cloneList = new HashMap<>(list);

        System.out.println("1. Price low to high");
        System.out.println("2. Price high to low");
        System.out.println("3. Best-selling");

        String option = sc.nextLine();

        switch (option) {

            // Low to high
            case "1" -> {
                System.out.println("-- LOW TO HIGH --");

                cloneList.entrySet().stream()
                        .sorted(Comparator.comparingDouble(k -> k.getValue().getPrice()))
                        .forEach(k -> System.out.println(
                                k.getKey() + ". " + k.getValue().getName() + " ($" + k.getValue().getPrice() + ")"
                        ));
            }

            // High to low
            case "2" -> {
                System.out.println("-- HIGH TO LOW --");

                cloneList.entrySet().stream()
                        .sorted(Comparator.comparingDouble(k -> -k.getValue().getPrice()))
                        .forEach(k -> System.out.println(
                                k.getKey() + ". " + k.getValue().getName() + " ($" + k.getValue().getPrice() + ")"
                        ));
            }

            // Best-selling
            case "3" -> {
                System.out.println("-- BEST SELLING --");

                cloneList.entrySet().stream()
                        .sorted(Comparator.comparingInt(k -> -k.getValue().getNoOfCopiedSold()))
                        .forEach(k -> System.out.println(
                                k.getKey() + ". " + k.getValue().getName() + " (" + k.getValue().getNoOfCopiedSold() + " copies sold)"
                        ));
            }

            default -> System.out.println("Invalid input");
        }
    }

    public static void countTotalOfBook(User user) {
        System.out.println("Total count of the books is " + list.size());
    }

    public static void displayAutobiographyBook(User user) {
        for (Integer i : list.keySet()) {
            if (list.get(i).getGenre().equals("Autobiography")) {
                System.out.println(i + ". " + list.get(i).getName() + " (genre: " + list.get(i).getGenre() + ")");
            }
        }
    }
}
