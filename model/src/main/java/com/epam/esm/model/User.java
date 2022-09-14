package com.epam.esm.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private double money;

    @ManyToMany
    @JoinTable(
            name = "user_order",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> orders;

    public User() {
        this.orders = new HashSet<>();
    }

    public User(String name, double money) {
        this();
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

    public void addOrder(Order order) {
        boolean added = orders.add(order);
        if (added) {
            order.getUsers().add(this);
        }
    }

    public void addAllOrders(Collection<Order> newOrders) {
        boolean added = orders.addAll(newOrders);
        if (added) {
            newOrders.forEach(gc -> gc.getUsers().add(this));
        }
    }

    public void removeOrder(Order order) {
        boolean removed = orders.remove(order);
        if (removed) {
            order.getUsers().remove(this);
        }
    }

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

}
