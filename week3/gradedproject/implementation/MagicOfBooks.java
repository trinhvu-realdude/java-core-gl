package greatlearning.week3.gradedproject.implementation;

import greatlearning.week3.gradedproject.exception.DuplicateElementException;
import greatlearning.week3.gradedproject.model.Book;
import greatlearning.week3.gradedproject.model.User;
import greatlearning.week3.gradedproject.seed.BookSeed;

import java.util.*;

public class MagicOfBooks {

    static LogImpl log = LogImpl.getInstance();

    private static BookSeed books = new BookSeed();

    private static HashMap<Integer, Book> list = books.getList();

    /**
     * Function: display
     * <p>
     * <p>
     * Display all books in the library
     */
    public void display() {
        for (Integer i : list.keySet()) {
            System.out.println(i + ". " + list.get(i).getName());
        }
        log.saveDisplayBookLog();
    }

    /**
     * Function: search
     *
     * @param id Search book by id, then execute a sub menu to add book to new
     *           book, favourite, completed
     * @requires id > 0
     * @testcases TCs ((actual) -> expected):
     * search(1) -> true,
     * search(3) -> true,
     * search(5) -> true,
     * search(7) -> true,
     * search(10) -> true,
     * search(11) -> false,
     */
    public boolean search(int id) {
        if (list.get(id) == null) {
            log.saveErrorLog("Book not found");
            System.out.println("Book not found");

            return false;
        } else {
            System.out.println("-- DETAIL BOOK --");
            System.out.println("- Id: " + id);
            System.out.println("- Name: " + list.get(id).getName());
            System.out.println("- Author: " + list.get(id).getAuthorName());
            System.out.println("- Description: " + list.get(id).getDescription());
            System.out.println("- Genre: " + list.get(id).getGenre());
            System.out.println("- Price: $" + list.get(id).getPrice());

            return true;
        }
    }

    /**
     * Function: addNewBook
     *
     * @param id
     * @param user
     * @param newBookList Add book to user's new book list
     * @testcases TCs ((actual) -> expected):
     * addNewBook(1, user, bookList) -> 4
     * addNewBook(14, user, bookList) -> 4
     */
    public void addNewBook(int id, User user, ArrayList<Book> newBookList) {
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
    }

    /**
     * Function: addFavourite
     *
     * @param id
     * @param user
     * @param favouriteBookList Add book to user's favourite list
     * @testcases TCs ((actual) -> expected):
     * addFavourite(1, user, bookList) -> 4
     * addFavourite(14, user, bookList) -> 4
     */
    public void addFavourite(int id, User user, ArrayList<Book> favouriteBookList) {
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
    }

    /**
     * Function: addCompleted
     *
     * @param id
     * @param user Add book to user's completed list
     * @testcases TCs ((actual) -> expected):
     * addCompleted(1, user, bookList) -> 4
     * addCompleted(14, user, bookList) -> 4
     */
    public void addCompleted(int id, User user, ArrayList<Book> completedBookList) {
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
    }

    /**
     * Function: displayNewBook
     *
     * @param user Display books in user's new book list
     */
    public void displayNewBook(User user) {
        displayPersonalList(user.getNewBook(), "new book", user.getUserName());
    }

    /**
     * Function: displayFavourite
     *
     * @param user Display books in user's favourite list
     */
    public void displayFavourite(User user) {
        displayPersonalList(user.getFavourite(), "favourite book", user.getUserName());
    }

    /**
     * Function: displayCompleted
     *
     * @param user Display books in user's completed list
     */
    public void displayCompleted(User user) {
        displayPersonalList(user.getCompleted(), "completed book", user.getUserName());
    }

