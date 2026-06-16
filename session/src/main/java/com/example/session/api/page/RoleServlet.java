package com.example.session.api.page;

import java.io.IOException;
import java.util.List;

import com.example.session.DAO.RoleDAO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.model.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/roles")
public class RoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws ServletException, IOException {

        try {
            RoleDAO roleDAO = new RoleDAO();
            List<Role> roles = roleDAO.getAllRoles();

            req.setAttribute("roles", roles);

            req.getRequestDispatcher("/roles.jsp")
                    .forward(req, res);

        } catch (DatabaseException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
            throws ServletException, IOException {

        try {
            String roleName = req.getParameter("roleName");

            if (roleName == null || roleName.isBlank()) {
                res.sendRedirect(req.getContextPath() + "/roles?error=roleNameRequired");
                return;
            }

            roleName = roleName.trim().toUpperCase();

            RoleDAO roleDAO = new RoleDAO();
            roleDAO.createRole(roleName);

            res.sendRedirect(req.getContextPath() + "/roles?success=roleCreated");

        } catch (DatabaseException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
}