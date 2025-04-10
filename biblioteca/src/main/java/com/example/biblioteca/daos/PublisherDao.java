package com.example.biblioteca.daos;

import com.example.biblioteca.entities.Publisher;

import java.util.List;

public interface PublisherDao {
    List<Publisher> findAll();
    Publisher findById(Long id);
    void save(Publisher publisher);
    void update(Publisher publisher);
    void delete(Long id);
}
