package com.epam.esm.service;

import com.epam.esm.exceptions.ItemNotFoundException;
import com.epam.esm.exceptions.MoneyNotEnoughException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Order;
import com.epam.esm.model.User;
import com.epam.esm.repository.OrderDAO;
import com.epam.esm.repository.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserDAO userDAO;
    private final OrderDAO orderDAO;
    private final GiftCertificateService giftCertificateService;

    @Autowired
    public UserService(UserDAO userDAO, OrderDAO orderDAO, GiftCertificateService giftCertificateService) {
        this.userDAO = userDAO;
        this.orderDAO = orderDAO;
        this.giftCertificateService = giftCertificateService;
    }

    public List<User> findAll(int page, int size) {
        return this.userDAO.findAll(page, size);
    }

    public User add(User user) {
        return this.userDAO.add(user);
    }

    public User find(Long id) {
        User response = this.userDAO.find(id);
        if (response == null) throw new ItemNotFoundException("User with id " + id + " was not found");
        return response;
    }

    public List<Order> getOrders(Long id, int page, int size) {
        return this.orderDAO.findByUser(id, page, size);
    }

    public Order orderGiftCertificate(Long userId, Long giftCertificateId) {
        User user = find(userId);
        GiftCertificate giftCertificate = this.giftCertificateService.find(giftCertificateId);
        if (user.getMoney() <= giftCertificate.getPrice()) {
            throw new MoneyNotEnoughException("user with id + " + userId + " has not enough money");
        }
        user.setMoney(user.getMoney() - giftCertificate.getPrice());
        user = this.userDAO.update(user);
        Order order = new Order(user, giftCertificate);
        return this.orderDAO.add(order);
    }

    public Order getOrder(Long userId, Long orderId, int page, int size) {
        User user = find(userId);
        Order order = user.getOrders().stream()
                .filter(o -> Objects.equals(o.getId(), orderId))
                .findAny().orElse(null);

        if (order == null)
            throw new ItemNotFoundException("User with id " + userId + " has no order with id " + orderId);
        return order;
    }
}
