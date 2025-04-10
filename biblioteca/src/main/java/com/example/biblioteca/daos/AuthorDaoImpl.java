package com.example.biblioteca.daos;

import com.example.biblioteca.entities.Author;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class AuthorDaoImpl implements AuthorDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Author> findAll() {
        return entityManager.createQuery("SELECT a FROM Author a", Author.class).getResultList();
    }

    @Override
    public Author findById(Long id) {
        return entityManager.find(Author.class, id);
    }

    @Override
    public void save(Author author) {
        entityManager.persist(author);
    }

    @Override
    public void update(Author author) {
        entityManager.merge(author);
    }

    @Override
    public void delete(Long id) {
        Author author = findById(id);
        if (author != null) {
            entityManager.remove(author);
        }
    }
}
