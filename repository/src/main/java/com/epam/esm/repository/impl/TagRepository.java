package com.epam.esm.repository.impl;

import com.epam.esm.repository.TagDAO;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TagRepository implements TagDAO {

    private final EntityManager entityManager;
    private final EntityManagerFactory emf;

    public TagRepository() {
        this.emf = Persistence.createEntityManagerFactory("pu");
        this.entityManager = emf.createEntityManager();
    }

    @Override
    public List<Tag> findAll() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Select t from Tag t");
        entityManager.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public Tag add(Tag tag) {
        entityManager.getTransaction().begin();
        entityManager.persist(tag);
        entityManager.getTransaction().commit();
        return tag;
    }

    @Override
    public Tag find(Long id) {
        return entityManager.find(Tag.class, id);
    }

    @Override
    public List<Tag> findByName(String name) {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Select t from Tag t where t.name = '" + name + "'");
        entityManager.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public void delete(Tag tag) {
        entityManager.getTransaction().begin();
        entityManager.remove(tag);
        entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
