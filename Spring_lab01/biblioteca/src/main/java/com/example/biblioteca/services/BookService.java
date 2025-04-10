package com.example.biblioteca.services;

import com.example.biblioteca.dtos.BookDTO;
import com.example.biblioteca.entities.Author;
import com.example.biblioteca.entities.Book;
import com.example.biblioteca.entities.Publisher;
import com.example.biblioteca.repositories.AuthorRepository;
import com.example.biblioteca.repositories.BookRepository;
import com.example.biblioteca.repositories.PublisherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;

    public List<BookDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getPublisher().getId(),
                        book.getCategories().stream().map(category -> category.getId()).collect(Collectors.toList())))
                .collect(Collectors.toList());
    }

    public BookDTO getBookById(Long id) {
        return bookRepository.findById(id)
                .map(book -> new BookDTO(book.getId(), book.getTitle(), book.getAuthor().getId(), book.getPublisher().getId(),
                        book.getCategories().stream().map(category -> category.getId()).collect(Collectors.toList())))
                .orElse(null);
    }

    public BookDTO createBook(BookDTO bookDTO) {
        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow();
        Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId()).orElseThrow();

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setPublisher(publisher);
        book = bookRepository.save(book);

        return new BookDTO(book.getId(), book.getTitle(), author.getId(), publisher.getId(), List.of());
    }

    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book book = bookRepository.findById(id).orElseThrow();
        Author author = authorRepository.findById(bookDTO.getAuthorId()).orElseThrow();
        Publisher publisher = publisherRepository.findById(bookDTO.getPublisherId()).orElseThrow();

        book.setTitle(bookDTO.getTitle());
        book.setAuthor(author);
        book.setPublisher(publisher);
        book = bookRepository.save(book);

        return new BookDTO(book.getId(), book.getTitle(), author.getId(), publisher.getId(), List.of());
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}
