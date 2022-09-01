package com.epam.esm.model.user;

import com.epam.esm.model.order.Order;

import java.util.ArrayList;
import java.util.List;

public class UserImpl implements User {

    private int id;
    private String name;
    private double money;
    private List<Order> orders;

    public UserImpl() {
        orders = new ArrayList<>();
    }

    public UserImpl(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    @Override
    public boolean order(Order order) {
        if (money < order.totalCost()) return false;
        money -= order.totalCost();
        orders.add(order);
        return true;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public Order getOrder(int orderId) {
        return null;
    }
}
