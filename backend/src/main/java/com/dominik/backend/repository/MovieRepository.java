package com.dominik.backend.repository;

import com.dominik.backend.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
    List<Movie> findByTitleContainingIgnoreCase(String title);
    @Modifying
    @Transactional
    @Query("update Movie m set m.rating = :rating where m.id = :movieId")
    void updateMovieRating(@Param("rating") Float rating,
                           @Param("movieId") Long id);
}
