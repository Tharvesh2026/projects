package com.example.session.api.auth;

import com.example.session.util.csrfValidator;
import com.example.session.model.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect("index.jsp?error=Session_Expired-Login_Again");
            return;
        }

        String sessionToken = session.getAttribute("csrfToken").toString();

        String cookieToken = getCookieValue(req, "CSRF_TOKEN");

        boolean valid = csrfValidator.validateCSRF(sessionToken, cookieToken);

        if (!valid) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Invalid CSRF Token");
            return;
        }

        User user = (User) session.getAttribute("user");
        
        session.invalidate();
        deleteCsrfCookie(req, res);
        res.sendRedirect("index.jsp?logout=SUCCESS");
        
        System.out.println("User " + user.getUsername() + " Logged out");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.sendRedirect(req.getContextPath() + "?error=InvalidMethod");
    }

    private String getCookieValue(HttpServletRequest req, String name) {

        Cookie[] cookies = req.getCookies();
        String value;

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                value = cookie.getValue();
                return value;
            }
        }

        return null;
    }

    private void deleteCsrfCookie(HttpServletRequest req, HttpServletResponse res) {

        String cookieHeader = "CSRF_TOKEN=" +
                "; Path=" + req.getContextPath() +
                "; Max-Age=0" +
                "; HttpOnly" +
                "; SameSite=Strict";

        res.addHeader("Set-Cookie", cookieHeader);
    }
}