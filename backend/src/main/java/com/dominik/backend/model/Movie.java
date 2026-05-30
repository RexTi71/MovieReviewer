package com.dominik.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Movie {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(length = 1000)
    private String description;
    private Float rating;
    private LocalDate productionDate;


    public Movie(String title, String description, LocalDate productionDate){
        this.title = title;
        this.description = description;
        this.productionDate = productionDate;
    }
}
