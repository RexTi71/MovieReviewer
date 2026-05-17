package com.dominik.backend.entità;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
//Защита от повторных жалоб на один и тот же комментарий
@Table(name = "report", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "comment_id"}))
public class Report {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Comment comment;

    private String reason;
}
