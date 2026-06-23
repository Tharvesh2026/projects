package com.example.session.util;

import jakarta.servlet.http.HttpServletRequest;

public class RequestUtil {

    public static String getClientIp(HttpServletRequest req) {

        String ip = req.getHeader("CF-Connecting-IP");

        if (ip == null || ip.isBlank()) {
            ip = req.getHeader("X-Forwarded-For");
        }

        if (ip != null && ip.contains(",")) {
            ip = ip.split(",")[0].trim();
        }

        if (ip == null || ip.isBlank()) {
            ip = req.getRemoteAddr();
        }

        return ip;
    }

    public static String getUserAgent(HttpServletRequest req) {

        String agent = req.getHeader("User-Agent");

        if (agent == null || agent.isBlank()) {
            return "Unknown";
        }

        return agent;
    }

    public static String getBrowser(HttpServletRequest req) {

        String agent = getUserAgent(req);

        if (agent.contains("Edg")) {
            return "Microsoft Edge";
        }

        if (agent.contains("Chrome")) {
            return "Google Chrome";
        }

        if (agent.contains("Firefox")) {
            return "Mozilla Firefox";
        }

        if (agent.contains("Safari")) {
            return "Safari";
        }

        return "Unknown Browser";
    }

    public static String getOS(HttpServletRequest req) {

        String agent = getUserAgent(req);

        if (agent.contains("Windows")) {
            return "Windows";
        }

        if (agent.contains("Linux")) {
            return "Linux";
        }

        if (agent.contains("Mac")) {
            return "macOS";
        }

        if (agent.contains("Android")) {
            return "Android";
        }

        if (agent.contains("iPhone") || agent.contains("iPad")) {
            return "iOS";
        }

        return "Unknown OS";
    }
}