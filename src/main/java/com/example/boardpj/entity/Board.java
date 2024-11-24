package com.example.boardpj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BOARD")
@IdClass(BoardId.class)
public class Board {

    @Id
    @Column(name = "BOARD_TYPE")
    private String boardType;

    @Id
    @Column(name = "BOARD_NUM")
    private Long boardNum;

    @Column(name = "BOARD_TITLE")
    private String boardTitle;

    @Column(name = "BOARD_COMMENT")
    private String boardComment;

    @Column(name = "CREATOR", insertable = false, updatable = false)
    private String creator;

    @Column(name = "CRAETE_TIME")
    private String createTime;

    @Column(name = "MODIFIER")
    private String modifier;

    @Column(name = "MODIFIED_TIME")
    private String modifiedTime;

    @Column(name = "FILE_ROOT")
    private String fileRoot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CREATOR", referencedColumnName = "CREATOR")
    private User user;

    @PrePersist
    public void prePersist() {
        this.createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        if (this.user != null) {
            this.creator = this.user.getUserId();
        }
    }

    public void changeBoard(String boardTitle, String boardComment) {
        this.boardTitle = boardTitle;
        this.boardComment = boardComment;
    }

}
