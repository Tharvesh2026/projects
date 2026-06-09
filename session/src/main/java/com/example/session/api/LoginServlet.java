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
        boolean rememberMe = isRememberMeChecked(req);

        UserDAO userDAO = new UserDAO();

        if (!userDAO.validateUser(mail, password)) {
            redirectToLoginWithError(res);
            return;
        }

        User user = userDAO.getUser(mail);

        HttpSession session = createFreshSession(req);
        configureSession(session, user);

        handleRememberMeCookie(req, res, mail, rememberMe);

        logSessionDetails(session);

        res.sendRedirect("welcome.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.sendRedirect("index.jsp");
    }

    private boolean isRememberMeChecked(HttpServletRequest req) {
        String rememberMeValue = req.getParameter("rememberMe");

        return rememberMeValue != null &&
                rememberMeValue.equals("true");
    }

    private HttpSession createFreshSession(HttpServletRequest req) {
        HttpSession oldSession = req.getSession(false);

        if (oldSession != null) {
            oldSession.invalidate();
        }

        return req.getSession(true);
    }

    private void configureSession(HttpSession session, User user) {
        session.setAttribute("user", user);
        session.setAttribute("csrfToken", generateCSRFToken());
        session.setMaxInactiveInterval(SESSION_TIMEOUT_SECONDS);
    }

    private void handleRememberMeCookie(HttpServletRequest req,
            HttpServletResponse res,
            String mail,
            boolean rememberMe) {

        Cookie emailCookie;

        if (rememberMe) {
            emailCookie = createCookie(
                    "email",
                    mail,
                    REMEMBER_ME_COOKIE_AGE,
                    req);
        } else {
            emailCookie = createCookie(
                    "email",
                    "",
                    0,
                    req);
        }

        res.addCookie(emailCookie);
    }

    private Cookie createCookie(String name,
            String value,
            int maxAge,
            HttpServletRequest req) {

        Cookie cookie = new Cookie(name, value);

        cookie.setMaxAge(maxAge);
        cookie.setPath(req.getContextPath());
        cookie.setHttpOnly(true);

        return cookie;
    }

    private String generateCSRFToken() {
        return UUID.randomUUID().toString();
    }

    private void redirectToLoginWithError(HttpServletResponse res)
            throws IOException {

        res.sendRedirect("index.jsp?error=Invalid email or password");
    }

    private void logSessionDetails(HttpSession session) {
        System.out.println("===== SESSION DETAILS =====");
        System.out.println("Session ID : " + session.getId());
        System.out.println("Creation Time : " + new Date(session.getCreationTime()));
        System.out.println("Last Access Time : " + new Date(session.getLastAccessedTime()));
        System.out.println("Timeout : " + session.getMaxInactiveInterval());
        System.out.println("Is New : " + session.isNew());
        System.out.println("Session User : " + session.getAttribute("user"));
    }
}