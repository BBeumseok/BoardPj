package com.example.boardpj.controller;

import com.example.boardpj.dto.BoardDTO;
import com.example.boardpj.entity.BoardId;
import com.example.boardpj.entity.CommCode;
import com.example.boardpj.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    //  게시글 리스트
    @GetMapping("/list")
    public String list(@RequestParam(required = false, defaultValue = "all") String type,
                       @PageableDefault(size = 10, sort = "boardNum", direction = Sort.Direction.DESC)
                       Pageable pageable, Model model) {

        Page<BoardDTO> boardTypeList = boardService.typePaging(type, pageable);
        model.addAttribute("boardTypeList", boardTypeList);

        Map<String, List<CommCode>> codeList = boardService.getCodes();
        model.addAttribute("codeList", codeList.get("menu"));
        model.addAttribute("type", type);

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setCreator(userId);
        model.addAttribute("boardDTO", boardDTO);
        return "board/list";
    }

    //  게시글 등록 화면
    @GetMapping("/register")
    public String register(Model model) {

        Map<String, List<CommCode>> codeList = boardService.getCodes();
        model.addAttribute("codeList", codeList.get("menu"));

        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setCreator(userId);
        model.addAttribute("boardDTO", boardDTO);

        return "board/register";
    }

    //  게시글 등록 처리
    @PostMapping("/register")
    public String register(@ModelAttribute BoardDTO boardDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "board/register";
        }

        boardService.register(boardDTO);

        return "redirect:/board/list";
    }

    //  게시글 조회
    @GetMapping({"/read/{boardType}/{boardNum}"})
    public String read(@PathVariable String boardType, @PathVariable Long boardNum, Model model) {

        BoardDTO boardDTO = boardService.read(boardType, boardNum);

        model.addAttribute("boardDTO", boardDTO);
        return "board/read";
    }

    //  게시글 수정 화면
    @GetMapping("/update/{boardType}/{boardNum}")
    public String update(@PathVariable String boardType, @PathVariable Long boardNum, Model model) {
        BoardDTO boardDTO = boardService.read(boardType, boardNum);
        model.addAttribute("boardDTO", boardDTO);

        return "board/update";
    }


    //  게시글 수정 처리
    @PostMapping("/update/{boardType}/{boardNum}")
    public String update(@PathVariable String boardType, @PathVariable Long boardNum,
                         @ModelAttribute @Valid BoardDTO boardDTO,
                         BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if(bindingResult.hasErrors()) {
            return "board/update";
        }

        BoardDTO dto = boardService.update(boardType, boardNum, boardDTO);
        String encoding = URLEncoder.encode(boardType, StandardCharsets.UTF_8);

        redirectAttributes.addFlashAttribute("message", "수정 완료");
        return "redirect:/board/read/" + encoding + "/" + boardNum;
    }

    //  게시글 삭제
    @GetMapping("/delete/{boardType}/{boardNum}")
    public String delete(@PathVariable String boardType, @PathVariable Long boardNum) {
        boardService.delete(new BoardId(boardType, boardNum));

        return "redirect:/board/list";
    }
}
