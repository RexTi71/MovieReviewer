package com.dominik.backend.service;

import com.dominik.backend.dto.MovieDto;
import com.dominik.backend.dto.MovieRequestDto;
import com.dominik.backend.model.Account;
import com.dominik.backend.model.Category;
import com.dominik.backend.model.Movie;
import com.dominik.backend.model.UserType;
import com.dominik.backend.repository.CategoryRepository;
import com.dominik.backend.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final AccountService accountService;
    private final CategoryRepository categoryRepository;

    private final Long MOVIES_PER_PAGE = 10L;

    public List<Movie> getAllMovies(Pageable pageable){
        return movieRepository.findAll(pageable).getContent();
    }
    public Long countMovies(){
        return movieRepository.count() / MOVIES_PER_PAGE + 1;
    }
    public Movie getMovie(Long id){return movieRepository.findById(id).orElse(null);}
    public List<Movie> searchMovie(String title){
        return movieRepository.findByTitleContainingIgnoreCase(title);
    }
    public List<MovieDto> getAllMoviesByCategory(String category){
        return movieRepository.findAllByCategories_Name(category);

    }
    public List<Movie> getTop10(){return movieRepository.getTop10(PageRequest.of(0,10));}
    public String addMovie(String token,
                           MovieRequestDto movieDto) throws IllegalArgumentException{

        Account account = accountService.getAccountFromToken(token);

        if(account == null){
            throw new IllegalArgumentException("Token JWT wygasł");
        }

        if(!account.getUserType().equals(UserType.ADMIN)){
            throw new IllegalArgumentException("Nie posiadasz uprawnień do tej zawartości");
        }


        Set<String> categoryNames = Arrays.stream(movieDto.getCategories().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toSet());

        Set<Category> categories = categoryRepository.findByNameIn(categoryNames);
        Movie movie = new Movie();
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setProductionDate(movieDto.getProductionDate());
        movie.setCategories(categories);


        movieRepository.save(movie);

        return "Pomyślnie stworzono film";
    }
    public String deleteMovie(Long id) throws IllegalArgumentException{
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Nie znaleziono takiego filmu") );

        movieRepository.delete(movie);
        return "Pomyślnie usunięto film";
    }

}
