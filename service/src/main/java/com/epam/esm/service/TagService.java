package com.epam.esm.service;

import com.epam.esm.TagRepository;
import com.epam.esm.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagRepository tagDAO;

    @Autowired
    public TagService(TagRepository tagDao) {
        this.tagDAO = tagDao;
    }

    public Tag add(Tag tag) {
        return tagDAO.add(tag);
    }

    public List<Tag> findAll() {
        return tagDAO.findAll();
    }

    public Tag get(long id) {
        return tagDAO.find(id);
    }

    public void delete(Tag tag) {
        tagDAO.delete(tag);
    }

}
