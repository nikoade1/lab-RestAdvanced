package com.epam.esm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
public class Tag extends RepresentationModel<Tag> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, nullable = false, length = 30)
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<GiftCertificate> giftCertificates;

    public Tag() {
        giftCertificates = new HashSet<>();
    }

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
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
            giftCertificate.getTags().add(this);
        }
    }

    public void addAllGiftCertificates(Collection<GiftCertificate> newGiftCertificates) {
        boolean added = giftCertificates.addAll(newGiftCertificates);
        if (added) {
            newGiftCertificates.forEach(gc -> gc.getTags().add(this));
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
