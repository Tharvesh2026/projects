package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.RoleDAO;
import com.example.session.DAO.UserDAO;
import com.example.session.DTO.Req.UpdateRoleRequestDTO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.exceptions.ResourceNotFoundException;
import com.example.session.exceptions.ValidationException;
import com.example.session.model.User;
import com.example.session.model.Role;
import com.example.session.util.JsonUtil;
import com.example.session.util.PermissionValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/update-role")
public class UpdateRoleApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse res)
            throws IOException, ServletException {

        try {
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("user") == null) {
                res.setStatus(401);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(401, "AUTHENTICATION_ERROR", "Login required"));
                return;
            }

            User you = (User) session.getAttribute("user");

            if (!PermissionValidator.hasPermission(you.getId(), "ROLE_UPDATE")) {
                res.setStatus(403);
                JsonUtil.writeJson(res, new ErrorResponseDTO(403, "AUTHORIZATION_ERROR", "Access Denied"));
                return;
            }

            UpdateRoleRequestDTO dto = JsonUtil.fromJson(req, UpdateRoleRequestDTO.class);

            if (dto.getId() <= 0) {
                res.setStatus(400);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(400, "VALIDATION_ERROR", "Invalid user id"));
                return;
            }

            if (dto.getRoleId() <= 0) {

                res.setStatus(400);

                JsonUtil.writeJson(
                        res,
                        new ErrorResponseDTO(
                                400,
                                "VALIDATION_ERROR",
                                "Invalid role id"));

                return;
            }

            UserDAO dao = new UserDAO();
            RoleDAO rdao = new RoleDAO();
            Role role = rdao.getRoleById(dto.getRoleId());

            boolean updated = dao.updateUserRole(dto.getId(), dto.getRoleId());

            if (!updated) {
                res.setStatus(404);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(404, "NOT_FOUND", "User not found"));
                return;
            }

            if(role == null){
                throw new ResourceNotFoundException("Role Not Found");
            }

            JsonUtil.writeJson(res,
                    new ApiResponseDTO(true, "User role updated successfully"));

        } catch (DatabaseException e) {
            res.setStatus(500);
            JsonUtil.writeJson(res,
                    new ErrorResponseDTO(500, "DATABASE_ERROR", e.getMessage()));
        }
        catch (ResourceNotFoundException e) {

            res.setStatus(404);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            404,
                            "RESOURCE_NOT_FOUND",
                            e.getMessage()));
        }
    }
}