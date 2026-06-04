package com.dominik.backend.service;

import com.dominik.backend.model.Movie;
import com.dominik.backend.repository.MovieRepository;
import com.dominik.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomeService {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;
    public List<Movie> getAllMovies(Pageable pageable){
        return movieRepository.findAll(pageable).getContent();
    }
    public Movie getMovie(Long id){return movieRepository.findById(id).orElse(null);}
    public List<Movie> searchMovie(String title){
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }
}
