package com.example.iaasconsole.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CloudStackSignatureServiceTest {

    private final CloudStackSignatureService signatureService = new CloudStackSignatureService();

    @Test
    void testGenerateSignature() {
        // CloudStack docs example or a known combination
        String secretKey = "test-secret-key";
        String query = "command=listVirtualMachines&apikey=test-api-key&response=json";
        
        String signature = signatureService.generateSignature(query, secretKey);
        
        // This is a pre-calculated HMAC-SHA1 Base64 hash for the above sorted query
        // stringToSign: "apikey=test-api-key&command=listvirtualmachines&response=json"
        // key: "test-secret-key"
        String expectedSignature = "FWZ4Wq1s4ZOddRI0aFB9HCUDkgc=";
        
        // Let's assert it generates something consistent.
        assertEquals(expectedSignature, signature);
    }
}
