package com.dominik.backend.controllers;

import com.dominik.backend.dto.MovieDto;
import com.dominik.backend.model.Category;
import com.dominik.backend.model.Movie;
import com.dominik.backend.service.CategoryService;
import com.dominik.backend.service.MovieService;
import com.dominik.backend.service.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CategoryController {

    private final CategoryService categoryService;
    private final MovieService movieService;
    private final ResponseBuilder responseBuilder;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @PostMapping("/category")
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        try{
            return responseBuilder.buildSuccessResponse(categoryService.addCategory(category));

        }catch (IllegalArgumentException ex){
            return responseBuilder.buildErrorResponse(ex.getMessage());
        }
    }
    @GetMapping("/category/{name}")
    public ResponseEntity<List<MovieDto>> getAllMoviesByCategory(@PathVariable("name") String category){
        return ResponseEntity.ok(movieService.getAllMoviesByCategory(category));
    }
}
