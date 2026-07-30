package com.example.iaasconsole.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VirtualMachineService {

    private final CloudStackClientService cloudStackClientService;

    public void deployVirtualMachine(String name, String templateId, String serviceOfferingId) {
        log.info("Deploying VM: {} with Template: {} and Offering: {}", name, templateId, serviceOfferingId);
        
        // Use a default zone ID for the simulator if none provided
        // In a real app we'd fetch this dynamically or allow selection
        String defaultZoneId = "1128bd56-b4d9-4ac6-a7b9-c715b187ce11"; // Example UUID from standard sim, or just 1
        
        // We'll just pass "1" for simulator default zone (if it accepts internal DB ID), or standard UUID
        // The simulator usually has Zone 1
        try {
            String response = cloudStackClientService.deployVirtualMachine(name, templateId, serviceOfferingId, "1");
            log.info("CloudStack Response: {}", response);
        } catch (Exception e) {
            log.error("Failed to deploy VM: {}", e.getMessage(), e);
        }
    }
}
