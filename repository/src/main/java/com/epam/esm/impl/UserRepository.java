package com.epam.esm.impl;

import com.epam.esm.UserDAO;
import org.apache.catalina.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

@Repository
public class UserRepository implements UserDAO {

    private final EntityManager entityManager;
    private final EntityManagerFactory emf;

    public UserRepository() {
        this.emf = Persistence.createEntityManagerFactory("pu");
        this.entityManager = emf.createEntityManager();
    }
}