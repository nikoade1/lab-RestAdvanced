package com.epam.esm.service;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.repository.GiftCertificateDAO;
import com.epam.esm.repository.TagDAO;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@Ignore
class GiftCertificateServiceTest {
    @Mock
    private GiftCertificateDAO giftCertificateDAO;
    @Mock
    private TagDAO tagDAO;

    private GiftCertificateService underTest;

    @BeforeEach
    void setUp() {
        underTest = new GiftCertificateService(giftCertificateDAO, tagDAO);
    }

    @Test
    void findAll() {
        underTest.findAll(1, 5);
        verify(giftCertificateDAO).findAll(1, 5);
    }

    @Test
    void add() {
        GiftCertificate giftCertificate = new GiftCertificate(
                "testName",
                "testDescription",
                10,
                10,
                new HashSet<>());

        underTest.add(giftCertificate);

        ArgumentCaptor<GiftCertificate> giftCertificateArgumentCaptor =
                ArgumentCaptor.forClass(GiftCertificate.class);

        verify(giftCertificateDAO).add(giftCertificateArgumentCaptor.capture());

        GiftCertificate capturedGiftCertificate = giftCertificateArgumentCaptor.getValue();

        assertEquals(giftCertificate, capturedGiftCertificate);
    }

    @Test
    void find() {
    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void findByTags() {
    }
}