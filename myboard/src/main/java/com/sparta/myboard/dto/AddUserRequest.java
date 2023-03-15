package com.sparta.myboard.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddUserRequest {

    @Size(min = 4,max = 10)
    @Pattern(regexp ="[a-z0-9]")
    private String username;
    @Size(min = 8,max = 15)
    @Pattern(regexp = "[a-zA-Z0-9`~!@#$%^&*()-_=+|{};:'\",.<>/?]")
    private String password;
}
