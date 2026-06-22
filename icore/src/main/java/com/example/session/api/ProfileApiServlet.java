package com.example.session.api;

import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.DTO.Res.ProfileResponseDTO;
import com.example.session.model.User;
import com.example.session.util.JsonUtil;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/api/profile")
public class ProfileApiServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws IOException {

        HttpSession session =
                req.getSession(false);

        if (session == null ||
                session.getAttribute("user") == null) {

            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            401,
                            "AUTHENTICATION_ERROR",
                            "Login required"
                    )
            );

            return;
        }

        User user =
                (User) session.getAttribute("user");

        ProfileResponseDTO profile =
                new ProfileResponseDTO(
                        user.getId(),
                        user.getName(),
                        user.getUsername(),
                        user.getEmail(),
                        user.getRole(),
                        user.getStatus()
                );

        res.setStatus(HttpServletResponse.SC_OK);

        JsonUtil.writeJson(res, profile);
    }
}