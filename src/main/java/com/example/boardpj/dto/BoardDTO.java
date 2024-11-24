package com.example.boardpj.dto;

import lombok.Data;

@Data
public class BoardDTO {

    private String boardType;

    private Long boardNum;

    private String boardTitle;

    private String boardComment;

    private String creator;

    private String createTime;

    private String modifier;

    private String modifiedTime;

    private String fileRoot;
}
