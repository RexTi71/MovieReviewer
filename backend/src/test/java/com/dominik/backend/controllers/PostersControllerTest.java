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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PostersControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // Pobieramy prawdziwy kontroler zarządzany przez Springa
    @Autowired
    private PostersController postersController;

    private FileStorage mockPosterStorage;

    @BeforeEach
    void setUp() {
        // Ręcznie tworzymy sztuczny obiekt FileStorage
        mockPosterStorage = Mockito.mock(FileStorage.class);

        // Wstrzykujemy naszą atrapę w miejsce 'new FileStorage("posters/")' wewnątrz kontrolera
        ReflectionTestUtils.setField(postersController, "posterStorage", mockPosterStorage);
    }

    @Test
    void shouldReturnPosterImage() throws Exception {
        // Przygotowujemy sztuczny plik w pamięci RAM (ciąg znaków jako bajty)
        byte[] fakeImageBytes = "to-jest-sztuczny-obrazek".getBytes();
        ByteArrayResource resource = new ByteArrayResource(fakeImageBytes);

        // Uczymy naszą atrapę (Mocka) zachowania:
        when(mockPosterStorage.loadAsResource(1L)).thenReturn(resource);

        // Wykonujemy zapytanie HTTP i sprawdzamy wyniki
        mockMvc.perform(get("/api/poster/{id}", 1L))
                .andExpect(status().isOk()) // Oczekujemy kodu 200
                .andExpect(content().contentType(MediaType.IMAGE_PNG)) // Oczekujemy nagłówka PNG
                .andExpect(content().bytes(fakeImageBytes)); // Oczekujemy dokładnie naszych bajtów
    }

    @Test
    void shouldReturnBadRequestWhenFileNotFound() throws Exception {
        // Uczymy atrapę rzucać wyjątek dla nieistniejącego ID (np. 99)
        when(mockPosterStorage.loadAsResource(99L)).thenThrow(new StorageException("Nie znaleziono pliku lub brakuje fallback.png"));

        // Wykonujemy zapytanie HTTP i sprawdzamy wyniki
        mockMvc.perform(get("/api/poster/{id}", 99L))
                .andExpect(status().isBadRequest()) // Oczekujemy kodu 400
                .andExpect(content().string("Nie znaleziono pliku lub brakuje fallback.png")); // Oczekujemy treści błędu
    }
}