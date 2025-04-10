package com.example.biblioteca.daos;

import com.example.biblioteca.entities.Publisher;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public class PublisherDaoImpl implements PublisherDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Publisher> findAll() {
        return entityManager.createQuery("SELECT p FROM Publisher p", Publisher.class).getResultList();
    }

    @Override
    public Publisher findById(Long id) {
        return entityManager.find(Publisher.class, id);
    }

    @Override
    public void save(Publisher publisher) {
        entityManager.persist(publisher);
    }

    @Override
    public void update(Publisher publisher) {
        entityManager.merge(publisher);
    }

    @Override
    public void delete(Long id) {
        Publisher publisher = findById(id);
        if (publisher != null) {
            entityManager.remove(publisher);
        }
    }
}
