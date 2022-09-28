package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateDAO;
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


    public GiftCertificateRepository() {
        this.emf = Persistence.createEntityManagerFactory("pu");
        this.entityManager = this.emf.createEntityManager();
    }

    @Override
    public List<GiftCertificate> findAll() {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createQuery("Select gc from GiftCertificate gc");
        this.entityManager.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(giftCertificate);
        this.entityManager.getTransaction().commit();
        return giftCertificate;
    }

    @Override
    public GiftCertificate find(Long id) {
        return this.entityManager.find(GiftCertificate.class, id);
    }


    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        GiftCertificate toUpdate = find(giftCertificate.getId());
        this.entityManager.getTransaction().begin();
        toUpdate.setName(giftCertificate.getName());
        toUpdate.setDescription(giftCertificate.getDescription());
        toUpdate.setDuration(giftCertificate.getDuration());
        toUpdate.setPrice(giftCertificate.getPrice());
        this.entityManager.getTransaction().commit();
        return toUpdate;
    }

    @Override
    public void merge(GiftCertificate giftCertificate) {
        this.entityManager.merge(giftCertificate);
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
