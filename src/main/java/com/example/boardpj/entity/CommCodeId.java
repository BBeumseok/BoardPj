package com.example.boardpj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommCodeId implements Serializable {

    private String codeType;

    private String codeId;
}
