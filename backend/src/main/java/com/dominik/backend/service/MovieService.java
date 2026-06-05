package com.dominik.backend.service;

import com.dominik.backend.dto.MovieDto;
import com.dominik.backend.model.Movie;
import com.dominik.backend.repository.MovieRepository;
import com.dominik.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final ReviewRepository reviewRepository;

    public List<Movie> getAllMovies(Pageable pageable){
        return movieRepository.findAll(pageable).getContent();
    }
    public Movie getMovie(Long id){return movieRepository.findById(id).orElse(null);}
    public List<Movie> searchMovie(String title){
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }
    public List<MovieDto> getAllMoviesByCategory(String category){
        return movieRepository.findAllByCategories_Name(category);

    }
    public List<Movie> getTop10(){return movieRepository.getTop10(PageRequest.of(0,10));}

}
