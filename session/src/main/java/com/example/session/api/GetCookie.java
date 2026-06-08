package com.example.session.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/getCookies")
public class GetCookie extends HttpServlet{
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
        Cookie[] cookies = req.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies){
                System.out.println("Cookie Name: " + cookie.getName() + ", Cookie Value: " + cookie.getValue());
            }
        }
    }
}
