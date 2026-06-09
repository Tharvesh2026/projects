package com.example.session.api.noAuth;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet("/api/theme")
public class ThemeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        res.getWriter().write("""
        {
            "theme":"dark",
            "primaryColor":"#2c3e50"
        }
        """);
    }
}