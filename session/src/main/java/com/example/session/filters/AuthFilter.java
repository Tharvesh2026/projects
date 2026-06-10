package com.example.session.filters;

import java.io.IOException;

import com.example.session.model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebFilter("/*")
public class AuthFilter implements Filter {
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String path = req.getServletPath();
        

        if (isPublicPath(path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/index.jsp?error=loginRequired");
            return;
        }

        User user = (User) session.getAttribute("user");
        
        if(path.equals("/settings.jsp") && !"ADMIN".equals(user.getRole())){
            res.sendError(403, "Access Denied");
            return;
        }
        
        chain.doFilter(request, response);
    }


    private boolean isPublicPath(String path) {

        return path.equals("/") ||
                path.equals("/index.jsp") ||
                path.equals("/login") ||
                path.equals("/register") ||
                path.equals("/health") ||
                path.startsWith("/api/*") ||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.startsWith("/assets/");
    }
}