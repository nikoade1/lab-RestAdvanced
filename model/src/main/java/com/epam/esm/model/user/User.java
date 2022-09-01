package com.epam.esm.model.user;

import com.epam.esm.model.order.Order;

import java.util.List;

public interface User {
    boolean order(Order order);

    List<Order> getOrders();

    Order getOrder(int orderId);
}
