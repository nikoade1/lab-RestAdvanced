package com.epam.esm.controller;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/giftCertificates")
public class GiftCertificateController {

    private final GiftCertificateService giftCertificateService;

    @Autowired
    public GiftCertificateController(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @GetMapping
    public ResponseEntity<List<GiftCertificate>> findAll() {
        return ResponseEntity.ok(this.giftCertificateService.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody GiftCertificate giftCertificate, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(giftCertificate);
        }
        return ResponseEntity.ok(this.giftCertificateService.add(giftCertificate));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificate> find(@PathVariable("id") long id) {
        return ResponseEntity.ok(this.giftCertificateService.find(id));
    }

    @PatchMapping("/update")
    public ResponseEntity<GiftCertificate> update(@RequestBody GiftCertificate giftCertificate) {
        return ResponseEntity.ok(this.giftCertificateService.update(giftCertificate));
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.giftCertificateService.deleteById(id);
        return new ModelAndView("redirect:/giftCertificates");
    }
}
