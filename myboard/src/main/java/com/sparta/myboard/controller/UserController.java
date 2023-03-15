package com.sparta.myboard.controller;

import com.sparta.myboard.dto.AddUserRequest;
import com.sparta.myboard.dto.LoginRequestDto;
import com.sparta.myboard.dto.SignupRequestDto;
import com.sparta.myboard.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/auth/signup")
    public String signup(@RequestBody  SignupRequestDto signupRequestDto, @Valid AddUserRequest addUserRequestDto ){
        userService.signup(signupRequestDto);
        return "success";
    }

    @PostMapping("/auth/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response){
        userService.login(loginRequestDto,response);
        return "success";
    }
}
