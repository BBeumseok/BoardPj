package com.example.boardpj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "COM_CODE")
@IdClass(CommCodeId.class)
public class CommCode {

    @Id
    @Column(name = "CODE_TYPE")
    private String codeType;

    @Id
    @Column(name = "CODE_ID")
    private String codeId;

    @Column(name = "CODE_NAME")
    private String codeName;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "MODIFIER")
    private String modifier;

    @Column(name = "MODIFIED_TIME")
    private String modifiedTime;
}
