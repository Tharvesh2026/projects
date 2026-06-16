package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.UserDAO;
import com.example.session.DTO.Req.UpdateStatusRequestDTO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.JsonUtil;
import com.example.session.util.PermissionValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/update-status")
public class UpdateStatusApiServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
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

             if (!PermissionValidator.hasPermission(you.getId(),"USER_UPDATE")){
                res.setStatus(403);
                JsonUtil.writeJson(res, new ErrorResponseDTO(403, "AUTHORIZATION_ERROR", "Access Denied"));
                return;
            }

            UpdateStatusRequestDTO dto = JsonUtil.fromJson(req, UpdateStatusRequestDTO.class);

            if (dto.getId() <= 0) {
                res.setStatus(400);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(400, "VALIDATION_ERROR", "Invalid user id"));
                return;
            }

            if (!isValidStatus(dto.getStatus())) {
                res.setStatus(400);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(400, "VALIDATION_ERROR", "Invalid status"));
                return;
            }

            UserDAO dao = new UserDAO();

            boolean updated = dao.updateUserStatus(dto.getId(), dto.getStatus());

            if (!updated) {
                res.setStatus(404);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(404, "NOT_FOUND", "User not found"));
                return;
            }

            JsonUtil.writeJson(res,
                    new ApiResponseDTO(true, "User status updated successfully"));

        } catch (DatabaseException e) {
            res.setStatus(500);
            JsonUtil.writeJson(res,
                    new ErrorResponseDTO(500, "DATABASE_ERROR", e.getMessage()));
        }
    }

    private boolean isValidStatus(String status) {
        return "ACTIVE".equals(status) ||
                "DISABLED".equals(status) ||
                "LOCKED".equals(status);
    }
}