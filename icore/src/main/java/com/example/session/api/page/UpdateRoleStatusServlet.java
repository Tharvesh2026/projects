package com.example.session.api.page;

import java.io.IOException;

import com.example.session.DAO.RoleDAO;
import com.example.session.exceptions.DatabaseException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/roles/status")
public class UpdateRoleStatusServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
            throws ServletException, IOException {

        try {
            int roleId =
                    Integer.parseInt(req.getParameter("roleId"));

            String status =
                    req.getParameter("status");

            if (!"ACTIVE".equals(status)
                    && !"INACTIVE".equals(status)) {

                res.sendRedirect(
                        req.getContextPath()
                                + "/roles?error=invalidStatus"
                );
                return;
            }

            RoleDAO dao =
                    new RoleDAO();

            dao.updateRoleStatus(
                    roleId,
                    status
            );

            res.sendRedirect(
                    req.getContextPath()
                            + "/roles?success=statusUpdated"
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