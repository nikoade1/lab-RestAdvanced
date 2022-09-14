package com.epam.esm.service;

import com.epam.esm.GiftCertificateDAO;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import com.epam.esm.model.WrapperGiftTags;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;
    private final TagService tagService;

    @Autowired
    public GiftCertificateService(GiftCertificateDAO giftCertificateDAO, TagService tagService) {
        this.giftCertificateDAO = giftCertificateDAO;
        this.tagService = tagService;
    }

    public List<GiftCertificate> findAll() {
        return this.giftCertificateDAO.findAll();
    }

    public GiftCertificate find(Long id) {
        return this.giftCertificateDAO.find(id);
    }

    public GiftCertificate add(GiftCertificate giftCertificate) {
        return this.giftCertificateDAO.add(giftCertificate);
    }

    public void delete(GiftCertificate giftCertificate) {
        this.giftCertificateDAO.delete(giftCertificate);
    }

    public GiftCertificate update(GiftCertificate giftCertificate) {
        return this.giftCertificateDAO.update(giftCertificate);
    }

    public void deleteById(Long id) {
        GiftCertificate giftCertificate = find(id);
        delete(giftCertificate);
    }

    public GiftCertificate addWithTags(WrapperGiftTags wrapperGiftTags) {
        GiftCertificate giftCertificate = wrapperGiftTags.getGiftCertificate();
        List<Tag> tags = wrapperGiftTags.getTags();
        List<Tag> newTags = tags.stream()
                .filter(tag -> tagService.findByName(tag.getName()).isEmpty())
                .collect(Collectors.toList());

        newTags.forEach(tagService::add);
        giftCertificate.addAllTags(tags);

        return this.giftCertificateDAO.add(giftCertificate);
    }
}
