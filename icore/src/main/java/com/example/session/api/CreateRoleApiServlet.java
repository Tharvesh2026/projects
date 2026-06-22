package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.RoleDAO;
import com.example.session.DTO.Req.CreateRoleRequestDTO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.exceptions.ValidationException;
import com.example.session.util.JsonUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/roles/create")
public class CreateRoleApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
            throws IOException {

        try {

            CreateRoleRequestDTO dto =
                    JsonUtil.fromJson(
                            req,
                            CreateRoleRequestDTO.class
                    );

            if (dto.getRoleName() == null ||
                    dto.getRoleName().isBlank()) {

                throw new ValidationException(
                        "Role name required"
                );
            }

            RoleDAO dao = new RoleDAO();

            boolean created =
                    dao.createRole(
                            dto.getRoleName().trim()
                    );

            if (!created) {

                throw new ValidationException(
                        "Unable to create role"
                );
            }

            JsonUtil.writeJson(
                    res,
                    new ApiResponseDTO(
                            true,
                            "Role created successfully"
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