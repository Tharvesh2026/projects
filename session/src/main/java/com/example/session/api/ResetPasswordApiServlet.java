package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.UserDAO;
import com.example.session.DTO.Req.ResetPasswordRequestDTO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.JsonUtil;
import com.example.session.util.PasswordHasher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/reset-password")
public class ResetPasswordApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req,
            HttpServletResponse res)
            throws IOException, ServletException {

        try {
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("user") == null) {
                res.setStatus(401);
                JsonUtil.writeJson(res, new ErrorResponseDTO(401, "AUTHENTICATION_ERROR", "Login required"));
                return;
            }

            User you = (User) session.getAttribute("user");

            if (!"SYS_ADMIN".equals(you.getRole())) {
                res.setStatus(403);
                JsonUtil.writeJson(res, new ErrorResponseDTO(403, "AUTHORIZATION_ERROR", "Access denied"));
                return;
            }

            ResetPasswordRequestDTO dto = JsonUtil.fromJson(req, ResetPasswordRequestDTO.class);

            if (dto.getId() <= 0) {
                res.setStatus(400);
                JsonUtil.writeJson(res, new ErrorResponseDTO(400, "VALIDATION_ERROR", "Invalid user id"));
                return;
            }

            if (dto.getNewPassword() == null || dto.getNewPassword().isBlank()) {
                res.setStatus(400);
                JsonUtil.writeJson(res, new ErrorResponseDTO(400, "VALIDATION_ERROR", "Password is required"));
                return;
            }

            if (dto.getNewPassword().length() < 6) {
                res.setStatus(400);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(400, "VALIDATION_ERROR", "Password must be at least 6 characters"));
                return;
            }

            String hashedPassword = PasswordHasher.hash(dto.getNewPassword());

            UserDAO dao = new UserDAO();

            boolean updated = dao.resetPassword(dto.getId(), hashedPassword);

            if (!updated) {
                res.setStatus(404);
                JsonUtil.writeJson(res, new ErrorResponseDTO(404, "NOT_FOUND", "User not found"));
                return;
            }

            JsonUtil.writeJson(res, new ApiResponseDTO(true, "Password reset successfully"));

        } catch (DatabaseException e) {
            res.setStatus(500);
            JsonUtil.writeJson(res, new ErrorResponseDTO(500, "DATABASE_ERROR", e.getMessage()));
        }
    }
}