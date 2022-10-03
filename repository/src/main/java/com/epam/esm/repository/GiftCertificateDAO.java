package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;

import java.util.List;

public interface GiftCertificateDAO {

    List<GiftCertificate> findAll(int page, int size);

    GiftCertificate add(GiftCertificate giftCertificate);

    GiftCertificate find(Long id);

    void delete(GiftCertificate giftCertificate);

    GiftCertificate update(GiftCertificate giftCertificate);

    List<GiftCertificate> findByTags(List<Tag> tags, int page, int size);
}
