package com.example.session.api;

import com.example.session.model.User;
import com.example.session.DAO.UserDAO;
import com.example.session.DTO.LoginRequestDTO;
import com.example.session.DTO.ApiResponseDTO;
import com.example.session.util.JsonUtil;
import com.example.session.exceptions.DatabaseException;

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

           validateInput(res,email,password);

            UserDAO userDAO = new UserDAO();
            boolean valid = userDAO.validateUser(email, password);
            if (!valid) {
                JsonUtil.writeJson(res, new ApiResponseDTO(false, "Invalid Email Password"));
                return;
            }

            User user = userDAO.getUser(email);
            HttpSession session = req.getSession(false);

            if (session != null) {
                session.invalidate();
            }

            session = req.getSession(true);
            session.setAttribute("user", user);
            session.setAttribute("csrfToken", UUID.randomUUID().toString());

            JsonUtil.writeJson(res, new ApiResponseDTO(true, "Login Sucessfull"));

        } catch (DatabaseException e) {

            JsonUtil.writeJson(
                    res,
                    new ApiResponseDTO(
                            false,
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

    private void validateInput( HttpServletResponse res, String email, String password)throws IOException{
         if (email == null || email.isBlank()) {
                JsonUtil.writeJson(res, new ApiResponseDTO(false, "Email Required"));
                return;
            }

            if (!isValidEmail(email)) {
                JsonUtil.writeJson(res, new ApiResponseDTO(false, "Enter Valid email address"));
                return;
            }


            if (password == null || password.isBlank()) {
                JsonUtil.writeJson(res, new ApiResponseDTO(false, "password Required"));
                return;
            }
    }
}