package GreatLearning.Week1.GradedProject;

import java.util.Scanner;

public class MagicOfBooks {

    private static final Scanner sc = new Scanner(System.in);

    // Data Book
    private static final Book b1 = new Book(1, "Anna Karenina", "Leo Tolstoy", "Anna Karenina tells of the doomed love affair between the sensuous and rebellious Anna and the dashing officer, Count Vronsky. Tragedy unfolds as Anna rejects her passionless marriage and must endure the hypocrisies of society. Set against a vast and richly textured canvas of nineteenth-century Russia, the novel's seven major characters create a dynamic imbalance, playing out the contrasts of city and country life and all the variations on love and family happiness. While previous versions have softened the robust, and sometimes shocking, quality of Tolstoy's writing, Pevear and Volokhonsky have produced a translation true to his powerful voice. This award-winning team's authoritative edition also includes an illuminating introduction and explanatory notes. Beautiful, vigorous, and eminently readable, this Anna Karenina will be the definitive text for generations to come.");
    private static final Book b2 = new Book(2, "Madame Bovary", "Gustave Flaubert", "For daring to peer into the heart of an adulteress and enumerate its contents with profound dispassion, the author of Madame Bovary was tried for \"offenses against morality and religion.\" What shocks us today about Flaubert's devastatingly realized tale of a young woman destroyed by the reckless pursuit of her romantic dreams is its pure artistry: the poise of its narrative structure, the opulence of its prose (marvelously captured in the English translation of Francis Steegmuller), and its creation of a world whose minor figures are as vital as its doomed heroine. In reading Madame Bovary, one experiences a work that remains genuinely revolutionary almost a century and a half after its creation.");
    private static final Book b3 = new Book(3, "War and Peace", "Leo Tolstoy", "Epic in scale, War and Peace delineates in graphic detail events leading up to Napoleon's invasion of Russia, and the impact of the Napoleonic era on Tsarist society, as seen through the eyes of five Russian aristocratic families.");
    private static final Book b4 = new Book(4, "The Great Gatsby", "F. Scott Fitzgerald", "The novel chronicles an era that Fitzgerald himself dubbed the \"Jazz Age\". Following the shock and chaos of World War I, American society enjoyed unprecedented levels of prosperity during the \"roaring\" 1920s as the economy soared. At the same time, Prohibition, the ban on the sale and manufacture of alcohol as mandated by the Eighteenth Amendment, made millionaires out of bootleggers and led to an increase in organized crime, for example the Jewish mafia. Although Fitzgerald, like Nick Carraway in his novel, idolized the riches and glamor of the age, he was uncomfortable with the unrestrained materialism and the lack of morality that went with it, a kind of decadence.");
    private static final Book b5 = new Book(5, "Lolita", "Vladimir Nabokov", "The book is internationally famous for its innovative style and infamous for its controversial subject: the protagonist and unreliable narrator, middle aged Humbert Humbert, becomes obsessed and sexually involved with a twelve-year-old girl named Dolores Haze.");
    private static final Book b6 = new Book(6, "Middlemarch", "George Eliot", "Middlemarch: A Study of Provincial Life is a novel by George Eliot, the pen name of Mary Anne Evans, later Marian Evans. It is her seventh novel, begun in 1869 and then put aside during the final illness of Thornton Lewes, the son of her companion George Henry Lewes. During the following year Eliot resumed work, fusing together several stories into a coherent whole, and during 1871–72 the novel appeared in serial form. The first one-volume edition was published in 1874, and attracted large sales. Subtitled \"A Study of Provincial Life\", the novel is set in the fictitious Midlands town of Middlemarch during the period 1830–32. It has a multiple plot with a large cast of characters, and in addition to its distinct though interlocking narratives it pursues a number of underlying themes, including the status of women, the nature of marriage, idealism and self-interest, religion and hypocrisy, political reform, and education. The pace is leisurely, the tone is mildly didactic (with an authorial voice that occasionally bursts through the narrative), and the canvas is very broad.");
    private static final Book b7 = new Book(7, "The Adventures of Huckleberry Finn", "Mark Twain", "Revered by all of the town's children and dreaded by all of its mothers, Huckleberry Finn is indisputably the most appealing child-hero in American literature. Unlike the tall-tale, idyllic world of Tom Sawyer, The Adventures of Huckleberry Finn is firmly grounded in early reality. From the abusive drunkard who serves as Huckleberry's father, to Huck's first tentative grappling with issues of personal liberty and the unknown, Huckleberry Finn endeavors to delve quite a bit deeper into the complexities — both joyful and tragic of life.");
    private static final Book b8 = new Book(8, "The Stories of Anton Chekhov", "Anton Chekhov", "Anton Pavlovich Chekhov was a Russian short-story writer, playwright and physician, considered to be one of the greatest short-story writers in the history of world literature. His career as a dramatist produced four classics and his best short stories are held in high esteem by writers and critics. Chekhov practised as a doctor throughout most of his literary career: \"Medicine is my lawful wife,\" he once said, \"and literature is my mistress\".");
    private static final Book b9 = new Book(9, "In Search of Lost Time", "Marcel Proust", "Swann's Way, the first part of A la recherche de temps perdu, Marcel Proust's seven-part cycle, was published in 1913. In it, Proust introduces the themes that run through the entire work. The narrator recalls his childhood, aided by the famous madeleine; and describes M. Swann's passion for Odette. The work is incomparable. Edmund Wilson said \"[Proust] has supplied for the first time in literature an equivalent in the full scale for the new theory of modern physics.\"");
    private static final Book b10 = new Book(10, "Hamlet", "William Shakespeare", "The Tragedy of Hamlet, Prince of Denmark, or more simply Hamlet, is a tragedy by William Shakespeare, believed to have been written between 1599 and 1601. The play, set in Denmark, recounts how Prince Hamlet exacts revenge on his uncle Claudius, who has murdered Hamlet's father, the King, and then taken the throne and married Gertrude, Hamlet's mother. The play vividly charts the course of real and feigned madness—from overwhelming grief to seething rage—and explores themes of treachery, revenge, incest, and moral corruption.");

