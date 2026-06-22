package com.example.session.api.noAuth;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;


@WebServlet("/api/language")
public class LanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,
                         HttpServletResponse res)
            throws IOException {

        res.setContentType("application/json");

        res.getWriter().write("""
        {
            "default":"en",
            "supported":["en","ta","hi"]
        }
        """);
    }
}