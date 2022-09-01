package com.epam.esm.model.order;

import com.epam.esm.model.GiftCertificate;

import java.time.LocalDateTime;

public interface Order {
    void addItem(GiftCertificate giftCertificate);
    double totalCost();
    LocalDateTime orderDate();
}
