package com.example.boardpj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER_INFO")
public class User implements UserDetails {

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Column(name = "USER_PW")
    private String userPw;

    @Column(name = "USER_NAME")
    private String userName;

    @Column(name = "USER_PHONE1")
    private String userPhone1;

    @Column(name = "USER_PHONE2")
    private String userPhone2;

    @Column(name = "USER_PHONE3")
    private String userPhone3;

    @Column(name = "USER_ADDR1")
    private String userAddr1;

    @Column(name = "USER_ADDR2")
    private String userAddr2;

    @Column(name = "USER_COMPANY")
    private String userCompany;

    @Column(name = "CREATOR")
    private String creator;

    @Column(name = "CREATE_TIME")
    private String createTime;

    @Column(name = "MODIFIER")
    private String modifier;

    @Column(name = "MODIFIED_TIME")
    private String modifiedTime;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Board> boards;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.userId;
    }

    @Override
    public String getPassword() {
        return this.userPw;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
