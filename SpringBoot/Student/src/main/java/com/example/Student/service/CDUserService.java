package com.example.Student.service;

import org.springframework.stereotype.Service;

@Service
public class CDUserService {
    private final EmailService emailService;

    public CDUserService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void createUser(String name) {
        System.out.println("User created: " + name);
        emailService.sendEmail(name);
    }

    public void logAction(String action) {
        System.out.println("UserService log: " + action);
    }
}