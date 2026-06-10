package com.example.session.api.auth;

import com.example.session.DAO.UserDAO;
import com.example.session.model.User;
import com.example.session.util.PasswordHasher;
import com.example.session.exceptions.ApplicationException;
import com.example.session.exceptions.ValidationException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {

            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String username = req.getParameter("username");
            String password = req.getParameter("password");

            validateRegisterInput(name, email, username, password);

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
                String msg = URLEncoder.encode(
                        "Registration successful. Please login.",
                        StandardCharsets.UTF_8);
                res.sendRedirect("index.jsp?success=" + msg);
            } else {
                throw new ValidationException("Registration failed");
            }
        } catch (ApplicationException e) {
            String msg = URLEncoder.encode(
                    e.getMessage(),
                    StandardCharsets.UTF_8);

            res.sendRedirect("index.jsp?error=" + msg);
        }
    }
 private void validateRegisterInput(String name,
                                       String email,
                                       String username,
                                       String password)
            throws ValidationException {

        if (name == null || name.isBlank()) {
            throw new ValidationException("Name is required");
        }

        if (username == null || username.isBlank()) {
            throw new ValidationException("Username is required");
        }

        if (email == null || email.isBlank()) {
            throw new ValidationException("Email is required");
        }

        if (!email.contains("@")) {
            throw new ValidationException("Invalid email format");
        }

        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }

        if (password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters");
        }
    }
}