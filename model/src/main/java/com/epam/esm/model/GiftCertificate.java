package com.epam.esm.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "gift_certificates")
public class GiftCertificate {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 100)
    private String description;

    private double price;
    private int duration;
    private LocalDateTime create_date;
    private LocalDateTime last_update_date;

    public GiftCertificate() {
        this.create_date = LocalDateTime.now();
        this.last_update_date = this.create_date;
    }

    public GiftCertificate(String name, String description, double price, int duration) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        updateTime();
    }

    private void updateTime() {
        this.last_update_date = LocalDateTime.now();
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
        updateTime();
    }

    public LocalDateTime getCreate_date() {
        return create_date;
    }
    public LocalDateTime getLast_update_date() {
        return last_update_date;
    }

}
