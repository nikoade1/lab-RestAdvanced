package com.epam.esm.repository.impl;

import com.epam.esm.repository.GiftCertificateDAO;
import com.epam.esm.model.GiftCertificate;
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
        emf = Persistence.createEntityManagerFactory("pu");
        this.entityManager = emf.createEntityManager();
    }

    @Override
    public List<GiftCertificate> findAll() {
        entityManager.getTransaction().begin();
        Query query = entityManager.createQuery("Select gc from GiftCertificate gc");
        entityManager.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public GiftCertificate add(GiftCertificate giftCertificate) {
        entityManager.getTransaction().begin();
        entityManager.persist(giftCertificate);
        entityManager.getTransaction().commit();
        return giftCertificate;
    }

    @Override
    public GiftCertificate find(Long id) {
        return entityManager.find(GiftCertificate.class, id);
    }


    @Override
    public GiftCertificate update(GiftCertificate giftCertificate) {
        GiftCertificate toUpdate = find(giftCertificate.getId());
        entityManager.getTransaction().begin();

        toUpdate.setName(giftCertificate.getName());
        toUpdate.setDescription(giftCertificate.getDescription());
        toUpdate.setDuration(giftCertificate.getDuration());
        toUpdate.setPrice(giftCertificate.getPrice());
        entityManager.getTransaction().commit();
        return toUpdate;
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        entityManager.getTransaction().begin();
        entityManager.remove(giftCertificate);
        entityManager.getTransaction().commit();
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
