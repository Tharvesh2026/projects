package com.example.session.api.auth;

import com.example.session.DAO.UserDAO;
import com.example.session.model.User;
import com.example.session.exceptions.ApplicationException;
import com.example.session.exceptions.AuthenticationException;
import com.example.session.exceptions.ValidationException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(LoginServlet.class);
    private static final int SESSION_TIMEOUT_SECONDS = 600;
    private static final int REMEMBER_ME_COOKIE_AGE = 7 * 24 * 60 * 60;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

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

            logger.info("Received login request with email: {}", mail);
            logger.info("User Role : {}", user.getRole());

            logger.info("===== SESSION DETAILS =====");
            logger.info("Session ID : {}", session.getId());
            logger.info("Creation Time : {}", new Date(session.getCreationTime()));
            logger.info("Last Access Time : {}", new Date(session.getLastAccessedTime()));
            logger.info("Timeout : {}", session.getMaxInactiveInterval());
            logger.info("Is New : {}", session.isNew());
            logger.info("Session User : {}", session.getAttribute("user"));
            logger.info("=============================");

            res.sendRedirect("welcome");

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