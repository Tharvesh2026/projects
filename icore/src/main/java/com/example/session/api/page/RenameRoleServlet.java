package com.example.session.api.page;

import java.io.IOException;

import com.example.session.DAO.RoleDAO;
import com.example.session.exceptions.DatabaseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/roles/rename")
public class RenameRoleServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
            throws ServletException, IOException {

        try {
            int roleId =
                    Integer.parseInt(req.getParameter("roleId"));

            String roleName =
                    req.getParameter("roleName");

            if (roleName == null || roleName.isBlank()) {
                res.sendRedirect(
                        req.getContextPath()
                                + "/roles?error=roleNameRequired"
                );
                return;
            }

            roleName =
                    roleName.trim().toUpperCase();

            RoleDAO dao =
                    new RoleDAO();

            dao.renameRole(
                    roleId,
                    roleName
            );

            res.sendRedirect(
                    req.getContextPath()
                            + "/roles?success=roleRenamed"
            );

        } catch (NumberFormatException e) {

            res.sendRedirect(
                    req.getContextPath()
                            + "/roles?error=invalidRole"
            );

        } catch (DatabaseException e) {

            throw new ServletException(
                    e.getMessage(),
                    e
            );
        }
    }
}