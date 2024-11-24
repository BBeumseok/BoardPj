package com.example.boardpj.serviceImpl;

import com.example.boardpj.dto.BoardDTO;
import com.example.boardpj.entity.Board;
import com.example.boardpj.entity.BoardId;
import com.example.boardpj.entity.CommCode;
import com.example.boardpj.entity.User;
import com.example.boardpj.repository.BoardRepository;
import com.example.boardpj.repository.CommCodeRepository;
import com.example.boardpj.repository.UserRepository;
import com.example.boardpj.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final ModelMapper modelMapper;
    private final CommCodeRepository commCodeRepository;
    private final UserRepository userRepository;


    //  게시글 목록
    @Override
    public Page<BoardDTO> boardList(Pageable pageable) {

        return boardRepository.findAll(pageable).map(board -> modelMapper.map(board, BoardDTO.class));
    }


    //  게시글 등록
    @Override
    public BoardDTO register(BoardDTO boardDTO) {

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("User Not Found"));

        Board board = modelMapper.map(boardDTO, Board.class);

        board.setUser(user);

        //  등록 시간 설정
        board.setCreateTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        CommCode code = commCodeRepository.findByCodeTypeAndCodeId("menu", boardDTO.getBoardType())
                .orElseThrow(() -> new RuntimeException("BoardType Not Found"));

        // 게시글 타입을 공통코드의 이름으로 저장
        board.setBoardType(code.getCodeName());

        Board savedBoard = boardRepository.save(board);
        return modelMapper.map(savedBoard, BoardDTO.class);
    }


    //  게시글 조회
    @Override
    public BoardDTO read(String boardType, Long boardNum) {

        Board board = boardRepository.findById(new BoardId(boardType, boardNum)).
                orElseThrow(() -> new RuntimeException("Select Board Not Found"));


        return modelMapper.map(board, BoardDTO.class);
    }


    //  게시글 수정
    @Override
    public BoardDTO update(String boardType, Long boardNum, BoardDTO boardDTO) {

        Board board = boardRepository.findById(new BoardId(boardType, boardNum))
                .orElseThrow(() -> new RuntimeException("Select Board Not Found"));

        modelMapper.map(boardDTO, board);

        board.changeBoard(board.getBoardTitle(), board.getBoardComment());

        board.setModifiedTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        Board updateBoard = boardRepository.save(board);
        return modelMapper.map(updateBoard, BoardDTO.class);
    }


    //  게시글 삭제
    @Override
    public void delete(BoardId boardId) {
        boardRepository.deleteById(boardId);
    }

    //  타입별로 조회
    @Override
    public Page<BoardDTO> typePaging(String type, Pageable pageable) {

        if(type == null || type.equals("all")) {
            return boardList(pageable);
        }

        CommCode code = commCodeRepository.findByCodeTypeAndCodeId("menu", type)
                .orElseThrow(() -> new RuntimeException("boardType Not Found"));

        Page<Board> boards = boardRepository.findByBoardType(code.getCodeName(), pageable);

        return boards.map(board -> modelMapper.map(board, BoardDTO.class));
    }

    //  공통코드 가져오기
    @Override
    public Map<String, List<CommCode>> getCodes() {

        List<CommCode> codeList = commCodeRepository.findAll();

        CommCode commCode = new CommCode();
        commCode.setCodeType("menu");
        commCode.setCodeId("all");
        commCode.setCodeName("전체");

        List<CommCode> filtering = codeList.stream().filter(code -> "menu".equals(code.getCodeType()))
                .collect(Collectors.toList());

        filtering.add(0, commCode);

        return Map.of("menu", filtering);
    }
}
