package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.RoleDAO;
import com.example.session.DTO.Req.RenameRoleRequestDTO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.exceptions.ValidationException;
import com.example.session.model.Role;
import com.example.session.util.JsonUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/roles/rename")
public class RenameRoleApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
            throws IOException {

        try {
            RenameRoleRequestDTO dto =
                    JsonUtil.fromJson(req, RenameRoleRequestDTO.class);

            if (dto.getRoleId() <= 0) {
                throw new ValidationException("Invalid role id");
            }

            if (dto.getRoleName() == null ||
                    dto.getRoleName().isBlank()) {
                throw new ValidationException("Role name is required");
            }

            RoleDAO dao = new RoleDAO();

            Role role =
                    dao.getRoleById(dto.getRoleId());

            if (role == null) {
                throw new ValidationException("Role not found");
            }

            String roleName =
                    dto.getRoleName().trim().toUpperCase();

            boolean updated =
                    dao.renameRole(dto.getRoleId(), roleName);

            if (!updated) {
                throw new ValidationException("Role rename failed");
            }

            JsonUtil.writeJson(
                    res,
                    new ApiResponseDTO(
                            true,
                            "Role renamed successfully"
                    )
            );

        } catch (ValidationException e) {
            res.setStatus(400);
            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            400,
                            "VALIDATION_ERROR",
                            e.getMessage()
                    )
            );

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