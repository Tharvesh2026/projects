package com.example.MyToDo.controller;

import com.example.MyToDo.dto.user.UserRequest;
import com.example.MyToDo.dto.user.UserResponse;
import com.example.MyToDo.service.AuthService;
import com.example.MyToDo.util.ApiResponse;
import com.example.MyToDo.util.JwtToken;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtToken jwtTokenUtil;

    @PostMapping("/auth/login")
    public ResponseEntity<ApiResponse<String>> login(@Valid @RequestBody UserRequest req) {
        UserResponse user = authService.login(req);
        String token = jwtTokenUtil.generateToken(user.getEmail());

        ApiResponse<String> response = ApiResponse.<String>builder()
                .success(true)
                .message("Login Successful")
                .data(token)
                .status(HttpStatus.OK.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/register")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody UserRequest req) {
        UserResponse user = authService.register(req);

        ApiResponse<UserResponse> response = ApiResponse.<UserResponse>builder()
                .success(true)
                .message("Registered Successfully")
                .data(user)
                .status(HttpStatus.CREATED.value())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