    /**
     * Function: displayPersonalList
     *
     * @param bookList
     * @param list
     * @param userName Display the personal list
     */
    public void displayPersonalList(ArrayList<Book> bookList, String list, String userName) {
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
     * @testcases TCs ((actual) -> expected)
     * isEmpty(new bookList) -> true,
     * isEmpty(bookList.add(new Book)) -> false
     */
    public boolean isEmpty(ArrayList<Book> bookList) {
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
     * @testcases TCs ((actual) -> expected)
     * isDuplicated(book1, bookList.add(book1)) -> true,
     * isDuplicated(book2, bookList.add(book1)) -> false
     */
    public boolean isDuplicated(Book book, ArrayList<Book> bookList) {
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

    /**
     * Function: add
     *
     * @param id
     * @param book Admin adds a new book to the list
     * @testcases TCs ((actual) -> expected)
     * add(3, new Book) -> false,
     * add(4, new Book) -> false,
     * add(11, new Book) -> true
     */
    public boolean add(int id, Book book) {
        for (Integer i : list.keySet()) {
            if (id == i) {
                return false;
            }
        }
        list.put(id, book);
        return true;
    }

    /**
     * Function: delete
     *
     * @param id Admin deletes a book from list
     * @testcases TCs ((actual) -> expected)
     * delete(2) -> true,
     * delete(9) -> true,
     * delete(11) -> false,
     * delete(2) -> false
     */
    public boolean delete(int id) {
        if (list.get(id) != null) {
            list.remove(id);
            return true;
        } else {
            log.saveErrorLog("Book not found");
            System.out.println("Book id " + id + " not found");
            return false;
        }
    }

    /**
     * Function: update
     *
     * @param id Admin updates information of specific book
     * @testcases TCs ((actual) -> expected)
     * update(10, new attributes of book) -> true,
     * update(8, new attributes of book) -> true,
     * update(11, new attributes of book) -> false
     */
    public boolean update(int id, String name, String authorName, String description, String genre, double price, int noCopiedOfSold) {
        if (list.get(id) != null) {
            list.get(id).setBook(name, authorName, description, genre, price, noCopiedOfSold);
            return true;
        }
        return false;
    }

    /**
     * Function: arrangeLowToHigh
     * <p>
     * Admin arranges the list of books in low to high orders
     */
    public void arrangeLowToHigh() {
        HashMap<Integer, Book> cloneList = new HashMap<>(list);
        cloneList.entrySet().stream()
                .sorted(Comparator.comparingDouble(k -> k.getValue().getPrice()))
                .forEach(k -> System.out.println(
                        k.getKey() + ". " + k.getValue().getName() + " ($" + k.getValue().getPrice() + ")"
                ));
    }

    /**
     * Function: arrangeHighToLow
     * <p>
     * Admin arranges the list of books in high to low orders
     */
    public void arrangeHighToLow() {
        HashMap<Integer, Book> cloneList = new HashMap<>(list);
        cloneList.entrySet().stream()
                .sorted(Comparator.comparingDouble(k -> -k.getValue().getPrice()))
                .forEach(k -> System.out.println(
                        k.getKey() + ". " + k.getValue().getName() + " ($" + k.getValue().getPrice() + ")"
                ));
    }

    /**
     * Function: arrangeBestSelling
     * <p>
     * Admin arranges the list of books in bestselling orders
     */
    public void arrangeBestSelling() {
        HashMap<Integer, Book> cloneList = new HashMap<>(list);
        cloneList.entrySet().stream()
                .sorted(Comparator.comparingInt(k -> -k.getValue().getNoOfCopiedSold()))
                .forEach(k -> System.out.println(
                        k.getKey() + ". " + k.getValue().getName() + " (" + k.getValue().getNoOfCopiedSold() + " copies sold)"
                ));
    }

    /**
     * Function: countTotalOfBook
     */
    public void countTotalOfBook() {
        System.out.println("Total count of the books is " + list.size());
    }

    /**
     * Function: displayAutobiographyBook
     */
    public void displayAutobiographyBook() {
        for (Integer i : list.keySet()) {
            if (list.get(i).getGenre().equals("Autobiography")) {
                System.out.println(i + ". " + list.get(i).getName() + " (genre: " + list.get(i).getGenre() + ")");
            }
        }
    }

    public HashMap<Integer, Book> getList() {
        return list;
    }
}
