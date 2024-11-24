package com.example.boardpj.repository;

import com.example.boardpj.entity.CommCode;
import com.example.boardpj.entity.CommCodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommCodeRepository extends JpaRepository<CommCode, CommCodeId> {

    @Query("SELECT c FROM CommCode c WHERE c.codeId = :codeId")
    List<CommCode> findByCodeId(@Param("codeId") CommCodeId codeId);

    List<CommCode> findByCodeTypeOrderByCodeId(String codeType);

    Optional<CommCode> findByCodeTypeAndCodeId(String codeType, String codeId);


}
