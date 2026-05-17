package com.dominik.backend.Entità;

import com.dominik.backend.Entità.chiaveComplessa.ReviewId;
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
    private String content;
}
