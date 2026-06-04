package com.dominik.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class MovieDto {
    private Long id;
    private String title;
    private String description;
    private Float rating;
    private LocalDate productionDate;
}
