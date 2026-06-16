package com.example.session.api.page;

import java.io.IOException;
import java.util.List;

import com.example.session.DAO.PermissionDAO;
import com.example.session.DAO.RoleDAO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.model.Permission;
import com.example.session.model.Role;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/manage-role")
public class ManageRoleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws ServletException, IOException {

        try {
            int roleId =
                    Integer.parseInt(req.getParameter("id"));

            RoleDAO roleDAO = new RoleDAO();
            PermissionDAO permissionDAO = new PermissionDAO();

            Role role = roleDAO.getRoleById(roleId);

            if (role == null) {
                res.sendRedirect(
                        req.getContextPath()
                                + "/roles?error=roleNotFound"
                );
                return;
            }

            List<Permission> permissions =
                    permissionDAO.getAllPermissions();

            List<Integer> assignedPermissionIds =
                    permissionDAO.getPermissionIdsByRoleId(roleId);

            req.setAttribute("role", role);
            req.setAttribute("permissions", permissions);
            req.setAttribute("assignedPermissionIds", assignedPermissionIds);

            req.getRequestDispatcher("/manage-role.jsp")
                    .forward(req, res);

        } catch (NumberFormatException e) {
            res.sendRedirect(
                    req.getContextPath()
                            + "/roles?error=invalidRole"
            );

        } catch (DatabaseException e) {
            throw new ServletException(e.getMessage(), e);
        }
    }
}