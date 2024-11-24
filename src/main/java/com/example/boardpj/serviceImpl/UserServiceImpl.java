package com.example.boardpj.serviceImpl;

import com.example.boardpj.dto.UserDTO;
import com.example.boardpj.entity.User;
import com.example.boardpj.repository.UserRepository;
import com.example.boardpj.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void joinUser(UserDTO userDTO) {

        if (userRepository.existsById(userDTO.getUserId())) {
            throw new IllegalArgumentException("Duplicated User Id");
        }
        User user = User.builder()
                .userId(userDTO.getUserId())
                .userPw(passwordEncoder.encode(userDTO.getUserPw()))
                .userName(userDTO.getUserName())
                .userPhone1(userDTO.getUserPhone1())
                .userPhone2(userDTO.getUserPhone2())
                .userPhone3(userDTO.getUserPhone3())
                .userAddr1(userDTO.getUserAddr1())
                .userAddr2(userDTO.getUserAddr2())
                .userCompany(userDTO.getUserCompany())
                .build();

        userRepository.save(user);
    }

    //  아이디 중복체크
    @Override
    public boolean existUserId(String userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public Optional<User> getUserId(String userId) {
        return userRepository.findById(userId);
    }
}
