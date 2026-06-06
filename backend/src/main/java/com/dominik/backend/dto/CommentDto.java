package com.dominik.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDto {
    private String content;
    private String token;
    private Long movieId;
}
