package com.example.session.api.auth;

import com.example.session.DAO.UserDAO;
import com.example.session.model.User;
import com.example.session.exceptions.DatabaseException;

import java.util.*;
import jakarta.servlet.annotation.WebServlet;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/users")
public class UsersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws ServletException, IOException {

        try {

            UserDAO dao = new UserDAO();

            List<User> users =
                    dao.getAllUsers();

            req.setAttribute(
                    "users",
                    users
            );

            req.getRequestDispatcher(
                    "/users.jsp"
            ).forward(req, res);

        } catch (DatabaseException e) {

            throw new ServletException(
                    e.getMessage()
            );
        }
    }
}
