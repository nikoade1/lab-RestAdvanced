package com.epam.esm.controller;

import com.epam.esm.exceptions.ItemNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
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
@RequestMapping("tags")
public class TagController {

    private final TagService tagService;

    private final int defaultPageValue = 1;
    private final int defaultSizeValue = 5;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public ResponseEntity<List<Tag>> findAll(@RequestParam(value = "page", required = false, defaultValue = "" + defaultPageValue) int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "" + defaultSizeValue) int size) {
        List<Tag> tags = this.tagService.findAll(page, size);
        List<Tag> response = new ArrayList<>();
        tags.forEach(tag -> {
            Tag copy = tag.copy();
            copy.add(linkTo(methodOn(TagController.class).find(tag.getId())).withSelfRel());
            response.add(copy);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody Tag tag) {
        Tag response = this.tagService.add(tag)
                .add(linkTo(methodOn(TagController.class).findAll(defaultPageValue, defaultSizeValue)).withRel("link to all Tags"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> find(@PathVariable("id") Long id) {
        Tag tag = this.tagService.find(id);
        Tag response = tag.copy();
        response.add(linkTo(methodOn(TagController.class).findAll(defaultPageValue, defaultSizeValue)).withRel("link to all Tags"));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) {
        this.tagService.deleteById(id);
        return new ModelAndView("redirect:/tags");
    }
}