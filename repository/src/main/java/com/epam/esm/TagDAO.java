package com.epam.esm;

import com.epam.esm.model.Tag;

import java.util.List;

public interface TagDAO {

    List<Tag> findAll();

    Tag add(Tag tag);

    Tag find(Long id);

    void delete(Tag tag);

    List<Tag> findByName(String name);
}
