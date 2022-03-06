package greatlearning.week4.gradedproject.test;

import greatlearning.week4.gradedproject.dao.BookDAO;
import greatlearning.week4.gradedproject.model.Book;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookTest {

    BookDAO test = BookDAO.getInstance();

    @Test
    void getBookById() {
        Book expected = new Book("Hamlet");
        Book actual = test.getBookById(10);
        assertEquals(expected.getName(), actual.getName());

        expected = new Book("Test");
        assertNotEquals(expected.getName(), actual.getName());
    }

    @Test
    void addBook() {
        int expected = 11;
        int actual = test.addBook(new Book.BookBuilder("test", "test", "test", "test", 2.99, 45).build());
        assertEquals(expected, actual);
    }

    @Test
    void getAutobiographyBook() {
        List<Book> expected = new ArrayList<>();
        expected.add(new Book("Long Walk to Freedom"));
        expected.add(new Book("I Am Ozzy"));
        expected.add(new Book("I Know Why the Caged Bird Sings"));
        expected.add(new Book("The Diary of a Young Girl"));

        List<Book> actual = test.getAutobiographyBook();

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getName(), actual.get(i).getName());
        }
    }
}