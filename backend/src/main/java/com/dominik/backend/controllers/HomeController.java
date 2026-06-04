package com.dominik.backend.controllers;

import com.dominik.backend.dto.MovieDto;
import com.dominik.backend.model.Movie;
import com.dominik.backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController {

    private final MovieService service;

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMovies(){
        Pageable pageable = PageRequest.of(0,15);
        return ResponseEntity.ok(service.getAllMovies(pageable));
    }
    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        return ResponseEntity.ok(service.getMovie(id));
    }
    @GetMapping("/search/{query}")
    public ResponseEntity<List<Movie>> searchMovie(@PathVariable String query){
        return ResponseEntity.ok(service.searchMovie(query));
    }

    //@GetMapping("/top10")
    //TODO:Доделать получение топ-10 фильмов по рейтингу
}
