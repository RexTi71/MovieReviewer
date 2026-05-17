package com.dominik.backend.entità;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Comment {
    @Id
    Long id;
    @ManyToOne
    private Account user;
    @ManyToOne
    private Review review;

    @ManyToOne
    @JoinColumn(nullable = true)
    private Comment replyTo;

    private String content;
    private Date date;
}
