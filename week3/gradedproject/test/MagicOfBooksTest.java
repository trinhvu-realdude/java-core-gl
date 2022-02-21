package greatlearning.week3.gradedproject.test;

import greatlearning.week3.gradedproject.implementation.MagicOfBooks;
import greatlearning.week3.gradedproject.model.Book;
import greatlearning.week3.gradedproject.model.User;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MagicOfBooksTest {

    MagicOfBooks test = new MagicOfBooks();

    @Test
    public void search() {
        boolean actual = test.search(1);
        assertTrue(actual);

        actual = test.search(3);
        assertTrue(actual);

        actual = test.search(5);
        assertTrue(actual);

        actual = test.search(7);
        assertTrue(actual);

        actual = test.search(10);
        assertTrue(actual);

        // Search the book not exist in list
        actual = test.search(11);
        assertFalse(actual);
    }

    @Test
    public void addNewBook() {
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book.BookBuilder("Long Walk to Freedom", "Nelson Mandela", "Nelson Mandela’s autobiography contains every elements of knowledge you want to know about this legendary leader. Starting from his childhood, growing up in to a freedom fighter, to his twenty seven years in prison, and his significant role in molding up a new, democratic South Africa, this book has it all.", "Autobiography", 14.49, 263).build());
        bookList.add(new Book.BookBuilder("War and Peace", "Leo Tolstoy", "Epic in scale, War and Peace delineates in graphic detail events leading up to Napoleon's invasion of Russia, and the impact of the Napoleonic era on Tsarist society, as seen through the eyes of five Russian aristocratic families.", "Novel", 12.81, 187).build());
        bookList.add(new Book.BookBuilder("I Am Ozzy", "Ozzy Osbourne", "The vocal of Black Sabbath may be not have a good reputation, but, at the end of the day, he is a human being too. And this is exactly what he tells us here. There are many things to learn from this man’s experiences. This is a book written in details and humor.", "Autobiography", 5.97, 127).build());

        // Add a book to new book list successfully
        test.addNewBook(1, new User.UserBuilder("trinh", "123").build(), bookList);
        int expected = 4;
        assertEquals(expected, bookList.size());

        // Failed to add a book to new book list
        test.addNewBook(14, new User.UserBuilder("trinh", "123").build(), bookList);
        assertEquals(expected, bookList.size());
    }

    @Test
    public void addFavourite() {
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book.BookBuilder("Long Walk to Freedom", "Nelson Mandela", "Nelson Mandela’s autobiography contains every elements of knowledge you want to know about this legendary leader. Starting from his childhood, growing up in to a freedom fighter, to his twenty seven years in prison, and his significant role in molding up a new, democratic South Africa, this book has it all.", "Autobiography", 14.49, 263).build());
        bookList.add(new Book.BookBuilder("War and Peace", "Leo Tolstoy", "Epic in scale, War and Peace delineates in graphic detail events leading up to Napoleon's invasion of Russia, and the impact of the Napoleonic era on Tsarist society, as seen through the eyes of five Russian aristocratic families.", "Novel", 12.81, 187).build());
        bookList.add(new Book.BookBuilder("I Am Ozzy", "Ozzy Osbourne", "The vocal of Black Sabbath may be not have a good reputation, but, at the end of the day, he is a human being too. And this is exactly what he tells us here. There are many things to learn from this man’s experiences. This is a book written in details and humor.", "Autobiography", 5.97, 127).build());

        // Add a book to favourite list successfully
        test.addFavourite(1, new User.UserBuilder("trinh", "123").build(), bookList);
        int expected = 4;
        assertEquals(expected, bookList.size());

        // Failed to add a book to favourite list
        test.addFavourite(14, new User.UserBuilder("trinh", "123").build(), bookList);
        assertEquals(expected, bookList.size());
    }

    @Test
    public void addCompleted() {
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book.BookBuilder("Long Walk to Freedom", "Nelson Mandela", "Nelson Mandela’s autobiography contains every elements of knowledge you want to know about this legendary leader. Starting from his childhood, growing up in to a freedom fighter, to his twenty seven years in prison, and his significant role in molding up a new, democratic South Africa, this book has it all.", "Autobiography", 14.49, 263).build());
        bookList.add(new Book.BookBuilder("War and Peace", "Leo Tolstoy", "Epic in scale, War and Peace delineates in graphic detail events leading up to Napoleon's invasion of Russia, and the impact of the Napoleonic era on Tsarist society, as seen through the eyes of five Russian aristocratic families.", "Novel", 12.81, 187).build());
        bookList.add(new Book.BookBuilder("I Am Ozzy", "Ozzy Osbourne", "The vocal of Black Sabbath may be not have a good reputation, but, at the end of the day, he is a human being too. And this is exactly what he tells us here. There are many things to learn from this man’s experiences. This is a book written in details and humor.", "Autobiography", 5.97, 127).build());

        // Add a book to completed list successfully
        test.addCompleted(1, new User.UserBuilder("trinh", "123").build(), bookList);
        int expected = 4;
        assertEquals(expected, bookList.size());

        // Failed to add a book to completed list
        test.addCompleted(14, new User.UserBuilder("trinh", "123").build(), bookList);
        assertEquals(expected, bookList.size());
    }

    @Test
    public void isEmpty() {
        ArrayList<Book> bookList = new ArrayList<>();

        // Check with an empty list
        boolean actual = test.isEmpty(bookList);
        assertTrue(actual);

        // Add some elements and check it again
        bookList.add(new Book.BookBuilder("Long Walk to Freedom", "Nelson Mandela", "Nelson Mandela’s autobiography contains every elements of knowledge you want to know about this legendary leader. Starting from his childhood, growing up in to a freedom fighter, to his twenty seven years in prison, and his significant role in molding up a new, democratic South Africa, this book has it all.", "Autobiography", 14.49, 263).build());
        bookList.add(new Book.BookBuilder("War and Peace", "Leo Tolstoy", "Epic in scale, War and Peace delineates in graphic detail events leading up to Napoleon's invasion of Russia, and the impact of the Napoleonic era on Tsarist society, as seen through the eyes of five Russian aristocratic families.", "Novel", 12.81, 187).build());
        bookList.add(new Book.BookBuilder("I Am Ozzy", "Ozzy Osbourne", "The vocal of Black Sabbath may be not have a good reputation, but, at the end of the day, he is a human being too. And this is exactly what he tells us here. There are many things to learn from this man’s experiences. This is a book written in details and humor.", "Autobiography", 5.97, 127).build());
        actual = test.isEmpty(bookList);
        assertFalse(actual);
    }

    @Test
    public void isDuplicated() {
        ArrayList<Book> bookList = new ArrayList<>();
        bookList.add(new Book.BookBuilder("Long Walk to Freedom", "Nelson Mandela", "Nelson Mandela’s autobiography contains every elements of knowledge you want to know about this legendary leader. Starting from his childhood, growing up in to a freedom fighter, to his twenty seven years in prison, and his significant role in molding up a new, democratic South Africa, this book has it all.", "Autobiography", 14.49, 263).build());
        bookList.add(new Book.BookBuilder("War and Peace", "Leo Tolstoy", "Epic in scale, War and Peace delineates in graphic detail events leading up to Napoleon's invasion of Russia, and the impact of the Napoleonic era on Tsarist society, as seen through the eyes of five Russian aristocratic families.", "Novel", 12.81, 187).build());
        bookList.add(new Book.BookBuilder("I Am Ozzy", "Ozzy Osbourne", "The vocal of Black Sabbath may be not have a good reputation, but, at the end of the day, he is a human being too. And this is exactly what he tells us here. There are many things to learn from this man’s experiences. This is a book written in details and humor.", "Autobiography", 5.97, 127).build());

        // A book already exist in list
        Book book1 = new Book.BookBuilder("I Am Ozzy", "Ozzy Osbourne", "The vocal of Black Sabbath may be not have a good reputation, but, at the end of the day, he is a human being too. And this is exactly what he tells us here. There are many things to learn from this man’s experiences. This is a book written in details and humor.", "Autobiography", 5.97, 127).build();
        boolean actual = test.isDuplicated(book1, bookList);
        assertTrue(actual);

        // Book not exist in list yet
        Book book2 = new Book.BookBuilder("Wizard", "Dr Strange", "so good", "Novel", 14.99, 456).build();
        actual = test.isDuplicated(book2, bookList);
        assertFalse(actual);
    }

    @Test
    public void add() {
        // Add the book already existed in list
        boolean actual = test.add(3, new Book.BookBuilder("Wizard", "Dr Strange", "so good", "Novel", 14.99, 456).build());
        assertFalse(actual);

        actual = test.add(4, new Book.BookBuilder("Wizard", "Dr Strange", "so good", "Novel", 14.99, 456).build());
        assertFalse(actual);

        // Add the book not exist in list
        actual = test.add(11, new Book.BookBuilder("Wizard", "Dr Strange", "so good", "Novel", 14.99, 456).build());
        assertTrue(actual);
    }

    @Test
    public void delete() {
        // Delete a book existing in list
        boolean actual = test.delete(2);
        assertTrue(actual);

        actual = test.delete(9);
        assertTrue(actual);

        // Delete a book not existing in list
        actual = test.delete(11);
        assertFalse(actual);

        // Delete a book deleted completely
        actual = test.delete(2);
        assertFalse(actual);
    }

    @Test
    public void update() {
        // Update a book existing in list
        boolean actual = test.update(10, "Wizard", "Dr Strange", "so good", "Novel", 14.99, 456);
        assertTrue(actual);

        actual = test.update(8, "Wizard", "Dr Strange", "so good", "Novel", 14.99, 456);
        assertTrue(actual);

        // Update a book not existing in list
        actual = test.update(11, "Wizard", "Dr Strange", "so good", "Novel", 14.99, 456);
        assertFalse(actual);
    }
}