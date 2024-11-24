package com.example.boardpj.service;

import com.example.boardpj.dto.BoardDTO;
import com.example.boardpj.entity.Board;
import com.example.boardpj.entity.BoardId;
import com.example.boardpj.entity.CommCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BoardService {

    //  게시글 목록
    Page<BoardDTO> boardList(Pageable pageable);
    
    //  게시글 등록
    BoardDTO register(BoardDTO boardDTO);
    
    //  게시글 조회
    BoardDTO read(String boardType, Long boardNum);

    //  게시글 수정
    BoardDTO update(String boardType, Long boardNum, BoardDTO boardDTO);
    
    //  게시글 삭제
    void delete(BoardId boardId);
    
    //  타입별로 페이징해서 조회
    Page<BoardDTO> typePaging(String type, Pageable pageable);

    //  공통코드
    Map<String, List<CommCode>> getCodes();
    
}
