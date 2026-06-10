package com.example.session.api.auth;

import com.example.session.DAO.UserDAO;
import com.example.session.model.User;
import com.example.session.util.PasswordHasher;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String hashedPassword = PasswordHasher.hash(password);

        User user = new User(
                username,
                email,
                hashedPassword,
                name,
            "USER");

        UserDAO userDAO = new UserDAO();

        boolean registered = userDAO.registerUser(user);

        if (registered) {
            res.sendRedirect("index.jsp?success=Registration successful. Please login.");
        } else {
            res.sendRedirect("index.jsp?error=Registration failed.");
        }
    }
}