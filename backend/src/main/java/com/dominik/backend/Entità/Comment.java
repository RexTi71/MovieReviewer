package com.dominik.backend.Entità;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Comment {
    @Id
    Long id;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Account account;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "review_account_id", referencedColumnName = "account_id"),
            @JoinColumn(name = "review_movie_id", referencedColumnName = "movie_id")
    })
    private Review review;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Comment replyTo;

    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Date date;
}
