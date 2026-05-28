package com.dominik.backend.repository;

import com.dominik.backend.model.CategoryXMovie;
import com.dominik.backend.model.Movie;
import com.dominik.backend.model.chiaveComplessa.CategoryXMovieId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryXMovieRepository extends JpaRepository<CategoryXMovie, CategoryXMovieId> {
    @Query("select cxm.movie from CategoryXMovie cxm where cxm.category.id = ?1")
    Page<Movie> allMoviesFromCategory(Long categoryId, Pageable pageable);
}
