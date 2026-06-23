package com.example.session.util;

public class ValidationDAOUtil {

    private ValidationDAOUtil() {
    }

    public static String sanitizeText(String value) {

        if (value == null) {
            return "";
        }

        value = value.replaceAll("<[^>]*>", "");

        return value.replaceAll("[^a-zA-Z0-9 .]", "")
                .trim();
    }

    public static String sanitizeName(String value) {

        if (value == null) {
            return "";
        }

        value = value.replaceAll("<[^>]*>", "");

        return value.replaceAll("[^a-zA-Z0-9 .]", "")
                .trim();
    }

    public static String sanitizeUsername(String username) {

        String cleaned = sanitizeText(username);

        if (cleaned == null) {
            return null;
        }

        return cleaned
                .toLowerCase()
                .replaceAll("\\s+", "_")
                .replaceAll("[^a-z0-9_]", "");
    }

    public static String sanitizeEmail(String email) {

        String cleaned = sanitizeText(email);

        if (cleaned == null) {
            return null;
        }

        return cleaned.toLowerCase();
    }

    public static String sanitizeRoleName(String roleName) {

        String cleaned = sanitizeText(roleName);

        if (cleaned == null) {
            return null;
        }

        return cleaned
                .toUpperCase()
                .replaceAll("\\s+", "_")
                .replaceAll("[^A-Z0-9_]", "");
    }

    public static boolean hasDangerousHtml(String value) {

        if (value == null) {
            return false;
        }

        return value.contains("<") ||
                value.contains(">") ||
                value.toLowerCase().contains("script") ||
                value.toLowerCase().contains("onerror") ||
                value.toLowerCase().contains("onclick") ||
                value.toLowerCase().contains("iframe");
    }
}