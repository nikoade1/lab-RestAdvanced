package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateDAO;
import com.epam.esm.repository.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Repository
public class GiftCertificateRepository implements GiftCertificateDAO {

    private final EntityManager entityManager;
    private final EntityManagerFactory emf;
    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateRepository(TagDAO tagDAO) {
        this.tagDAO = tagDAO;
        this.emf = Persistence.createEntityManagerFactory("pu");
        this.entityManager = this.emf.createEntityManager();
    }

    @Override
    public List<GiftCertificate> findAll(int page, int size) {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createQuery("Select gc from GiftCertificate gc");
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        this.entityManager.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        this.entityManager.getTransaction().begin();
        giftCertificate.getTags()
                .forEach(t -> {
                    List<Tag> ls = this.tagDAO.findByName(t.getName());
                    if (ls.isEmpty()) return;
                    t.setId(ls.get(0).getId());
                });
        GiftCertificate merged = this.entityManager.merge(giftCertificate);
        this.entityManager.getTransaction().commit();
        return merged;
    }

    @Override
    public GiftCertificate find(Long id) {
        return this.entityManager.find(GiftCertificate.class, id);
    }

    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        GiftCertificate toUpdate = find(giftCertificate.getId());
        this.entityManager.getTransaction().begin();
        this.entityManager.detach(toUpdate);
        toUpdate.setName(giftCertificate.getName());
        toUpdate.setDescription(giftCertificate.getDescription());
        toUpdate.setDuration(giftCertificate.getDuration());
        toUpdate.setPrice(giftCertificate.getPrice());
        toUpdate.setTags(giftCertificate.getTags());
        toUpdate.getTags()
                .forEach(t -> {
                    List<Tag> ls = this.tagDAO.findByName(t.getName());
                    if (ls.isEmpty()) return;
                    t.setId(ls.get(0).getId());
                });
        this.entityManager.merge(toUpdate);
        this.entityManager.getTransaction().commit();
        return find(toUpdate.getId());
    }

    @Override
    public void merge(GiftCertificate giftCertificate) {
        this.entityManager.getTransaction().begin();
        this.entityManager.merge(giftCertificate);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(giftCertificate);
        this.entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
