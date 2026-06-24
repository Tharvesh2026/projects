package com.example.session.api;

import java.io.IOException;

import com.example.session.DAO.UserDAO;
import com.example.session.DTO.Req.UpdateProfileRequestDTO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.exceptions.ValidationException;
import com.example.session.model.User;
import com.example.session.util.JsonUtil;
import com.example.session.util.ValidationDAOUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/profile/update")
public class UpdateProfileApiServlet extends HttpServlet {

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

            UpdateProfileRequestDTO dto = JsonUtil.fromJson(req, UpdateProfileRequestDTO.class);
            
            String name = ValidationDAOUtil.sanitizeName(dto.getName());
            String email = dto.getEmail();
            String username = ValidationDAOUtil.sanitizeUsername(dto.getUsername());

            validateProfile(name, username, email);

            UserDAO dao = new UserDAO();

            boolean updated = dao.updateProfile(user.getId(), name, username, email);

            if (!updated) {
                throw new ValidationException("Profile update failed");
            }

            User refreshed = new User(
                    username,
                    email,
                    user.getPasswordHash(),
                    name,
                    user.getRole(),
                    user.getStatus(),
                    user.getId());

            session.setAttribute("user", refreshed);

            JsonUtil.writeJson(res,
                    new ApiResponseDTO(true, "Profile updated successfully"));

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

    private void validateProfile(String name, String username, String email)
            throws ValidationException {

        if (name.isBlank() || username.isBlank() || email.isBlank()) {
            throw new ValidationException("All fields are required");
        }

        if (!email.matches("^[\\w.+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")) {
            throw new ValidationException("Invalid email address");
        }

        if (username.length() < 3 || username.length() > 30) {
            throw new ValidationException("Username must be 3-30 characters");
        }

        if (!username.matches("^[a-z0-9_]+$")) {
            throw new ValidationException(
                    "Username may only contain lowercase letters, numbers and underscores");
        }
    }
}