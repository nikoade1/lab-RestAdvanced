package com.epam.esm.controller;

import com.epam.esm.exceptions.TagNotFoundException;
import com.epam.esm.model.Tag;
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

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public ResponseEntity<List<Tag>> findAll(){
        List<Tag> tags = this.tagService.findAll();
        List<Tag> response = new ArrayList<>();
        tags.forEach(tag -> {
            Tag copy = new Tag(tag.getId(), tag.getName());
            try {
                copy.add(linkTo(methodOn(TagController.class).find(tag.getId())).withSelfRel());
            } catch (TagNotFoundException e) {
                throw new RuntimeException(e);
            }
            response.add(copy);
        });
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody Tag tag) {
        Tag response = this.tagService.add(tag)
                .add(linkTo(methodOn(TagController.class).findAll()).withRel("link to all Tags"));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tag> find(@PathVariable("id") Long id) throws TagNotFoundException {
        Tag tag = this.tagService.find(id);
        Tag copy = new Tag(tag.getId(), tag.getName());
        copy.add(linkTo(methodOn(TagController.class).findAll()).withRel("link to all Tags"));
        return ResponseEntity.ok(copy);
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") Long id) throws TagNotFoundException {
        this.tagService.deleteById(id);
        return new ModelAndView("redirect:/tags");
    }
}