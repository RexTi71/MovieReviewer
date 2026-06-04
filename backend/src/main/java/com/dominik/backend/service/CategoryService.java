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

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public String addCategory(Category category){
        categoryRepository.save(category);
        if(categoryRepository.findById(category.getId()).isEmpty()){
            throw new IllegalArgumentException("Nie udało się dodać kategorii");
        }
        return "Pomyślnie dodano kategorie";
    }
}
