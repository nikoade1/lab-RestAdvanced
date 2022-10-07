package com.epam.esm.service;

import com.epam.esm.model.User;
import com.epam.esm.repository.OrderDAO;
import com.epam.esm.repository.UserDAO;
import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@Ignore
class UserServiceTest {
    @Mock
    private UserDAO userDAO;
    @Mock
    private OrderDAO orderDAO;
    @Mock
    private GiftCertificateService giftCertificateService;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        underTest = new UserService(userDAO, orderDAO, giftCertificateService);
    }

    @Test
    void findAll() {
        underTest.findAll(1, 5);
        verify(userDAO).findAll(1, 5);
    }

    @Test
    void add() {
        User user = new User("testName", "testLastName", 1000);
        underTest.add(user);

        ArgumentCaptor<User> userArgumentCaptor =
                ArgumentCaptor.forClass(User.class);

        verify(userDAO).add(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();

        assertEquals(user, capturedUser);
    }

    @Test
    void find() {
    }

    @Test
    void getOrders() {
    }

    @Test
    void orderGiftCertificate() {
    }

    @Test
    void getOrder() {
    }
}