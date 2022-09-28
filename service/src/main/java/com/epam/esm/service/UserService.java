package com.epam.esm.service;

import com.epam.esm.model.User;
import com.epam.esm.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public List<User> findAll() {
        return null;
    }

    public User add(User user) {
        return null;
    }

    public User find(Long id) {
        return null;
    }

    public void orderGiftCertificate(Long userId, Long giftCertificateId) {
    }
}
