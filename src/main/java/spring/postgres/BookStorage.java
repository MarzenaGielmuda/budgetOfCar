package spring.postgres;

import spring.type.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookStorage {

    Book getBook(long id);
    List<Book> getAllBooks();
    void addBook(Book book);
    void removeBook(long bookIdToDelete);
}
