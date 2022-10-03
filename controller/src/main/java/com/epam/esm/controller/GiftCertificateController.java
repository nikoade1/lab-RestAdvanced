package com.epam.esm.controller;

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

    private final int defaultPageValue = 1;
    private final int defaultSizeValue = 5;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(value = "page", required = false, defaultValue = "" + defaultPageValue) int page,
                                     @RequestParam(value = "size", required = false, defaultValue = "" + defaultSizeValue) int size) {
        List<GiftCertificate> giftCertificates = this.giftCertificateService.findAll(page, size);
        List<GiftCertificate> response = new ArrayList<>();
        giftCertificates.forEach(gc -> {
            GiftCertificate copy = gc.copy();
            copy.add(linkTo(methodOn(GiftCertificateController.class).find(gc.getId())).withSelfRel());
            response.add(copy);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate response = this.giftCertificateService.add(giftCertificate)
                .add(linkTo(methodOn(GiftCertificateController.class).findAll(defaultPageValue, defaultSizeValue))
                        .withRel("link to all GiftCertificates"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") long id) {
        GiftCertificate giftCertificate = this.giftCertificateService.find(id);
        GiftCertificate response = giftCertificate.copy();

        response.add(linkTo(methodOn(GiftCertificateController.class).findAll(defaultPageValue, defaultSizeValue))
                .withRel("link to all GiftCertificates"));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<?> update(@Valid @RequestBody GiftCertificate giftCertificate) {
        GiftCertificate updated = this.giftCertificateService.update(giftCertificate);
        GiftCertificate response = updated.copy();

        response.add(linkTo(methodOn(GiftCertificateController.class).findAll(defaultPageValue, defaultSizeValue))
                .withRel("link to all GiftCertificates"));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.giftCertificateService.deleteById(id);
        return new ModelAndView("redirect:/giftCertificates");
    }

    @GetMapping("/tags")
    public ResponseEntity<?> findByTags(@RequestParam String[] tagNames,
                                        @RequestParam(value = "page", required = false, defaultValue = "" + defaultPageValue) int page,
                                        @RequestParam(value = "size", required = false, defaultValue = "" + defaultSizeValue) int size) {

        List<GiftCertificate> giftCertificates = this.giftCertificateService.findByTags(tagNames, page, size);
        List<GiftCertificate> response = new ArrayList<>();
        giftCertificates.forEach(gc -> {
            GiftCertificate copy = gc.copy();
            copy.add(linkTo(methodOn(GiftCertificateController.class).find(gc.getId())).withSelfRel());

            response.add(copy);
        });

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
