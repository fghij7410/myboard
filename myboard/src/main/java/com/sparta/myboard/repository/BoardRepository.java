package com.sparta.myboard.repository;

import com.sparta.myboard.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board,Long> {
    List<Board> findAllByOrderByModifiedAtDesc();

    Optional<Board> findById(Long id);
    int deleteByIdAndUsername(Long id, String username );

    Optional<Board> findByIdAndUsername(Long id, String username);
}
