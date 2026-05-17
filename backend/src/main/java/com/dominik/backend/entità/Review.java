package com.dominik.backend.entità;

import com.dominik.backend.entità.chiaveComplessa.ReviewId;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@IdClass(ReviewId.class)
public class Review {
    @Id
    @ManyToOne
    Movie movie;
    @Id
    @ManyToOne
    Account user;

    private Integer rating;
    private String content;
}
