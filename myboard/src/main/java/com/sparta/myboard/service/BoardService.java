package com.sparta.myboard.service;

import com.sparta.myboard.dto.BoardDto;
import com.sparta.myboard.dto.ResultDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Board createBoard(BoardDto requestDto) {
        Board board = new Board(requestDto);
        boardRepository.save(board);
        return board;
    }

    @Transactional(readOnly = true)
    public List<Board> getBoards() {
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    @Transactional(readOnly = true)
    public Optional<Board> getBoard(Long id) {
        return boardRepository.findById(id);
    }

    @Transactional
    public Long update(Long id, BoardDto requestDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        board.update(requestDto);
        return board.getId();
    }

    @Transactional
    public ResultDto deleteBoard(Long id,String password) {
        int num = boardRepository.deleteByIdAndPassword(id,password);
            ResultDto resultDto = new ResultDto();
        if(num>0){
            resultDto.setResult("success");
            resultDto.setBool(true);
            return resultDto;
        }else{
            resultDto.setResult("비밀번호가 다릅니다.");
            resultDto.setBool(false);
            return resultDto;
        }
    }

}
