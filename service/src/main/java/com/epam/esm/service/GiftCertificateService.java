package com.epam.esm.service;

import com.epam.esm.exceptions.ItemNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.GiftCertificateDAO;
import com.epam.esm.repository.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagDAO tagDAO;

    @Autowired
    public GiftCertificateService(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagDAO = tagDAO;
    }

    public List<GiftCertificate> findAll(int page, int size) {
        return this.giftCertificateDAO.findAll(page, size);
    }

    public GiftCertificate find(Long id) {
        GiftCertificate response = this.giftCertificateDAO.find(id);
        if (response == null) throw new ItemNotFoundException("GiftCertificate with id " + id + " was not found");
        return response;
    }

    public GiftCertificate add(GiftCertificate giftCertificate) {
        this.addIdToTags(giftCertificate);
        return this.giftCertificateDAO.add(giftCertificate);
    }

    public void delete(GiftCertificate giftCertificate) {
        this.giftCertificateDAO.delete(giftCertificate);
    }

    public GiftCertificate update(GiftCertificate giftCertificate) {
        if (giftCertificate.getId() == null) throw new ItemNotFoundException("Id is not provided");
        this.addIdToTags(giftCertificate);
        return this.giftCertificateDAO.update(giftCertificate);
    }

    public void deleteById(Long id) {
        GiftCertificate giftCertificate = find(id);
        delete(giftCertificate);
    }

    public List<GiftCertificate> findByTags(String[] tagNames, int page, int size) {
        List<Tag> tags = new ArrayList<>();
        for (String name : tagNames) {
            this.tagDAO.findByName(name).stream().findAny().ifPresent(tags::add);
        }
        return this.giftCertificateDAO.findByTags(tags, page, size);
    }

    private void addIdToTags(GiftCertificate giftCertificate) {
        giftCertificate.getTags()
                .forEach(t -> {
                    Tag tag = this.tagDAO.findByName(t.getName()).stream().findAny().orElse(null);
                    if (tag == null) return;
                    t.setId(tag.getId());
                });

    }
}
