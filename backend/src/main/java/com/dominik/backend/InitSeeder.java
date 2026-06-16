package com.dominik.backend;

import com.dominik.backend.repository.MovieRepository;
import com.dominik.backend.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitSeeder implements CommandLineRunner {
    private final ReviewService service;
    private final MovieRepository movieRepository;
    @Override
    public void run(String... args) throws Exception {
        movieRepository.findAll().forEach(movie ->{
            service.updateRating(movie.getId());
        });
    }
}
