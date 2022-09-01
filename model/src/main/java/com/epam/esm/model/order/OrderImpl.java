package com.epam.esm.model.order;

import com.epam.esm.model.GiftCertificate;

import java.time.LocalDateTime;
import java.util.List;

public class OrderImpl implements Order{
    private int id;
    private double totalCost;
    private final LocalDateTime orderDate;
    private List<GiftCertificate> items;

    public OrderImpl() {
        this.orderDate = LocalDateTime.now();
    }

    public OrderImpl(List<GiftCertificate> items) {
        this();
        this.items = items;
    }

    public int getId() {
        return this.id;
    }

    public List<GiftCertificate> getItems() {
        return this.items;
    }

    @Override
    public void addItem(GiftCertificate giftCertificate) {
        this.items.add(giftCertificate);
        this.totalCost += giftCertificate.getPrice();
    }

    @Override
    public double totalCost() {
        return this.totalCost;
    }

    @Override
    public LocalDateTime orderDate() {
        return this.orderDate;
    }
}
