package com.epam.esm.controller;

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

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> findAll() {
        List<User> users = this.userService.findAll();
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
        response.add(linkTo(methodOn(UserController.class).findAll()).withRel("link to all Users"));
        return null;
    }

    @PostMapping("/{id}/order")
    public ResponseEntity<?> orderGiftCertificate(@PathVariable Long userId, @RequestBody Long giftCertificateId) {
        this.userService.orderGiftCertificate(userId, giftCertificateId);
        return null;
    }
}
