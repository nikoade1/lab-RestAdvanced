package com.epam.esm.controller;

import com.epam.esm.model.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping("tags")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping()
    public List<Tag> findAll() {
        return this.tagService.findAll();
    }

    @PostMapping("/create")
    public Tag add(@RequestBody Tag tag) {
        return this.tagService.add(tag);
    }

    @GetMapping("/{id}")
    public Tag get(@PathVariable("id") long id) {
        return this.tagService.get(id);
    }

    @DeleteMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") long id) {
        Tag tag = this.tagService.get(id);
        this.tagService.delete(tag);
        return new ModelAndView("redirect:/tags");
    }

}