package com.sparta.myboard.dto;

import lombok.Getter;

@Getter
public class BoardDto {
    private Long id;

    private String title;
    private String content;
    private String author;
    private String password;


}
