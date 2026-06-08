package com.example.session.api;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/cookies")
public class SetCookies extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Cookie cookie = new Cookie("email","imtharvesh@gmail.com");
        cookie.setMaxAge(60);
        res.addCookie(cookie);

        res.getWriter().println("Cookie set successfully!");
    }
}
