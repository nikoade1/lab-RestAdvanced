package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "gift_certificates")
public class GiftCertificate extends RepresentationModel<GiftCertificate> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 30)
    @Column(nullable = false, length = 30)
    private String name;

    @Column(length = 100)
    private String description;

    @Min(1)
    @Max(10000)
    private double price;

    @Min(4)
    @Max(100)
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
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"giftCertificate_id", "tag_id"}))
    private Set<Tag> tags;

    public GiftCertificate() {
        this.create_date = LocalDateTime.now();
        this.last_update_date = this.create_date;
        this.tags = new HashSet<>();
    }

    public GiftCertificate(Long id, String name, String description, double price, int duration, Set<Tag> tags) {
        this();
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
        updateTime();
    }

    private void updateTime() {
        this.last_update_date = LocalDateTime.now();
    }

    public int getDuration() {
        return this.duration;
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

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public void addTag(Tag tag) {
        this.tags.add(tag);
        tag.getGiftCertificates().add(this);
    }

    public void removeTag(Long tagId) {
        Tag tag = this.tags.stream().filter(t -> t.getId() == tagId).findFirst().orElse(null);
        if (tag != null) {
            this.tags.remove(tag);
            tag.getGiftCertificates().remove(this);
        }
    }

    public void removeTag(String name) {
        Tag tag = this.tags.stream().filter(t -> t.getName().equals(name)).findFirst().orElse(null);
        if (tag != null) {
            this.tags.remove(tag);
            tag.getGiftCertificates().remove(this);
        }
    }

    public GiftCertificate copy() {
        return new GiftCertificate(
                this.getId(),
                this.getName(),
                this.getDescription(),
                this.getPrice(),
                this.getDuration(),
                this.getTags());
    }


    @Override
    public String toString() {
        return "GiftCertificate{" +
                "id=" + this.id +
                ", name='" + this.name + '\'' +
                ", description='" + this.description + '\'' +
                ", price=" + this.price +
                ", duration=" + this.duration +
                ", create_date=" + this.create_date +
                ", last_update_date=" + this.last_update_date +
                ", tags = " + this.tags;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof GiftCertificate)) return false;
        GiftCertificate giftCertificate = (GiftCertificate) obj;

        return this.getId() == giftCertificate.getId()
                && this.getName().equals(giftCertificate.getName())
                && this.getDescription().equals(giftCertificate.getDescription())
                && this.getPrice() == giftCertificate.getPrice()
                && this.getDuration() == giftCertificate.getDuration()
                && this.getCreate_date().equals(giftCertificate.getCreate_date())
                && this.getLast_update_date().equals(giftCertificate.getLast_update_date());
    }

    @Override
    public int hashCode() {
        String code = this.getId().toString()
                + this.getName()
                + this.getDescription()
                + this.getPrice()
                + this.getDuration()
                + this.getCreate_date()
                + this.getLast_update_date();
        return code.hashCode();
    }
}
