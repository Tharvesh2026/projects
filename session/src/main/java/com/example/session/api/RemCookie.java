package com.example.session.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/removeCookie")
public class RemCookie extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        Cookie cookie = new Cookie("email", "");
        cookie.setMaxAge(0);
        res.addCookie(cookie);

        res.getWriter().println("Cookie removed successfully!");
    }
}
