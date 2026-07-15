package com.example.Student.service;

import org.springframework.stereotype.Service;

@Service
public class AuditService {

    private final CDUserService userService;

    public AuditService(CDUserService userService) {
        this.userService = userService;
    }

    public void audit(String msg) {
        System.out.println("AUDIT: " + msg);

        // creates circular dependency loop
        userService.logAction("Audit completed");
    }
}
