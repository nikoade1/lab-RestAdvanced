package com.epam.esm.impl;

import com.epam.esm.OrderDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Repository
public class OrderRepository implements OrderDAO {

    private final EntityManager entityManager;
    private final EntityManagerFactory emf;

    public OrderRepository() {
        this.emf = Persistence.createEntityManagerFactory("pu");
        this.entityManager = emf.createEntityManager();
    }
}
