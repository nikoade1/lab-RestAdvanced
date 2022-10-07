package com.epam.esm.model;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
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

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH},
            fetch = FetchType.EAGER)
    @JoinTable(
            name = "giftCertificate_tag",
            joinColumns = @JoinColumn(name = "giftCertificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"giftCertificate_id", "tag_id"}))
    private Set<Tag> tags;

    @OneToMany
    @JoinColumn(name = "giftCertificate_id")
    private Set<Order> orders;

    public GiftCertificate() {
        this.tags = new HashSet<>();
    }

    public GiftCertificate(String name, String description, double price, int duration, Set<Tag> tags) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    private GiftCertificate(Long id, String name, String description, double price, int duration, Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.tags = tags;
    }

    private GiftCertificate(Long id, String name, String description, double price, int duration,
                            LocalDateTime create_date, LocalDateTime last_update_date, Set<Tag> tags) {
        this(id, name, description, price, duration, tags);
        this.create_date = create_date;
        this.last_update_date = last_update_date;
    }


    @PrePersist
    public void prePersist() {
        this.create_date = LocalDateTime.now();
        this.last_update_date = LocalDateTime.now();
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

    @PreUpdate
    public void preUpdate() {
        this.last_update_date = LocalDateTime.now();
    }

    private void updateTime() {
        this.last_update_date = LocalDateTime.now();
    }

    public int getDuration() {
        return this.duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
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
        Tag tag = this.tags.stream().filter(t -> Objects.equals(t.getId(), tagId)).findFirst().orElse(null);
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
                this.getCreate_date(),
                this.getLast_update_date(),
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

        return Objects.equals(this.getId(), giftCertificate.getId())
                && Objects.equals(this.getName(), giftCertificate.getName())
                && Objects.equals(this.getDescription(), giftCertificate.getDescription())
                && Objects.equals(this.getPrice(), giftCertificate.getPrice())
                && Objects.equals(this.getDuration(), giftCertificate.getDuration())
                && Objects.equals(this.getCreate_date(), giftCertificate.getCreate_date())
                && Objects.equals(this.getLast_update_date(), giftCertificate.getLast_update_date());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName(), this.getDescription(),
                this.getPrice(), this.getDuration(), this.getCreate_date(), this.getLast_update_date());
    }
}
