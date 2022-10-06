package com.epam.esm.model;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Objects;

@Table(name = "orders")
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "purchase_date")
    private LocalDateTime purchaseDate;

    @Min(1)
    @Max(10000)
    private double cost;

    @ManyToOne
    @JoinColumn(name = "giftCertificate_id")
    private GiftCertificate giftCertificate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Order() {
    }

    public Order(User user, GiftCertificate giftCertificate) {
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.cost = giftCertificate.getPrice();
    }

    @PrePersist
    public void prePersist() {
        this.purchaseDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        Order order = (Order) o;
        return Objects.equals(this.getId(), order.getId())
                && Objects.equals(this.getGiftCertificate(), order.getGiftCertificate())
                && Objects.equals(this.getCost(), order.getCost())
                && Objects.equals(this.getPurchaseDate(), order.getPurchaseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(),
                this.getGiftCertificate(),
                this.getCost(),
                this.getPurchaseDate());
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", purchaseDate=" + purchaseDate +
                ", cost=" + cost +
                ", giftCertificate=" + giftCertificate +
                ", user=" + user +
                '}';
    }
}
