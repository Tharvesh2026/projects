package com.example.SecurityRBAC.controller;

import com.example.SecurityRBAC.dto.request.CreateUserRequest;
import com.example.SecurityRBAC.dto.response.UserResponse;
import com.example.SecurityRBAC.service.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final UserServices service;

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(
            @Valid @RequestBody CreateUserRequest request) {

        UserResponse savedUser = service.saveUser(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/api/resources/users/{username}")
                .buildAndExpand(savedUser.getUsername())
                .toUri();

        return ResponseEntity.created(uri)
                .body(savedUser);
    }
}