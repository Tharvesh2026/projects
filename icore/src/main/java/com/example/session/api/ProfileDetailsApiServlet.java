package com.example.session.api;

import java.io.IOException;

import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.model.User;
import com.example.session.util.JsonUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/auth/profile")
public class ProfileDetailsApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.setStatus(401);
            JsonUtil.writeJson(res,
                    new ErrorResponseDTO(401, "AUTHENTICATION_ERROR", "Login required"));
            return;
        }

        User user = (User) session.getAttribute("user");

        JsonUtil.writeJson(res,
                new ApiResponseDTO(true, "Profile fetched successfully", user));
    }
}