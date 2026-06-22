package com.example.session.util;

public class csrfValidator {

    public static boolean validateCSRF(String sessionToken, String requestToken) {
        return sessionToken != null &&
                requestToken != null &&
                sessionToken.equals(requestToken);
    }
}