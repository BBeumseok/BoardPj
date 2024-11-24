package com.example.boardpj.service;

import com.example.boardpj.entity.CommCode;
import com.example.boardpj.repository.CommCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommCodeService {

    private final CommCodeRepository commCodeRepository;

    public List<CommCode> phoneCode() {
        return commCodeRepository.findByCodeTypeOrderByCodeId("phone");
    }
}
