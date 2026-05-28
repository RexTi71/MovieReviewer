package com.dominik.backend.service;

import com.dominik.backend.model.Movie;
import com.dominik.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final MovieRepository repository;
    public Page<Movie> getAllMovies(Pageable pageable){
        return repository.findAll(pageable);
    }
    public List<Movie> searchMovie(String title){
        return repository.findByTitleContaining(title);
    }
}
