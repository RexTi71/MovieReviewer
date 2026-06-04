package com.dominik.backend.controllers;

import com.dominik.backend.model.Category;
import com.dominik.backend.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
@Slf4j
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    @PostMapping("/category")
    public ResponseEntity<String> addCategory(@RequestBody Category category){
        try{
            return ResponseEntity.ok(categoryService.addCategory(category));

        }catch (IllegalArgumentException ex){
            log.warn(ex.getMessage());
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
