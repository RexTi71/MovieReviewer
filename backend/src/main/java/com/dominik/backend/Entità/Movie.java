package com.dominik.backend.Entità;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

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
    private String description;
    private Date productionDate;


    public Movie(String title, String description, Date productionDate){
        this.title = title;
        this.description = description;
        this.productionDate = productionDate;
    }
}