    // Store all books in Array
    private static final Book[] list = {b1, b2, b3, b4, b5, b6, b7, b8, b9, b10};

    // Declare 3 new arrays to store 3 types of book: new book, favourite, completed
    private static Book[] newBookList = new Book[100];

    private static Book[] favouriteList = new Book[100];

    private static Book[] completedList = new Book[100];


    static int countNewBook;

    static int countFavourite;

    static int countCompleted;

    /**
     * Function: display
     * <p>
     * Display all books in the library
     */
    public static void display() {
        for (Book book : list) {
            System.out.println(book.getId() + ". " + book.getName());
        }
    }

    /**
     * Function: search
     *
     * @param user Search book by id, then execute a sub menu to add book to new book, favourite, completed
     */
    public static void search(User user) {
        System.out.println("Enter the book id you want to search: ");
        int id = Integer.parseInt(sc.nextLine());

        for (Book book : list) {
            if (book.getId() == id) {
                System.out.println("-- DETAIL BOOK --");
                System.out.println("- Id: " + book.getId());
                System.out.println("- Name: " + book.getName());
                System.out.println("- Author: " + book.getAuthorName());
                System.out.println("- Description: " + book.getDescription());
                System.out.println("-- *-*-*-*-* --");
            }
        }

        // After displaying details of book, ask user to choose some options in sub menu
        subMenu(id, user);
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

            int option = Integer.parseInt(sc.nextLine());

            switch (option) {

                // Add book to new book list
                case 1 -> {
                    System.out.println("-- ADD NEW BOOK LIST --");
                    addNewBook(id, user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Add book to favourite list
                case 2 -> {
                    System.out.println("-- ADD FAVOURITE LIST --");
                    addFavourite(id, user);
                    System.out.println("-- *-*-*-*-* --");

                }

                // Add book to completed list
                case 3 -> {
                    System.out.println("-- ADD COMPLETED LIST --");
                    addCompleted(id, user);
                    System.out.println("-- *-*-*-*-* --");
                }

                // Exit
                case 0 -> {
                    isStop = true;
                }

                default -> System.out.println("Invalid option!");
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
        System.out.print("Do you want to add this book to your new book collection? (y/n) ");
        String option = sc.nextLine();

        if (option.equals("y")) {
            for (Book book : list) {
                if (book.getId() == id) {
                    if (isDuplicated(book, user.getNewBook())) {
                        System.out.println("The book " + book.getName() + " already exist in this list!");
                    } else {
                        newBookList[countNewBook] = book;
                        countNewBook++;

                        user.setNewBook(newBookList);

                        System.out.println("Add the book " + book.getName() + " to New Book list successfully!");
                    }
                }
            }
        } else if (option.equals("n")) {
            System.out.println("Have a wonderful adventure!");
        } else {
            System.out.println("Please try again!");
        }
    }

    /**
     * Function: addFavourite
     *
     * @param id
     * @param user Add book to user's favourite list
     */
    public static void addFavourite(int id, User user) {
        System.out.print("Do you want to add this book to your favourite collection? (y/n) ");
        String option = sc.nextLine();

        if (option.equals("y")) {
            for (Book book : list) {
                if (book.getId() == id) {
                    if (isDuplicated(book, user.getFavourite())) {
                        System.out.println("The book " + book.getName() + " already exist in this list!");
                    } else {
                        favouriteList[countFavourite] = book;
                        countFavourite++;

                        user.setFavourite(favouriteList);

                        System.out.println("Add the book " + book.getName() + " to Favourite list successfully!");
                    }
                }
            }
        } else if (option.equals("n")) {
            System.out.println("Hope you can find your favourite book!");
        } else {
            System.out.println("Please try again!");
        }
    }

    /**
     * Function: addCompleted
     *
     * @param id
     * @param user Add book to user's completed list
     */
    public static void addCompleted(int id, User user) {
        System.out.print("Have you finished reading this book yet? (y/n) ");
        String option = sc.nextLine();

        if (option.equals("y")) {
            for (Book book : list) {
                if (book.getId() == id) {
                    if (isDuplicated(book, user.getCompleted())) {
                        System.out.println("The book " + book.getName() + " already exist in this list!");
                    } else {
                        completedList[countCompleted] = book;
                        countCompleted++;

                        user.setCompleted(completedList);

                        System.out.println("Well done!");
                    }
                }
            }
        } else if (option.equals("n")) {
            System.out.println("Keep your momentum!");
        } else {
            System.out.println("Please try again!");
        }
    }

    /**
     * Function: displayNewBook
     *
     * @param user Display books in user's new book list
     */
    public static void displayNewBook(User user) {
        displayPersonalList(user.getNewBook());
    }

    /**
     * Function: displayFavourite
     *
     * @param user Display books in user's favourite list
     */
    public static void displayFavourite(User user) {
        displayPersonalList(user.getFavourite());
    }

    /**
     * Function: displayCompleted
     *
     * @param user Display books in user's completed list
     */
    public static void displayCompleted(User user) {
        displayPersonalList(user.getCompleted());
    }

    /**
     * Function: displayPersonalList
     *
     * @param bookList Display the personal list
     */
    public static void displayPersonalList(Book[] bookList) {
        if (isEmpty(bookList)) {
            System.out.println("List is empty!");
        } else {
            for (int i = 0; i < bookList.length; i++) {
                Book book = bookList[i];
                int index = i + 1;
                if (book == null) {
                    break;
                } else {
                    System.out.println(index + ". " + book.getName());
                }
            }
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
    public static boolean isEmpty(Book[] bookList) {
        int check = 0;

        while (check < bookList.length) {
            if (bookList[check] == null) {
                break;
            }
            check++;
        }
        return check == 0;
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
    public static boolean isDuplicated(Book book, Book[] bookList) {
        for (Book value : bookList) {
            if (value == null) {
                break;
            } else {
                if (book.getName().equals(value.getName())) {
                    return true;
                }
            }
        }
        return false;
    }
}
