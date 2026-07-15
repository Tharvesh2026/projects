package com.example.Student.service;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final AuditService auditService;

    public EmailService(AuditService auditService) {
        this.auditService = auditService;
    }

    public void sendEmail(String user) {
        System.out.println("Email sent to: " + user);
        auditService.audit("Email sent to " + user);
    }
}