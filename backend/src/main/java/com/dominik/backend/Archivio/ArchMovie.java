package com.dominik.backend.Archivio;

import com.dominik.backend.Entità.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArchMovie extends JpaRepository<Movie,Long> {
    @Query("select m from Movie m")
    Page<Movie> getAllMovies(Pageable pageable);
}
