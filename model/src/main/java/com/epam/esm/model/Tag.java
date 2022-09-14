package com.epam.esm.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 30)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<GiftCertificate> giftCertificates;

    public Tag() {
        giftCertificates = new HashSet<>();
    }

    public Tag(String name) {
        this();
        this.name = name;
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

    @Override
    public String toString() {
        return "Tag = " + this.name;
    }


    public void addGiftCertificate(GiftCertificate giftCertificate) {
        boolean added = giftCertificates.add(giftCertificate);
        if (added) {
            giftCertificate.addTag(this);
        }
    }

    public void removeGiftCertificate(GiftCertificate giftCertificate) {
        boolean removed = getGiftCertificates().remove(giftCertificates);
        if (removed) {
            giftCertificate.getTags().remove(this);
        }
    }

    public Set<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(Set<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }
}
