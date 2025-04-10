package com.example.biblioteca.services;

import com.example.biblioteca.dtos.AuthorDTO;
import com.example.biblioteca.entities.Author;
import com.example.biblioteca.repositories.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;

    public List<AuthorDTO> getAllAuthors() {
        return authorRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        return authorRepository.findById(id).map(this::convertToDTO)
                .orElseThrow(() -> new RuntimeException("Author not found"));
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = new Author();
        author.setName(authorDTO.getName());
        author.setBooks(Collections.emptyList()); // ✅ Устанавливаем пустой список, чтобы избежать NPE
        author = authorRepository.save(author);
        return convertToDTO(author);
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Author not found"));

        author.setName(authorDTO.getName());
        author = authorRepository.save(author);
        return convertToDTO(author);
    }

    public void deleteAuthor(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new RuntimeException("Author not found");
        }
        authorRepository.deleteById(id);
    }

    private AuthorDTO convertToDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .bookIds(author.getBooks() != null
                        ? author.getBooks().stream().map(book -> book.getId()).collect(Collectors.toList())
                        : Collections.emptyList()) // ✅ Если books == null, вернем пустой список
                .build();
    }
}
