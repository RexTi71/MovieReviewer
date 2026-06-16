package com.dominik.backend.controllers;

import com.dominik.backend.service.filemanager.FileStorage;
import com.dominik.backend.service.filemanager.StorageException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostersController postersController;

    private FileStorage mockPosterStorage;

    @BeforeEach
    void setUp() {
        mockPosterStorage = Mockito.mock(FileStorage.class);

        ReflectionTestUtils.setField(postersController, "posterStorage", mockPosterStorage);
    }

    @Test
    void shouldReturnPosterImage() throws Exception {
        //sztuczny obrazek w pamieci RAM
        byte[] fakeImageBytes = "to-jest-sztuczny-obrazek".getBytes();
        ByteArrayResource resource = new ByteArrayResource(fakeImageBytes);

        when(mockPosterStorage.loadAsResource(1L)).thenReturn(resource);

        mockMvc.perform(get("/api/poster/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_PNG))
                .andExpect(content().bytes(fakeImageBytes));
    }

    @Test
    void shouldReturnBadRequestWhenFileNotFound() throws Exception {
        //wyjatek dla nieistniejacego ID
        when(mockPosterStorage.loadAsResource(99L)).thenThrow(new StorageException("Nie znaleziono pliku lub brakuje fallback.png"));

        mockMvc.perform(get("/api/poster/{id}", 99L))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.response", is("Nie znaleziono pliku lub brakuje fallback.png")));
    }
}