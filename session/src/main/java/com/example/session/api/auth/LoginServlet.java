package com.example.session.api.auth;

import com.example.session.DAO.UserDAO;
import com.example.session.model.User;
import com.example.session.exceptions.ApplicationException;
import com.example.session.exceptions.AuthenticationException;
import com.example.session.exceptions.ValidationException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final int SESSION_TIMEOUT_SECONDS = 600;
    private static final int REMEMBER_ME_COOKIE_AGE = 7 * 24 * 60 * 60;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        try {
            String mail = req.getParameter("email");
            String password = req.getParameter("password");

            validateLoginInput(mail, password);

            boolean rememberMe = req.getParameter("rememberMe") != null
                    && req.getParameter("rememberMe").equals("true");

            UserDAO userDAO = new UserDAO();

            if (!userDAO.validateUser(mail, password)) {
                throw new AuthenticationException("Invalid email or password");
            }

            User user = userDAO.getUser(mail);

            if (user == null) {
                throw new AuthenticationException("User not found");
            }

            HttpSession oldSession = req.getSession(false);

            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession session = req.getSession(true);

            String csrfToken = UUID.randomUUID().toString();

            session.setAttribute("user", user);
            session.setAttribute("csrfToken", csrfToken);
            session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);

            String csrfCookie = "CSRF_TOKEN=" + csrfToken +
                    "; Path=" + req.getContextPath() +
                    "; Max-Age=" + SESSION_TIMEOUT_SECONDS +
                    "; HttpOnly" +
                    "; SameSite=Strict";

            res.addHeader("Set-Cookie", csrfCookie);

            Cookie emailCookie;

            if (rememberMe) {
                emailCookie = new Cookie("email", mail);
                emailCookie.setMaxAge(REMEMBER_ME_COOKIE_AGE);
            } else {
                emailCookie = new Cookie("email", "");
                emailCookie.setMaxAge(0);
            }

            emailCookie.setPath(req.getContextPath());
            emailCookie.setHttpOnly(true);

            res.addCookie(emailCookie);

            System.out.println("Received login request with email: " + mail);
            System.out.println("User Role : " + user.getRole());
            System.out.println("===== SESSION DETAILS =====");
            System.out.println("Session ID : " + session.getId());
            System.out.println("Creation Time : " + new Date(session.getCreationTime()));
            System.out.println("Last Access Time : " + new Date(session.getLastAccessedTime()));
            System.out.println("Timeout : " + session.getMaxInactiveInterval());
            System.out.println("Is New : " + session.isNew());
            System.out.println("Session User : " + session.getAttribute("user"));
            System.out.println("\n\n\n");

            res.sendRedirect("welcome.jsp");

        } catch (ApplicationException e) {
            redirectWithError(res, e.getMessage());
        }
    }

    private void validateLoginInput(String mail, String password)
            throws ValidationException {

        if (mail == null || mail.isBlank()) {
            throw new ValidationException("Email is required");
        }

        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }

        if (!mail.contains("@")) {
            throw new ValidationException("Invalid email format");
        }
    }

    private void redirectWithError(HttpServletResponse res, String message)
            throws IOException {

        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);

        res.sendRedirect("index.jsp?error=" + encodedMessage);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.sendRedirect("index.jsp");
    }
}