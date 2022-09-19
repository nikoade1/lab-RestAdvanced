package com.epam.esm.service;

import com.epam.esm.exceptions.ItemNotFoundException;
import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {
    private final TagDAO tagDAO;

    @Autowired
    public TagService(TagDAO tagDao) {
        this.tagDAO = tagDao;
    }

    public Tag add(Tag tag) {
        return this.tagDAO.add(tag);
    }

    public List<Tag> findAll() {
        return this.tagDAO.findAll();
    }

    public Tag find(long id) throws ItemNotFoundException {
        Tag response = this.tagDAO.find(id);
        if (response == null) throw new ItemNotFoundException("Tag with id " + id + "not found");
        return response;
    }

    public void delete(Tag tag) {
        this.tagDAO.delete(tag);
    }

    public void deleteById(long id) throws ItemNotFoundException {
        Tag tag = find(id);
        delete(tag);
    }

    public List<Tag> findByName(String name) {
        return this.tagDAO.findByName(name);
    }
}
