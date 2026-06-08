package com.example.session.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

import java.io.IOException;
import java.util.Date;

import com.example.session.DAO.UserDAO;
import com.example.session.model.User;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")

public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String mail = req.getParameter("email");
        String pwd = req.getParameter("password");
        Boolean remMe = req.getParameter("rememberMe") != null && req.getParameter("rememberMe").equals("true");


        System.out.println("Received login request with email: " + mail);

        UserDAO userDAO = new UserDAO();
        if (userDAO.validateUser(mail, pwd)) {
            
            User user = userDAO.getUser(mail, pwd);
            System.err.println("USER OBJECT = " + user);

            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            if(remMe){
                Cookie cookieValue = CreateCookie("email", mail, 60);
                res.addCookie(cookieValue);       
            }
            System.err.println("SESSION ID = " + session.getId());

            System.err.println("SESSION USER = " + session.getAttribute("user"));
            res.sendRedirect("welcome.jsp");

            // HttpSession session = request.getSession();

            System.out.println("===== SESSION DETAILS =====");
            System.out.println("Session ID : " + session.getId());
            System.out.println("Creation Time : " + new Date(session.getCreationTime()));
            System.out.println("Last Access Time : " + new Date(session.getLastAccessedTime()));
            System.out.println("Timeout : " + session.getMaxInactiveInterval());
            System.out.println("Is New : " + session.isNew());

        } else {
            res.sendRedirect("login.jsp?error=Invalid email or password");
        }
    }

    public Cookie CreateCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/session");
        return cookie;
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendRedirect("login.jsp");
    }
}
