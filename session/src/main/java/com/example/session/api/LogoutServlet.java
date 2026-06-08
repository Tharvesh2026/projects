package com.example.session.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Cookie;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,HttpServletResponse res)throws IOException {

        HttpSession session = req.getSession(false);

        if(session != null && session.getAttribute("user") != null){
            session.invalidate();
        }
        
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                if(cookie.getName().equals("email")){
                    cookie.setValue("");
                    cookie.setMaxAge(0);
                    cookie.setPath("/session");
                    res.addCookie(cookie);
                }
            }
        }

        res.sendRedirect("index.jsp");
    }
}