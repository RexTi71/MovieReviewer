package com.dominik.backend.repository;

import com.dominik.backend.model.Review;
import com.dominik.backend.model.chiaveComplessa.ReviewId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ReviewRepository extends JpaRepository<Review, ReviewId> {
    List<Review> findAllByMovieId(Long id);
}
