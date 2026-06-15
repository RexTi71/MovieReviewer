package com.dominik.backend.controllers;

import com.dominik.backend.service.ResponseBuilder;
import com.dominik.backend.service.filemanager.FileStorage;
import com.dominik.backend.service.filemanager.StorageException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/poster")
public class PostersController {
    private final ResponseBuilder responseBuilder;
    FileStorage posterStorage = new FileStorage("posters/");
    @GetMapping("/{id}")
    public ResponseEntity<?> getMoviePoster(@PathVariable("id") Long id){
        try{
            return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(posterStorage.loadAsResource(id));
        }catch (StorageException ex){
            return responseBuilder.buildErrorResponse(ex.getMessage());
        }
    }
}
