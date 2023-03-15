package com.sparta.myboard.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String Password;

    @Enumerated(EnumType.STRING)
    private RoleType RoleType;

    public User(String username, String password,RoleType roleType) {
        this.username = username;
        this.Password = password;
        this.RoleType = roleType;
    }
}