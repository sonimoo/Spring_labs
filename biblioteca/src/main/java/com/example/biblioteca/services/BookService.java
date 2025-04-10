package com.example.biblioteca.services;

import com.example.biblioteca.daos.BookDao;
import com.example.biblioteca.daos.AuthorDao;
import com.example.biblioteca.daos.PublisherDao;
import com.example.biblioteca.dtos.BookDTO;
import com.example.biblioteca.entities.Author;
import com.example.biblioteca.entities.Book;
import com.example.biblioteca.entities.Publisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final PublisherDao publisherDao;

    public List<BookDTO> getAllBooks() {
        return bookDao.findAll().stream()
                .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getPublisher().getId(), List.of()))
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        Book book = bookDao.findById(id);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }
        return new BookDTO(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getPublisher().getId(), List.of());
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Author author = authorDao.findById(bookDTO.getAuthorId());
        Publisher publisher = publisherDao.findById(bookDTO.getPublisherId());

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setPublisher(publisher);
        bookDao.save(book);

        return new BookDTO(book.getId(), book.getTitle(), author.getId(), publisher.getId(), List.of());
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookDao.findById(id);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }

        Author author = authorDao.findById(bookDTO.getAuthorId());
        Publisher publisher = publisherDao.findById(bookDTO.getPublisherId());

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setPublisher(publisher);
        bookDao.update(book);

        return new BookDTO(book.getId(), book.getTitle(), author.getId(), publisher.getId(), List.of());
    }

    public void deleteBook(Long id) {
        bookDao.delete(id);
    }
}
