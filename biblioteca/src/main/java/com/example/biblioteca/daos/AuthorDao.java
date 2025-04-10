package com.example.biblioteca.daos;

import com.example.biblioteca.entities.Author;

import java.util.List;

public interface AuthorDao {
    List<Author> findAll();
    Author findById(Long id);
    void save(Author author);
    void update(Author author);
    void delete(Long id);
}
