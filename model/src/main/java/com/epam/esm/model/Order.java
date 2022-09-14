package com.epam.esm.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double price;

    @ManyToMany(mappedBy = "orders")
    private Set<User> users;

    public Order() {
        this.users = new HashSet<>();
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void addUser(User user) {
        boolean added = this.users.add(user);
        if (added) {
            user.getOrders().add(this);
        }
    }

    public void addAllUsers(Collection<User> newUsers) {
        boolean added = users.addAll(newUsers);
        if (added) {
            newUsers.forEach(u -> u.getOrders().add(this));
        }
    }

    public void removeUser(User user) {
        boolean removed = this.users.remove(user);
        if (removed) {
            user.getOrders().remove(this);
        }
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
