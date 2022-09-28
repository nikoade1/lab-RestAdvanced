package com.epam.esm.service;

import com.epam.esm.exceptions.ItemNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateDAO;
import com.epam.esm.repository.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateService(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    public List<GiftCertificate> findAll() {
        return this.giftCertificateDAO.findAll();
    }

    public GiftCertificate find(Long id) throws ItemNotFoundException {
        GiftCertificate response = this.giftCertificateDAO.find(id);
        if (response == null) throw new ItemNotFoundException("GiftCertificate with id " + id + " was not found");
        return response;
    }

    public GiftCertificate add(GiftCertificate giftCertificate) {
        Set<Tag> tags = giftCertificate.getTags();
        Set<Tag> newTags = new HashSet<>();
        Set<Tag> oldTags = new HashSet<>();

        tags.forEach(tag -> {
            List<Tag> inDB = this.tagDAO.findByName(tag.getName());
            if (inDB.isEmpty()) {
                newTags.add(tag);
            } else {
                oldTags.add(tag);
            }
        });
        giftCertificate.setTags(newTags);
        giftCertificate = this.giftCertificateDAO.add(giftCertificate);
        GiftCertificate persisted = this.giftCertificateDAO.find(giftCertificate.getId());
        oldTags.forEach(persisted::addTag);
        return persisted;
    }

    public void delete(GiftCertificate giftCertificate) {
        this.giftCertificateDAO.delete(giftCertificate);
    }

    public GiftCertificate update(GiftCertificate giftCertificate) throws ItemNotFoundException {
        if (giftCertificate.getId() == null) throw new ItemNotFoundException("Id is not provided");
        return this.giftCertificateDAO.update(giftCertificate);
    }

    public void deleteById(Long id) throws ItemNotFoundException {
        GiftCertificate giftCertificate = find(id);
        delete(giftCertificate);
    }


    public GiftCertificate addTag(Long giftCertificateId, Tag tag) throws ItemNotFoundException {
        GiftCertificate giftCertificate = find(giftCertificateId);
        List<Tag> inDB = this.tagDAO.findByName(tag.getName());
        if (inDB.isEmpty()) {
            this.tagDAO.add(tag);
            giftCertificate.addTag(tag);
        } else {
            giftCertificate.addTag(inDB.get(0));
        }
        return giftCertificate;
    }

    public GiftCertificate removeTag(Long giftCertificateId, Tag tag) throws ItemNotFoundException {
        GiftCertificate giftCertificate = find(giftCertificateId);
        giftCertificate.removeTag(tag.getName());
        return giftCertificate;
    }

}
