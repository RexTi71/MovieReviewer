package com.dominik.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CommentResponseDto {
    private Long id;
    private Long userId;
    private String username;
    private String content;
    private LocalDate date;
    private Long parentId;
}
