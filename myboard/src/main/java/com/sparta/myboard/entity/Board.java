package com.sparta.myboard.entity;


import com.sparta.myboard.dto.BoardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Board extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String username;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    public Board(BoardDto requestDto,User user) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.username=requestDto.getUsername();
        this.user=user;
    }

    public void update(BoardDto requestDto) {
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
    }
}