package com.epam.esm.repository.impl;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
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
        this.entityManager.merge(toUpdate);
        this.entityManager.getTransaction().commit();
        return find(toUpdate.getId());
    }

    @Override
    public List<GiftCertificate> findByTags(List<Tag> tags, int page, int size) {
        List<GiftCertificate> resultList = new ArrayList<>();
        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GiftCertificate.class);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GiftCertificate.class);
        Join<GiftCertificate, Tag> join = giftCertificateRoot.join("tags", JoinType.LEFT);

        for (Tag tag : tags) {
            criteriaQuery
                    .select(giftCertificateRoot)
                    .where(criteriaBuilder.equal(join.get("name"), tag.getName()));
            if (resultList.isEmpty()) {
                resultList.addAll(this.entityManager.createQuery(criteriaQuery).getResultList());
            }
            resultList.retainAll(this.entityManager.createQuery(criteriaQuery).getResultList());
        }

        int from = (page - 1) * size;
        int to = Math.min(resultList.size(), from + size);
        return resultList.subList(from, to);
    }

    @Override
    public void delete(GiftCertificate giftCertificate) {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(giftCertificate);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
