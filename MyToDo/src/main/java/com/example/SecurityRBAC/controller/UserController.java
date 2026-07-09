package com.example.SecurityRBAC.controller;

import com.example.SecurityRBAC.dto.request.CreateUserRequest;
import com.example.SecurityRBAC.dto.response.UserResponse;
import com.example.SecurityRBAC.service.UserServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/resources/users")
@RequiredArgsConstructor
public class UserController {

    private final UserServices service;

    @PostMapping
    @PreAuthorize("hasAnyValues='','',''")
    public ResponseEntity<UserResponse> saveUser(
            @Valid @RequestBody CreateUserRequest request) {

        UserResponse savedUser = service.saveUser(request);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{username}")
                .buildAndExpand(savedUser.getUsername())
                .toUri();

        return ResponseEntity.created(uri)
                .body(savedUser);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponse>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<UserResponse> users = service.getAllUser(page, size);

        return ResponseEntity.ok(users);
    }

    @PostMapping("/{username}/roles/{roleName}")
    public ResponseEntity<UserResponse> assignRoleToUser(
            @PathVariable String username,
            @PathVariable String roleName) {

        service.assignRoleToUser(username, roleName);

        return ResponseEntity.ok(service.getUser(username));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserResponse> getUser(
            @PathVariable String username) {

        return ResponseEntity.ok(service.getUser(username));
    }
}