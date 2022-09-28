package com.epam.esm.repository;

import com.epam.esm.model.User;

import java.util.List;

public interface UserDAO {

    List<User> findAll();

    User add(User user);

    User find(Long id);

    void delete(User user);

    User update(User User);

}
