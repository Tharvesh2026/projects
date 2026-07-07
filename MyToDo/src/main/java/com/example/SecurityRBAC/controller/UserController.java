package com.example.SecurityRBAC.controller;

import com.example.SecurityRBAC.domain.AppUser;
import com.example.SecurityRBAC.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/resources/users")
public class UserController {

    private final UserServiceImpl service;

    @PostMapping
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user){
        AppUser saved = service.saveUser(user);
        URI uri= URI.create(ServletUriComponentsBuilder.fromCurrentContextPath() .path("/resources/users"). toUriString());
        return ResponseEntity.created(uri).body(saved);
    }

    @GetMapping
    public ResponseEntity<Page<AppUser>> getUsers(@RequestParam int page){
        Page<AppUser> users = service.getAllUser(page,10);
        return ResponseEntity.ok().body(users);
    }

    @PostMapping("/assign/role")
    public ResponseEntity<AppUser> promoteUser(@RequestParam String uname,
                                               @RequestParam String rname){
        service.assignRoleToUser(uname, rname);
        return ResponseEntity.ok(service.getUser(uname));
    }

    @GetMapping("/user/{uname}")
    public ResponseEntity<AppUser> getUser(@PathVariable String uname){
        AppUser user =  service.getUser(uname);
        return ResponseEntity.ok(service.getUser(uname));
    }

}
