package com.epam.esm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<GiftCertificate> getGiftCertificates() {
        return giftCertificates;
    }

    public void setGiftCertificates(Set<GiftCertificate> giftCertificates) {
        this.giftCertificates = giftCertificates;
    }

    public Tag copy() {
        return new Tag(this.getId(), this.getName());
    }

    @Override
    public String toString() {
        return "Tag = " + this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (!(obj instanceof Tag)) return false;
        Tag tag = (Tag) obj;
        return this.getName().equals(tag.getName());
    }

    @Override
    public int hashCode() {
        return this.getName().hashCode();
    }
}
