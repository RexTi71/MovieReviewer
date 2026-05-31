package com.dominik.backend.controllers;

import com.dominik.backend.model.Movie;
import com.dominik.backend.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class HomeController {
    private final HomeService service;
    @GetMapping("/movies")
    public List<Movie> getMovies(){
        Pageable pageable = PageRequest.of(0,15);
        return service.getAllMovies(pageable).getContent();
    }
    @GetMapping("/movie/{id}")
    public Movie getMovie(@PathVariable Long id){
        return service.getMovie(id);
    }
    @GetMapping("/search/{query}")
    public List<Movie> searchMovie(@PathVariable String query){
        return service.searchMovie(query);
    }

    //@GetMapping("/top10")
    //TODO:Доделать получение топ-10 фильмов по рейтингу
}
