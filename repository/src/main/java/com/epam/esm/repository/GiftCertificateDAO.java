package com.epam.esm.repository;

import com.epam.esm.model.GiftCertificate;

import java.util.List;

public interface GiftCertificateDAO {

    List<GiftCertificate> findAll();

    GiftCertificate add(GiftCertificate giftCertificate);

    GiftCertificate find(Long id);

    void delete(GiftCertificate giftCertificate);

    GiftCertificate update(GiftCertificate giftCertificate);

    void merge(GiftCertificate giftCertificate);
}
