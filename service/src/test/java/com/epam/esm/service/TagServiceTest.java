package com.epam.esm.service;

import com.epam.esm.model.Tag;
import com.epam.esm.repository.TagDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagDAO tagDAO;
    private TagService underTest;

    @BeforeEach
    void setUp() {
        underTest = new TagService(tagDAO);
    }

    @Test
    void add() {
        Tag tag = new Tag("testTag");

        underTest.add(tag);

        ArgumentCaptor<Tag> tagArgumentCaptor =
                ArgumentCaptor.forClass(Tag.class);

        verify(tagDAO).add(tagArgumentCaptor.capture());

        Tag capturedTag = tagArgumentCaptor.getValue();

        assertEquals(tag, capturedTag);
    }

    @Test
    void findAll() {
        underTest.findAll(1, 5);
        verify(tagDAO).findAll(1, 5);
    }

    @Test
    void find() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void getMostWidelyUsedTag() {
    }

    @Test
    void findByName() {
    }
}