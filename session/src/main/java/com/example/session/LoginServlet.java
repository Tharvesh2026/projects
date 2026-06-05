package com.example.session;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")

public class LoginServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String mail = req.getParameter("email");
        String pwd = req.getParameter("password");

        System.out.println("Received login request with email: " + mail);

        UserDAO userDAO = new UserDAO();
        if (userDAO.validateUser(mail, pwd)) {
            
            User user = userDAO.getUser(mail, pwd);
            System.err.println("USER OBJECT = " + user);

            HttpSession session = req.getSession();
            session.setAttribute("user", user);
            System.err.println("SESSION ID = " + session.getId());

            System.err.println("SESSION USER = " + session.getAttribute("user"));
            res.sendRedirect("welcome.jsp");

        } else {
            res.sendRedirect("login.jsp?error=Invalid email or password");
        }
    }

    // public void doPost(HttpServletRequest req, HttpServletResponse res) throws
    // IOException {

    // String mail = req.getParameter("email");
    // String pwd = req.getParameter("password");

    // System.err.println("LOGIN SERVLET HIT");
    // System.err.println("EMAIL = " + mail);
    // System.err.println("PASSWORD = " + pwd);

    // UserDAO userDAO = new UserDAO();

    // boolean valid = userDAO.validateUser(mail, pwd);

    // System.err.println("VALID USER = " + valid);

    // if(valid) {

    // User user = userDAO.getUser(mail, pwd);

    // System.err.println("USER OBJECT = " + user);

    // HttpSession session = req.getSession();

    // session.setAttribute("user", user);

    // System.err.println("SESSION ID = " + session.getId());

    // System.err.println("SESSION USER = " +
    // session.getAttribute("user"));

    // res.sendRedirect("welcome.jsp");

    // } else {

    // System.err.println("LOGIN FAILED");

    // res.sendRedirect("login.jsp?error=Invalid email or password");
    // }
    // }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.sendRedirect("login.jsp");
    }
}
