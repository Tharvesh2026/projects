package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.PermissionDAO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.JsonUtil;
import com.example.session.util.PermissionValidator;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/roles/update-permissions")
public class UpdateRolePermissionsApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
            throws IOException {

        try {

            HttpSession session =
                    req.getSession(false);

            if (session == null ||
                    session.getAttribute("user") == null) {

                res.setStatus(401);

                JsonUtil.writeJson(
                        res,
                        new ErrorResponseDTO(
                                401,
                                "AUTHENTICATION_ERROR",
                                "Login Required"
                        )
                );

                return;
            }

            User user =
                    (User) session.getAttribute("user");

            if (!PermissionValidator.hasPermission(
                    user.getId(),
                    "ROLE_PERMISSION_MANAGE")) {

                res.setStatus(403);

                JsonUtil.writeJson(
                        res,
                        new ErrorResponseDTO(
                                403,
                                "AUTHORIZATION_ERROR",
                                "Access Denied"
                        )
                );

                return;
            }

            int roleId =
                    Integer.parseInt(
                            req.getParameter("roleId")
                    );

            String[] permissionIds =
                    req.getParameterValues(
                            "permissionIds"
                    );

            PermissionDAO dao =
                    new PermissionDAO();

            dao.updateRolePermissions(
                    roleId,
                    permissionIds
            );

            res.sendRedirect(
                req.getContextPath()
                        + "/manage-role?id="
                        + roleId
                        + "&success=permissionsUpdated"
        );
        return;

        } catch (DatabaseException e) {

            res.setStatus(500);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            500,
                            "DATABASE_ERROR",
                            e.getMessage()
                    )
            );
        }
    }
}