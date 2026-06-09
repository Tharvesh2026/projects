package com.example.session.filters;

import java.io.IOException;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebFilter({
    "/settings.jsp",
    "/welcome.jsp"
})
public class AuthFilter implements Filter{
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException{
        HttpServletRequest req =(HttpServletRequest)request;
        HttpServletResponse res =(HttpServletResponse)response;
        HttpSession session = req.getSession();

        if(session == null || session.getAttribute("user")==null){
            res.sendRedirect("index.jsp?error=loginFailed");
            return;
        }
        chain.doFilter(request, response);
    }    
}
