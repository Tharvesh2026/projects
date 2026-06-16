package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.UserDAO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.JsonUtil;
import com.example.session.util.PermissionValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/user")
public class UserByIdApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
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
            if (!PermissionValidator.hasPermission(you.getId(),"USER_READ")){
                res.setStatus(403);
                JsonUtil.writeJson(res, new ErrorResponseDTO(403, "AUTHORIZATION_ERROR", "Access Denied"));
                return;
            }

            String idValue = req.getParameter("id");

            if (idValue == null || idValue.isBlank()) {
                res.setStatus(400);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(400, "VALIDATION_ERROR", "User id is required"));
                return;
            }

            int id = Integer.parseInt(idValue);

            UserDAO dao = new UserDAO();
            User user = dao.getUserById(id);

            if (user == null) {
                res.setStatus(404);
                JsonUtil.writeJson(res,
                        new ErrorResponseDTO(404, "NOT_FOUND", "User not found"));
                return;
            }

            JsonUtil.writeJson(res,
                    new ApiResponseDTO(true, "User fetched successfully", user));

        } catch (NumberFormatException e) {
            res.setStatus(400);
            JsonUtil.writeJson(res,
                    new ErrorResponseDTO(400, "VALIDATION_ERROR", "Invalid user id"));

        } catch (DatabaseException e) {
            res.setStatus(500);
            JsonUtil.writeJson(res,
                    new ErrorResponseDTO(500, "DATABASE_ERROR", e.getMessage()));
        }
    }
}