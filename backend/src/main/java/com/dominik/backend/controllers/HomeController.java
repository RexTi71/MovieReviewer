package com.dominik.backend.controllers;

import com.dominik.backend.dto.MovieDto;
import com.dominik.backend.model.Movie;
import com.dominik.backend.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController {

    private final MovieService service;
    private final int PAGE_SIZE = 10;

    @GetMapping("/movies")
    public ResponseEntity<List<Movie>> getMovies(@RequestParam int page){
        Pageable pageable = PageRequest.of(page,PAGE_SIZE);
        return ResponseEntity.ok(service.getAllMovies(pageable));
    }
    @GetMapping("/movies-amount")
    public ResponseEntity<Long> countMovies(){
        return ResponseEntity.ok(service.countMovies());
    }
    @GetMapping("/movie/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        return ResponseEntity.ok(service.getMovie(id));
    }
    @GetMapping("/search/{query}")
    public ResponseEntity<List<Movie>> searchMovie(@PathVariable String query){
        return ResponseEntity.ok(service.searchMovie(query));
    }
    @GetMapping("/top10")
    public List<Movie> getTop10(){return service.getTop10();}
}
