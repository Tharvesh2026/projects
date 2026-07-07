package com.example.SecurityRBAC.controller;


import com.example.SecurityRBAC.domain.Role;
import com.example.SecurityRBAC.service.RoleServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/resources/roles")
public class RoleController {
    private RoleServiceImpl service;

    @PostMapping
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        Role saved = service.saveRole(role);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public ResponseEntity<Page<Role>> getRoles(@RequestParam int page){
        Page<Role> roles = service.getAllRole(page,10);
        return ResponseEntity.status(HttpStatus.CREATED).body(roles);
    }
}
