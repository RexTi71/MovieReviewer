package com.dominik.backend.Archivio;

import com.dominik.backend.Entità.CategoryXMovie;
import com.dominik.backend.Entità.Movie;
import com.dominik.backend.Entità.chiaveComplessa.CategoryXMovieId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArchCategoryXMovie extends JpaRepository<CategoryXMovie, CategoryXMovieId> {
    @Query("select cxm.movie from CategoryXMovie cxm where cxm.category.id = ?1")
    Page<Movie> allMoviesFromCategory(Long categoryId, Pageable pageable);
}
