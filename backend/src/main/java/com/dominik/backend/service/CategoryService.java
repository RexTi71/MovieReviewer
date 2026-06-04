package com.dominik.backend.service;

import com.dominik.backend.model.Category;
import com.dominik.backend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ResponseEntity<List<Category>> getAllCategories(){
        return ResponseEntity.ok(categoryRepository.findAll());
    }
    public ResponseEntity<String> addCategory(Category category){
        Category newCategory = categoryRepository.save(category);
        if(categoryRepository.findById(newCategory.getId()).isEmpty()){
            return ResponseEntity.badRequest().body("Nie udało się dodać kategorii");
        }
        return ResponseEntity.ok("Pomyślnie dodano kategorie");
    }
}
