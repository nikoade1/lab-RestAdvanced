package com.epam.esm.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gift_certificates")
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 100)
    private String description;

    private double price;
    private int duration;
    private LocalDateTime create_date;
    private LocalDateTime last_update_date;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
    @JoinTable(
            name = "giftCertificate_tag",
            joinColumns = @JoinColumn(name = "giftCertificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;

    public GiftCertificate() {
        this.create_date = LocalDateTime.now();
        this.last_update_date = this.create_date;
        this.tags = new HashSet<>();
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

    public void addTag(Tag tag) {
        boolean added = tags.add(tag);
        if (added) {
            tag.getGiftCertificates().add(this);
        }
    }

    public void addAllTags(Collection<Tag> newTags) {
        boolean added = tags.addAll(newTags);
        if (added) {
            newTags.forEach(t -> t.getGiftCertificates().add(this));
        }
        System.out.println("new tags added in giftCertificate object " + newTags);
    }

    public void removeTag(Tag tag) {
        boolean removed = tags.remove(tag);
        if (removed) {
            tag.getGiftCertificates().remove(this);
        }
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", duration=" + duration +
                ", create_date=" + create_date +
                ", last_update_date=" + last_update_date +
                ", tags = " + tags;
    }
}
