package com.epam.esm.repository;

import com.epam.esm.model.Order;

import java.util.List;

public interface OrderDAO {

    List<Order> findAll(int page, int size);

    List<Order> findByUser(Long userId, int page, int size);

    Order find(Long id);

    Order add(Order order);

    void close();
}
