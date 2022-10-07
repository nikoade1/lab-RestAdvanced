package com.epam.esm.controller;

import com.epam.esm.model.Order;
import com.epam.esm.model.Tag;
import com.epam.esm.model.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    private final int defaultPageValue = 1;
    private final int defaultSizeValue = 5;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll(@RequestParam(value = "page", required = false, defaultValue = "" + defaultPageValue) int page,
                                              @RequestParam(value = "size", required = false, defaultValue = "" + defaultSizeValue) int size) {
        List<User> users = this.userService.findAll(page, size);
        List<User> response = new ArrayList<>();
        users.forEach(user -> {
            User copy = user.copy();
            copy.add(linkTo(methodOn(UserController.class).find(user.getId())).withSelfRel());
            response.add(copy);
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> find(@PathVariable Long id) {
        User user = this.userService.find(id);
        User response = user.copy();
        response.add(linkTo(methodOn(UserController.class).findAll(defaultPageValue, defaultSizeValue)).withRel("link to all Users"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<?> findOrders(@PathVariable Long id,
                                        @RequestParam(value = "page", required = false, defaultValue = "" + defaultPageValue) int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "" + defaultSizeValue) int size) {

        List<Order> response = this.userService.getOrders(id, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<?> findOrder(@PathVariable Long userId,
                                       @PathVariable Long orderId,
                                       @RequestParam(value = "page", required = false, defaultValue = "" + defaultPageValue) int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "" + defaultSizeValue) int size) {

        Order response = this.userService.getOrder(userId, orderId, page, size);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/{id}/order/{giftCertificateId}")
    public ResponseEntity<?> orderGiftCertificate(@PathVariable Long id, @PathVariable Long giftCertificateId) {
        Order response = this.userService.orderGiftCertificate(id, giftCertificateId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
