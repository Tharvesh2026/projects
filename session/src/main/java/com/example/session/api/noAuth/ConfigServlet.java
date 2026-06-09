package com.example.session.api.noAuth;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;


@WebServlet("/api/config")
public class ConfigServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        res.getWriter().write("""
        {
            "version":"1.0.0",
            "maintenance":false
        }
        """);
    }
}