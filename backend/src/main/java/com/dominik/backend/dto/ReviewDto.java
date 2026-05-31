package com.dominik.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewDto {
    private String token;
    private Long movieId;
    private Integer rating;
    private String title;
    private String content;

}
