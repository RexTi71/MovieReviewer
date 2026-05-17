package com.dominik.backend.entità;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@Table(name = "film")
public class Film {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private Date productionDate;

    @ManyToMany
    @JoinTable(
            name = "genreXfilm",
            joinColumns = @JoinColumn(name = "film_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @ToString.Exclude
    private Set<Genre> genres;

    public Film(String title, String description, Date productionDate){
        this.title = title;
        this.description = description;
        this.productionDate = productionDate;
    }
}
