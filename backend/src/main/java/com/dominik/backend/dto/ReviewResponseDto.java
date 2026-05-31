package com.dominik.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewResponseDto {
    private Long movieId;
    private String title;
    private Integer rating;
    private Long userId;
    private String username;
    private String content;
}
