package com.example.boardpj.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardId implements Serializable {

    private String boardType;

    private Long boardNum;
}
