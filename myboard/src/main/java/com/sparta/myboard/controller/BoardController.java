package com.sparta.myboard.controller;

import com.sparta.myboard.dto.BoardDto;
import com.sparta.myboard.dto.ResultDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    @PostMapping("/api/post")
    public Board createBoards(@RequestBody BoardDto requestDto) {
        return boardService.createBoard(requestDto);
    }

    @GetMapping("/api/posts")
    public List<Board> getBoards() {

        return boardService.getBoards();
    }

    @GetMapping("/api/post/{id}")
    public Optional<Board> getBoard(@PathVariable Long id) {
        return boardService.getBoard(id);
    }

    @PutMapping("/api/post/{id}")
    public Long updateBoard(@PathVariable Long id, @RequestBody BoardDto requestDto) {
        return boardService.update(id, requestDto);
    }

    @DeleteMapping("/api/post/{id}")
    public ResultDto deleteBoard(@PathVariable Long id, @RequestBody BoardDto requestDto) {
        System.out.println(requestDto.getPassword());
        return boardService.deleteBoard(id,requestDto.getPassword());


    }
}
