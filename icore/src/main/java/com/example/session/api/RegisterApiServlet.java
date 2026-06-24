package com.example.session.api;

import com.example.session.model.User;
import com.example.session.DAO.UserDAO;
import com.example.session.DTO.Req.RegisterRequestDTO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.util.JsonUtil;
import com.example.session.exceptions.*;
import com.example.session.util.PasswordHasher;
import com.example.session.util.ValidationDAOUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/user/register")

public class RegisterApiServlet extends HttpServlet {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            RegisterRequestDTO dto = JsonUtil.fromJson(req, RegisterRequestDTO.class);
            String name = ValidationDAOUtil.sanitizeName(dto.getName());
            String mail = dto.getEmail();
            String username = ValidationDAOUtil.sanitizeUsername(dto.getUsername());
            String password = (dto.getPassword());
            validateRegisterInput(name, mail, username, password);

            String hashPwd = PasswordHasher.hash(password);

            User user = new User(username, mail, hashPwd, name, "USER", "ACTIVE");

            UserDAO dao = new UserDAO();
            boolean regStatus = dao.registerUser(user);

            if (regStatus) {
                JsonUtil.writeJson(res, new ApiResponseDTO(true, "Registration  sucessful, Please Login"));
                return;
            }
            throw new ValidationException("Registration failed");

        } catch (ValidationException e) {

            res.setStatus(400);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            400,
                            "VALIDATION_ERROR",
                            e.getMessage()));
        } catch (DatabaseException e) {

            res.setStatus(500);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            500,
                            "DATABASE_ERROR",
                            e.getMessage()));
        }
    }

    private void validateRegisterInput(String name,
            String email,
            String username,
            String password)
            throws ValidationException {

        if (name == null || name.isBlank()) {
            throw new ValidationException("Name is required");
        }

        if (username == null || username.isBlank()) {
            throw new ValidationException("Username is required");
        }

        if (email == null || email.isBlank()) {
            throw new ValidationException("Email is required");
        }

        if (!isValidEmail(email)) {
            throw new ValidationException("Invalid email format");
        }

        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }

        if (password.length() < 6) {
            throw new ValidationException("Password must be at least 6 characters");
        }
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

}
