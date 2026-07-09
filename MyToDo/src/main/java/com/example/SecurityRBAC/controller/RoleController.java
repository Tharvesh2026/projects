package com.example.SecurityRBAC.controller;

import com.example.SecurityRBAC.dto.request.CreateRoleRequest;
import com.example.SecurityRBAC.dto.response.RoleResponse;
import com.example.SecurityRBAC.service.RolesServices;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RolesServices service;

    @PostMapping("/save")
    public ResponseEntity<RoleResponse> saveRole(
            @Valid @RequestBody CreateRoleRequest request) {

        RoleResponse savedRole = service.saveRole(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedRole);
    }

    @GetMapping
    public ResponseEntity<Page<RoleResponse>> getRoles(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<RoleResponse> roles = service.getAllRole(page, size);

        return ResponseEntity.ok(roles);
    }
}