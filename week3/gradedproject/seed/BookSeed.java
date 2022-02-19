package greatlearning.week3.gradedproject.seed;

import greatlearning.week3.gradedproject.model.Book;

import java.util.HashMap;

public class BookSeed {

    // Store all books in Array
    private HashMap<Integer, Book> list = new HashMap<Integer, Book>();

    public BookSeed() {
        // Default data Book
        Book b1 = new Book.BookBuilder("Anna Karenina", "Leo Tolstoy", "Anna Karenina tells of the doomed love affair between the sensuous and rebellious Anna and the dashing officer, Count Vronsky. Tragedy unfolds as Anna rejects her passionless marriage and must endure the hypocrisies of society. Set against a vast and richly textured canvas of nineteenth-century Russia, the novel's seven major characters create a dynamic imbalance, playing out the contrasts of city and country life and all the variations on love and family happiness.", "Novel", 18.21, 254).build();

        Book b2 = b1.clone();
        b2.setBook("Long Walk to Freedom", "Nelson Mandela", "Nelson Mandela’s autobiography contains every elements of knowledge you want to know about this legendary leader. Starting from his childhood, growing up in to a freedom fighter, to his twenty seven years in prison, and his significant role in molding up a new, democratic South Africa, this book has it all.", "Autobiography", 14.49, 263);

        Book b3 = b1.clone();
        b3.setBook("War and Peace", "Leo Tolstoy", "Epic in scale, War and Peace delineates in graphic detail events leading up to Napoleon's invasion of Russia, and the impact of the Napoleonic era on Tsarist society, as seen through the eyes of five Russian aristocratic families.", "Novel", 12.81, 187);

        Book b4 = b1.clone();
        b4.setBook("I Am Ozzy", "Ozzy Osbourne", "The vocal of Black Sabbath may be not have a good reputation, but, at the end of the day, he is a human being too. And this is exactly what he tells us here. There are many things to learn from this man’s experiences. This is a book written in details and humor.", "Autobiography", 5.97, 127);

        Book b5 = b1.clone();
        b5.setBook("Lolita", "Vladimir Nabokov", "The book is internationally famous for its innovative style and infamous for its controversial subject: the protagonist and unreliable narrator, middle aged Humbert Humbert, becomes obsessed and sexually involved with a twelve-year-old girl named Dolores Haze.", "Novel", 12.99, 441);

        Book b6 = b1.clone();
        b6.setBook("I Know Why the Caged Bird Sings", "Maya Angelou", "This autobiography is the first of Maya’s seven autobiographies, but this has claimed fame for her. This book tells a wonderful, emotional journey of a struggling Black American, who went through bitter experiences in the course of her first seventeen years.", "Autobiography", 10.49, 379);

        Book b7 = b1.clone();
        b7.setBook("The Diary of a Young Girl", "Anne Frank", "Anne Frank was a Jewish girl, who, along with her family and few friends, went into hiding during World War II. This beautiful piece describes everything that a thirteen year old girl would experience: typical girlhood consciousness, friendships with other girls, her crushes on boys, and her academic performances.", "Autobiography", 11.95, 453);

        Book b8 = b1.clone();
        b8.setBook("The Stories of Anton Chekhov", "Anton Chekhov", "Anton Pavlovich Chekhov was a Russian short-story writer, playwright and physician, considered to be one of the greatest short-story writers in the history of world literature. His career as a dramatist produced four classics and his best short stories are held in high esteem by writers and critics. Chekhov practised as a doctor throughout most of his literary career: \"Medicine is my lawful wife,\" he once said, \"and literature is my mistress\".", "Fiction", 13.99, 374);

        Book b9 = b1.clone();
        b9.setBook("In Search of Lost Time", "Marcel Proust", "Swann's Way, the first part of A la recherche de temps perdu, Marcel Proust's seven-part cycle, was published in 1913. In it, Proust introduces the themes that run through the entire work. The narrator recalls his childhood, aided by the famous madeleine; and describes M. Swann's passion for Odette. The work is incomparable. Edmund Wilson said \"[Proust] has supplied for the first time in literature an equivalent in the full scale for the new theory of modern physics.\"", "Philosophical Fiction", 63.99, 482);

        Book b10 = b1.clone();
        b10.setBook("Hamlet", "William Shakespeare", "The Tragedy of Hamlet, Prince of Denmark, or more simply Hamlet, is a tragedy by William Shakespeare, believed to have been written between 1599 and 1601. The play, set in Denmark, recounts how Prince Hamlet exacts revenge on his uncle Claudius, who has murdered Hamlet's father, the King, and then taken the throne and married Gertrude, Hamlet's mother. The play vividly charts the course of real and feigned madness—from overwhelming grief to seething rage—and explores themes of treachery, revenge, incest, and moral corruption.", "Tragedy", 6.29, 240);

        list.put(1, b1);
        list.put(2, b2);
        list.put(3, b3);
        list.put(4, b4);
        list.put(5, b5);
        list.put(6, b6);
        list.put(7, b7);
        list.put(8, b8);
        list.put(9, b9);
        list.put(10, b10);
    }

    public HashMap<Integer, Book> getList() {
        return list;
    }
}
