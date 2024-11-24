package com.example.boardpj.service;

import com.example.boardpj.dto.UserDTO;
import com.example.boardpj.entity.User;

import java.util.Optional;

public interface UserService {

    void joinUser(UserDTO userDTO);

    boolean existUserId(String userId);

    Optional<User> getUserId(String userId);
}
