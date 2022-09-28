package com.epam.esm.repository.impl;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagDAO;
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
        this.entityManager = this.emf.createEntityManager();
    }

    @Override
    public List<Tag> findAll() {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createQuery("Select t from Tag t");
        this.entityManager.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public Tag add(Tag tag) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(tag);
        this.entityManager.getTransaction().commit();
        return tag;
    }

    @Override
    public Tag find(Long id) {
        return this.entityManager.find(Tag.class, id);
    }

    @Override
    public List<Tag> findByName(String name) {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createQuery("Select t from Tag t where t.name = '" + name + "'");
        this.entityManager.getTransaction().commit();
        return query.getResultList();
    }


    @Override
    public void delete(Tag tag) {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(tag);
        tag.getGiftCertificates().forEach(gc -> {
            gc.removeTag(tag.getName());
            this.entityManager.merge(gc);
        });
        this.entityManager.flush();
        this.entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
