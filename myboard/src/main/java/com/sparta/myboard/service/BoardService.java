package com.sparta.myboard.service;

import com.sparta.myboard.dto.BoardDto;
import com.sparta.myboard.dto.ResultDto;
import com.sparta.myboard.entity.Board;
import com.sparta.myboard.entity.User;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.BoardRepository;
import com.sparta.myboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private  final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public Board createBoard(BoardDto requestDto, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        // 토큰이 있는 경우에만 관심상품 추가 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

            Board board = new Board(requestDto,user);
            board.setUsername(claims.getSubject());
            boardRepository.save(board);
            return board;
        }
        return null;
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
    public Long update(Long id, BoardDto requestDto,HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Board board = boardRepository.findById(id).orElseThrow(
                () -> new NullPointerException("아이디가 존재하지 않습니다.")
        );
        if (token != null) {
            // Token 검증
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );
            Board board1  = boardRepository.findByIdAndUsername(id,user.getUsername()).orElseThrow(
                    ()->  new IllegalArgumentException("사용자가 다릅니다.")
            );

            board1.setUsername(claims.getSubject());
             board1.update(requestDto);

            return board.getId();
        }
        return null;
    }

    @Transactional
    public ResultDto deleteBoard(Long id,HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        int num = 0;
        ResultDto resultDto = new ResultDto();
        if (jwtUtil.validateToken(token)) {
            // 토큰에서 사용자 정보 가져오기
            claims = jwtUtil.getUserInfoFromToken(token);

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
            );

           num= boardRepository.deleteByIdAndUsername(id,user.getUsername());

            if(num>0){
                resultDto.setResult("success");
                resultDto.setBool(true);
            }else{
                resultDto.setResult("사용자가 다릅니다.");
                resultDto.setBool(false);
            }
        }else {
            throw new IllegalArgumentException("Token Error");
        }

        // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
        User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );
        return resultDto;
    }

}
