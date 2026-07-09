package com.example.SecurityRBAC;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RenderController {
    @GetMapping("auth/login")
    public String login() {
        return "auth/login";
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/")
    public String home() {
        return "index";
    }
}

