package com.epam.esm;

import com.epam.esm.model.Tag;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TagRepository {

    private EntityManager entityManager;
    private EntityManagerFactory emf;

    public TagRepository() {
        emf = Persistence.createEntityManagerFactory("tag_pu");
        this.entityManager = emf.createEntityManager();
    }

    public Tag add(Tag tag) {
        entityManager.getTransaction().begin();
        entityManager.persist(tag);
        entityManager.getTransaction().commit();
        return tag;
    }

    public Tag find(Long id) {
        return entityManager.find(Tag.class, id);
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }

    public void delete(Tag tag) {
        entityManager.getTransaction().begin();
        entityManager.remove(tag);
        entityManager.getTransaction().commit();
    }

    public List<Tag> findAll() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Select t from Tag t");
        entityManager.getTransaction().commit();
        return query.getResultList();
    }
}
