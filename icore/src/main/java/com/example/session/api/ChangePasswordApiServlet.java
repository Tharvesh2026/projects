package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.UserDAO;
import com.example.session.DTO.Req.ChangePasswordRequestDTO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.exceptions.ValidationException;
import com.example.session.model.User;
import com.example.session.util.JsonUtil;
import com.example.session.util.PasswordHasher;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/profile/change-password")
public class ChangePasswordApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
                          HttpServletResponse res)
            throws IOException {

        try {
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("user") == null) {
                res.setStatus(401);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(401, "AUTHENTICATION_ERROR", "Login required"));
                return;
            }

            User user = (User) session.getAttribute("user");

            ChangePasswordRequestDTO dto =
                    JsonUtil.fromJson(req, ChangePasswordRequestDTO.class);

            validatePassword(dto);

            UserDAO dao = new UserDAO();

            boolean valid =
                    dao.verifyPassword(user.getId(), dto.getCurrentPassword());

            if (!valid) {
                throw new ValidationException("Current password is incorrect");
            }

            String newHash =
                    PasswordHasher.hash(dto.getNewPassword());

            boolean changed =
                    dao.resetPassword(user.getId(), newHash);

            if (!changed) {
                throw new ValidationException("Password update failed");
            }

            JsonUtil.writeJson(res,
                    new ApiResponseDTO(true, "Password changed successfully"));

        } catch (ValidationException e) {
            res.setStatus(400);
            JsonUtil.writeJson(res,
                    new ErrorResponseDTO(400, "VALIDATION_ERROR", e.getMessage()));

        } catch (DatabaseException e) {
            res.setStatus(500);
            JsonUtil.writeJson(res,
                    new ErrorResponseDTO(500, "DATABASE_ERROR", e.getMessage()));
        }
    }

    private void validatePassword(ChangePasswordRequestDTO dto)
            throws ValidationException {

        if (isBlank(dto.getCurrentPassword()) ||
                isBlank(dto.getNewPassword()) ||
                isBlank(dto.getConfirmPassword())) {

            throw new ValidationException("All password fields are required");
        }

        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            throw new ValidationException("New passwords do not match");
        }

        if (dto.getNewPassword().length() < 6) {
            throw new ValidationException("New password must be at least 6 characters");
        }

        if (dto.getNewPassword().equals(dto.getCurrentPassword())) {
            throw new ValidationException("New password must differ from current password");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}