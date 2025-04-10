package com.example.biblioteca.services;

import com.example.biblioteca.daos.AuthorDao;
import com.example.biblioteca.dtos.AuthorDTO;
import com.example.biblioteca.entities.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorDao authorDao;

    public List<AuthorDTO> getAllAuthors() {
        return authorDao.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(Long id) {
        Author author = authorDao.findById(id);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }
        return convertToDTO(author);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        Author author = convertToEntity(authorDTO);
        authorDao.save(author);
        return convertToDTO(author);
    }

    public AuthorDTO updateAuthor(Long id, AuthorDTO authorDTO) {
        Author author = authorDao.findById(id);
        if (author == null) {
            throw new RuntimeException("Author not found");
        }
        author.setName(authorDTO.getName());
        authorDao.update(author);
        return convertToDTO(author);
    }

    public void deleteAuthor(Long id) {
        authorDao.delete(id);
    }

    private AuthorDTO convertToDTO(Author author) {
        return AuthorDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }

    private Author convertToEntity(AuthorDTO authorDTO) {
        return new Author(null, authorDTO.getName());
    }
}
