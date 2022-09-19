package com.epam.esm.repository.impl;

import com.epam.esm.repository.UserDAO;
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
