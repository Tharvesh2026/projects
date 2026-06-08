package com.example.session.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.example.session.util.csrfValidator;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null) {
            res.sendRedirect("index.jsp?error=Session_Expired-Login_Again");
            return;
        }

        String sessionToken = session.getAttribute("X_Secret_Token").toString();
        String RequestToken = req.getParameter("csrfToken");

        boolean valid = csrfValidator.validateCSRF(sessionToken, RequestToken);

        if (!valid) {
            res.sendError(403,"Invalid CSRF Token");
            return;
        }

        session.invalidate();

        res.sendRedirect("index.jsp?logout=SUCESS");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.sendRedirect("index.jsp");
    }
    
}