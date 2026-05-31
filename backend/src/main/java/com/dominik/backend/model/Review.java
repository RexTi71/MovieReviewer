package com.dominik.backend.model;

import com.dominik.backend.model.chiaveComplessa.ReviewId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(ReviewId.class)
public class Review {
    @Id
    @ManyToOne
    @JoinColumn(name = "movie_id")
    Movie movie;
    @Id
    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;

    @Column(nullable = false)
    private Integer rating;
    private String title;
    @Column(length = 2000)
    private String content;
}
