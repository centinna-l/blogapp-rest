package com.jerry.blogapp.blogapp.service.impl;

import com.jerry.blogapp.blogapp.exceptions.BlogApiException;
import com.jerry.blogapp.blogapp.models.Role;
import com.jerry.blogapp.blogapp.models.User;
import com.jerry.blogapp.blogapp.payload.LoginDto;
import com.jerry.blogapp.blogapp.payload.RegisterDto;
import com.jerry.blogapp.blogapp.repository.RoleRepository;
import com.jerry.blogapp.blogapp.repository.UserRepository;
import com.jerry.blogapp.blogapp.security.JwtAuthenticationFilter;
import com.jerry.blogapp.blogapp.security.JwtTokenProvider;
import com.jerry.blogapp.blogapp.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(
            AuthenticationManager authenticationManager,
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {
        System.out.println("Login Implementation");
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getUsernameOrEmail(),
                                loginDto.getPassword()));
        System.out.println("After Authenticate");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateJwt(authentication);
        System.out.print("Token: " + token);
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        // Add check for username exists or not
        if (userRepository.existsByUsername(registerDto.getUsername())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Username Exists");
        }

        // check for email
        if (userRepository.existsByEmail(registerDto.getEmail())) {
            throw new BlogApiException(HttpStatus.BAD_REQUEST, "Email Exists");

        }
        User user = new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());
        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        roles.add(userRole);
        user.setRoles(roles);
        System.out.println(user.getRoles() + " " + userRole.getName());
        userRepository.save(user);
        return "User Registered Successfully";
    }
}
