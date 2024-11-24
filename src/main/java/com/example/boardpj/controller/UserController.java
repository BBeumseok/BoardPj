package com.example.boardpj.controller;

import com.example.boardpj.dto.UserDTO;
import com.example.boardpj.service.CommCodeService;
import com.example.boardpj.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class UserController {

    private final UserService userService;
    private final CommCodeService commCodeService;

    @GetMapping("/join")
    public String joinGet(Model model) {

        model.addAttribute("phone", commCodeService.phoneCode());
        model.addAttribute("userDTO", new UserDTO());
        return "member/join";
    }

    @PostMapping("/join")
    public String joinPost(@ModelAttribute @Valid UserDTO userDTO, BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) {
            return "member/join";
        }

        try {
            userService.joinUser(userDTO);
            return "redirect:/member/login";

        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "member/join";
        }
    }

    @GetMapping("/login")
    public String loginGet(@RequestParam(value = "error", required = false) String error,
                           @RequestParam(value = "exception", required = false) String exception,
                           Model model) {

        model.addAttribute("error", error);
        model.addAttribute("exception", exception);

        return "member/login";
    }

    //  아이디 중복확인
    @GetMapping("check-id")
    @ResponseBody
    public Map<String, Boolean> checkId(@RequestParam String userId) {
        boolean available = !userService.existUserId(userId);
        return Collections.singletonMap("available", available);
    }
}
