package com.epam.esm.repository.impl;

import com.epam.esm.model.Order;
import com.epam.esm.repository.OrderDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class OrderRepository implements OrderDAO {

    private final EntityManager entityManager;
    private final EntityManagerFactory emf;

    public OrderRepository() {
        this.emf = Persistence.createEntityManagerFactory("pu");
        this.entityManager = this.emf.createEntityManager();
    }

    @Override
    public List<Order> findAll(int page, int size) {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createQuery("Select o from Order o");
        query.setFirstResult((page - 1) * size);
        query.setMaxResults(size);
        this.entityManager.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public List<Order> findByUser(Long userId, int page, int size) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> OrderRoot = criteriaQuery.from(Order.class);
        criteriaQuery.select(OrderRoot);
        criteriaQuery.where(criteriaBuilder.equal(OrderRoot.get("user").get("id"), userId));
        TypedQuery<Order> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * size);
        typedQuery.setMaxResults(size);
        return typedQuery.getResultList();
    }

    @Override
    public Order find(Long id) {
        return this.entityManager.find(Order.class, id);
    }

    @Override
    public Order add(Order order) {
        this.entityManager.getTransaction().begin();
        Order merged = this.entityManager.merge(order);
        this.entityManager.getTransaction().commit();
        return merged;
    }

    @Override
    public void close() {
        this.entityManager.close();
        this.emf.close();
    }
}
