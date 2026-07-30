package com.example.iaasconsole.security;

import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import java.util.TreeMap;

@Service
public class CloudStackSignatureService {

    public String generateSignature(String query, String secretKey) {
        try {
            // Split and sort query parameters
            Map<String, String> params = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            if (query != null && !query.isEmpty()) {
                String[] pairs = query.split("&");
                for (String pair : pairs) {
                    int idx = pair.indexOf("=");
                    String key = idx > 0 ? pair.substring(0, idx).toLowerCase() : pair.toLowerCase();
                    String value = idx > 0 && pair.length() > idx + 1 ? pair.substring(idx + 1) : "";
                    
                    value = java.net.URLDecoder.decode(value, StandardCharsets.UTF_8.name());
                    params.put(key, value);
                }
            }

            // Construct string to sign
            StringBuilder stringToSign = new StringBuilder();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                if (!stringToSign.isEmpty()) {
                    stringToSign.append("&");
                }
                stringToSign.append(entry.getKey()).append("=")
                            .append(URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8.name())
                                    .replace("+", "%20")); // CloudStack requires %20 instead of +
            }

            // Create HMAC-SHA1 signature
            Mac mac = Mac.getInstance("HmacSHA1");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA1");
            mac.init(secretKeySpec);
            byte[] digest = mac.doFinal(stringToSign.toString().toLowerCase().getBytes(StandardCharsets.UTF_8));
            
            return Base64.getEncoder().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to generate CloudStack API signature", e);
        }
    }
}
