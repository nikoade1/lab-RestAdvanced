package com.epam.esm.repository.impl;

import com.epam.esm.model.User;
import com.epam.esm.repository.UserDAO;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserRepository implements UserDAO {

    private final EntityManager entityManager;
    private final EntityManagerFactory emf;

    public UserRepository() {
        this.emf = Persistence.createEntityManagerFactory("pu");
        this.entityManager = this.emf.createEntityManager();
    }

    public List<User> findAll() {
        this.entityManager.getTransaction().begin();
        Query query = this.entityManager.createQuery("Select u from User u");
        this.entityManager.getTransaction().commit();
        return query.getResultList();
    }

    @Override
    public User add(User user) {
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(user);
        this.entityManager.getTransaction().commit();
        return user;
    }

    @Override
    public User find(Long id) {
        return this.entityManager.find(User.class, id);
    }

    @Override
    public void delete(User user) {
        this.entityManager.getTransaction().begin();
        this.entityManager.remove(user);
        this.entityManager.getTransaction().commit();
    }

    @Override
    public User update(User user) {
        User toUpdate = find(user.getId());
        this.entityManager.getTransaction().begin();

        toUpdate.setFirstName(user.getFirstName());
        toUpdate.setLastName(user.getLastName());
        toUpdate.setMoney(user.getMoney());

        this.entityManager.getTransaction().commit();
        return toUpdate;
    }

    public void close() {
        this.entityManager.close();
        this.emf.close();
    }

}
