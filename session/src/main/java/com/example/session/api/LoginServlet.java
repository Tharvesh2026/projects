package com.example.session.api;

import com.example.session.DAO.UserDAO;
import com.example.session.model.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final int SESSION_TIMEOUT_SECONDS = 600;
    private static final int REMEMBER_ME_COOKIE_AGE = 7 * 24 * 60 * 60;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String mail = req.getParameter("email");
        String password = req.getParameter("password");

        boolean rememberMe = req.getParameter("rememberMe") != null && req.getParameter("rememberMe").equals("true");

        System.out.println("Received login request with email: " + mail);

        UserDAO userDAO = new UserDAO();

        if (userDAO.validateUser(mail, password)) {

            User user = userDAO.getUser(mail);

            HttpSession oldSession = req.getSession(false);

            if (oldSession != null) {
                oldSession.invalidate();
            }

            HttpSession session = req.getSession(true);

            String csrfToken = UUID.randomUUID().toString();

            session.setAttribute("user", user);
            session.setAttribute("csrfToken", csrfToken);
            session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);

            String csrfCookie =
                    "CSRF_TOKEN=" + csrfToken +
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

            System.out.println("===== SESSION DETAILS =====");
            System.out.println("Session ID : " + session.getId());
            System.out.println("Creation Time : " + new Date(session.getCreationTime()));
            System.out.println("Last Access Time : " + new Date(session.getLastAccessedTime()));
            System.out.println("Timeout : " + session.getMaxInactiveInterval());
            System.out.println("Is New : " + session.isNew());
            System.out.println("Session User : " + session.getAttribute("user"));

            res.sendRedirect("welcome.jsp");

        } else {

            res.sendRedirect("index.jsp?error=Invalid email or password");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.sendRedirect("index.jsp");
    }
}