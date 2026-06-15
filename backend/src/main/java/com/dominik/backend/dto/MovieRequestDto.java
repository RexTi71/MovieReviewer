package com.dominik.backend.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class MovieRequestDto {
    private String title;
    private String description;
    private LocalDate productionDate;
    private String categories;
}
