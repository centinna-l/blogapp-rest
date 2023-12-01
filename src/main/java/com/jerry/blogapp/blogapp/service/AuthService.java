package com.jerry.blogapp.blogapp.service;

import com.jerry.blogapp.blogapp.payload.LoginDto;
import com.jerry.blogapp.blogapp.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
