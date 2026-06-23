package com.example.SSBoot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController
{
    @GetMapping("login")
    public String Login(){
        return "User Logged In";
    }

}
