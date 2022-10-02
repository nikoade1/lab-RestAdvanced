package com.epam.esm.service;

import com.epam.esm.exceptions.ItemNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateDAO;
import com.epam.esm.repository.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftCertificateService {

    private final GiftCertificateDAO giftCertificateDAO;

    @Autowired
    public GiftCertificateService(GiftCertificateDAO giftCertificateDAO, TagDAO tagDAO) {
        this.giftCertificateDAO = giftCertificateDAO;
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
        return this.giftCertificateDAO.add(giftCertificate);
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

}
