package com.dominik.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReportDto {
    private String username;
    private Long commentId;
    private String content;
    private LocalDate date;

}
