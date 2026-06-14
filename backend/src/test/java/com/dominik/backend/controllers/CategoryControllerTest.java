package com.dominik.backend.controllers;

import com.dominik.backend.model.Category;
import com.dominik.backend.model.Movie;
import com.dominik.backend.repository.CategoryRepository;
import com.dominik.backend.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private MovieRepository movieRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private Category actionCategory;
    private Category sciFiCategory;
    private Movie matrixMovie;

    @BeforeEach
    void setUp() {
        // Czyszczenie bazy (kolejność ma znaczenie ze względu na klucze obce)
        movieRepository.deleteAll();
        categoryRepository.deleteAll();

        // 1. Tworzymy i zapisujemy kategorie
        actionCategory = new Category();
        actionCategory.setName("Akcja");
        actionCategory.setDescription("Filmy z dużą ilością wybuchów");
        categoryRepository.save(actionCategory);

        sciFiCategory = new Category();
        sciFiCategory.setName("Sci-Fi");
        sciFiCategory.setDescription("Fantastyka naukowa");
        categoryRepository.save(sciFiCategory);

        // 2. Tworzymy film i przypisujemy mu kategorię
        matrixMovie = new Movie();
        matrixMovie.setTitle("Matrix");
        matrixMovie.setDescription("Haker odkrywa prawdę.");
        matrixMovie.setRating(9.0f);
        matrixMovie.setProductionDate(LocalDate.of(1999, 3, 31));
        matrixMovie.setCategories(Set.of(actionCategory, sciFiCategory));
        movieRepository.save(matrixMovie);
    }

    @Test
    void shouldReturnAllCategories() throws Exception {
        mockMvc.perform(get("/api/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Oczekujemy dwóch kategorii z setUp()
                // Sprawdzamy czy poprawnie zmapowało nazwy
                .andExpect(jsonPath("$[0].name", is("Akcja")))
                .andExpect(jsonPath("$[1].name", is("Sci-Fi")));
    }

    @Test
    void shouldAddCategorySuccessfully() throws Exception {
        Category newCategory = new Category();
        newCategory.setName("Horror");
        newCategory.setDescription("Straszne filmy");

        mockMvc.perform(post("/api/v1/category")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCategory)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Pomyślnie dodano kategorie")));

        // Weryfikacja bazy danych: 2 z setUp() + 1 nowa
        List<Category> categoriesInDb = categoryRepository.findAll();
        assertEquals(3, categoriesInDb.size());
    }

    @Test
    void shouldReturnMoviesBySpecificCategory() throws Exception {
        // Szukamy filmów, które mają w sobie przypisaną kategorię "Sci-Fi"
        mockMvc.perform(get("/api/v1/category/{name}", "Sci-Fi")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Oczekujemy tylko Matrixa
                .andExpect(jsonPath("$[0].title", is("Matrix")))
                .andExpect(jsonPath("$[0].rating", is(9.0)));
    }

    @Test
    void shouldReturnEmptyListWhenNoMoviesInCategory() throws Exception {
        // Tworzymy pustą kategorię (bez podpiętych filmów)
        Category emptyCategory = new Category();
        emptyCategory.setName("Dramat");
        categoryRepository.save(emptyCategory);

        // Oczekujemy, że wyszukiwanie zwróci pustą listę []
        mockMvc.perform(get("/api/v1/category/{name}", "Dramat")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }
}