package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.RoleDAO;
import com.example.session.DTO.Req.RoleStatusRequestDTO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.exceptions.ValidationException;
import com.example.session.model.Role;
import com.example.session.util.JsonUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/roles/activate")
public class ActivateRoleApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
            throws IOException {

        try {
            RoleStatusRequestDTO dto =
                    JsonUtil.fromJson(req, RoleStatusRequestDTO.class);

            if (dto.getRoleId() <= 0) {
                throw new ValidationException("Invalid role id");
            }

            RoleDAO dao = new RoleDAO();

            Role role =
                    dao.getRoleById(dto.getRoleId());

            if (role == null) {
                throw new ValidationException("Role not found");
            }

            boolean updated =
                    dao.updateRoleStatus(
                            dto.getRoleId(),
                            "ACTIVE"
                    );

            if (!updated) {
                throw new ValidationException("Role activation failed");
            }

            JsonUtil.writeJson(
                    res,
                    new ApiResponseDTO(
                            true,
                            "Role activated successfully"
                    )
            );

        } catch (ValidationException e) {
            res.setStatus(400);
            JsonUtil.writeJson(res,
                    new ErrorResponseDTO(
                            400,
                            "VALIDATION_ERROR",
                            e.getMessage()));

        } catch (DatabaseException e) {
            res.setStatus(500);
            JsonUtil.writeJson(res,
                    new ErrorResponseDTO(
                            500,
                            "DATABASE_ERROR",
                            e.getMessage()));
        }
    }
}