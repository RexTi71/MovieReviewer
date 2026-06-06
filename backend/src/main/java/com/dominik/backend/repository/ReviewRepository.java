package com.dominik.backend.repository;

import com.dominik.backend.model.Review;
import com.dominik.backend.model.chiaveComplessa.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {

    List<Review> findAllByMovieId(Long id);
    Review findByAccountIdAndMovieId(Long accountId, Long movieId);
    @Query("select avg(r.rating) from Review r where r.movie.id=:id")
    Float avgRating(@Param("id") Long id);
}
