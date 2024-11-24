package com.example.boardpj.repository;

import com.example.boardpj.entity.Board;
import com.example.boardpj.entity.BoardId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, BoardId> {

    //  게시글 타입별로 페이징 처리
    Page<Board> findByBoardType(String boardType, Pageable pageable);
}
