package com.epam.esm.controller;

import com.epam.esm.exceptions.ItemNotFoundException;
import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        List<GiftCertificate> giftCertificates = this.giftCertificateService.findAll();
        List<GiftCertificate> response = new ArrayList<>();
        giftCertificates.forEach(gc -> {
            GiftCertificate copy = gc.copy();
            try {
                copy.add(linkTo(methodOn(GiftCertificateController.class).find(gc.getId())).withSelfRel());
            } catch (ItemNotFoundException e) {
                throw new RuntimeException(e);
            }
            response.add(copy);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate response = this.giftCertificateService.add(giftCertificate)
                .add(linkTo(methodOn(GiftCertificateController.class).findAll())
                        .withRel("link to all GiftCertificates"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") long id) throws ItemNotFoundException {
        GiftCertificate giftCertificate = this.giftCertificateService.find(id);
        GiftCertificate response = giftCertificate.copy();

        response.add(linkTo(methodOn(GiftCertificateController.class).findAll())
                .withRel("link to all GiftCertificates"));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate updated = this.giftCertificateService.update(giftCertificate);
        GiftCertificate response = updated.copy();

        response.add(linkTo(methodOn(GiftCertificateController.class).findAll())
                .withRel("link to all GiftCertificates"));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) throws ItemNotFoundException {
        this.giftCertificateService.deleteById(id);
        return new ModelAndView("redirect:/giftCertificates");
    }
}
