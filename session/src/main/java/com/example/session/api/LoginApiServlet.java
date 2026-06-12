package com.example.session.api;

import com.example.session.model.User;
import com.example.session.DAO.UserDAO;
import com.example.session.DTO.LoginRequestDTO;
import com.example.session.DTO.ApiResponseDTO;
import com.example.session.DTO.ErrorResponseDTO;
import com.example.session.util.JsonUtil;
import com.example.session.exceptions.AuthenticationException;
import com.example.session.exceptions.DatabaseException;
import com.example.session.exceptions.ValidationException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/user/login")
public class LoginApiServlet extends HttpServlet {
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {
        LoginRequestDTO dto = JsonUtil.fromJson(req, LoginRequestDTO.class);

        try {
            String email = dto.getEmail();
            String password = dto.getPassword();

            validateInput(email, password);

            UserDAO userDAO = new UserDAO();
            boolean valid = userDAO.validateUser(email, password);
            if (!valid) {
                throw new AuthenticationException(
                        "Invalid Email or Password");
            }

            User user = userDAO.getUser(email);
            if (user == null) {
                throw new AuthenticationException("User not found");
            }
            HttpSession session = req.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            session = req.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("csrfToken", UUID.randomUUID().toString());

            JsonUtil.writeJson(res, new ApiResponseDTO(true, "Login Sucessful"));

        } catch (DatabaseException e) {

            res.setStatus(500);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            500,
                            "DATABASE_ERROR",
                            e.getMessage()));
        } catch (ValidationException e) {

            res.setStatus(400);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            400,
                            "VALIDATION_ERROR",
                            e.getMessage()));
        } catch (AuthenticationException e) {

            res.setStatus(401);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            401,
                            "AUTHENTICATION_ERROR",
                            e.getMessage()));
        }

    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        return matcher.matches();
    }

    private void validateInput(String email, String password)
            throws ValidationException {

        if (email == null || email.isBlank()) {
            throw new ValidationException("Email is required");
        }

        if (!isValidEmail(email)) {
            throw new ValidationException("Enter valid email address");
        }

        if (password == null || password.isBlank()) {
            throw new ValidationException("Password is required");
        }
    }
}