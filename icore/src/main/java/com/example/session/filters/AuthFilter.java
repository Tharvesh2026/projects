package com.example.session.filters;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.PermissionValidator;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthFilter.class);

    @Override
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
            logger.warn(
                    "AUTH_REQUIRED | method={} | endpoint={} | ip={}",
                    req.getMethod(),
                    path,
                    req.getRemoteAddr());
            res.sendRedirect(req.getContextPath() + "/index.jsp?error=loginRequired");
            return;
        }

        User user = (User) session.getAttribute("user");

        String requiredPermission = getRequiredPermission(path);

        if (requiredPermission != null) {

            try {
                boolean allowed = PermissionValidator.hasPermission(
                        user.getId(),
                        requiredPermission);

                if (!allowed) {

                    logger.warn(
                            "ACCESS_DENIED | method={} | endpoint={} | user={} | userId={} | permission={} | ip={}",
                            req.getMethod(),
                            path,
                            user.getUsername(),
                            user.getId(),
                            requiredPermission,
                            req.getRemoteAddr());

                    res.sendError(
                            HttpServletResponse.SC_FORBIDDEN,
                            "Access Denied");
                    return;
                }

                logger.info(
                        "ACCESS_ALLOWED | method={} | endpoint={} | user={} | userId={} | permission={} | ip={}",
                        req.getMethod(),
                        path,
                        user.getUsername(),
                        user.getId(),
                        requiredPermission,
                        req.getRemoteAddr());

            } catch (DatabaseException e) {

                logger.error(
                        "PERMISSION_VALIDATION_FAILED | method={} | endpoint={} | user={} | userId={} | permission={} | message={}",
                        req.getMethod(),
                        path,
                        user.getUsername(),
                        user.getId(),
                        requiredPermission,
                        e.getMessage());

                res.sendError(
                        HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Permission validation failed");
                return;
            }
        }

        chain.doFilter(request, response);
    }

    private boolean isPublicPath(String path) {

        return path.equals("/") ||
                path.equals("/index.jsp") ||

                path.equals("/login") ||
                path.equals("/register") ||

                path.equals("/user/login") ||
                path.equals("/user/register") ||

                path.equals("/health") ||

                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.startsWith("/assets/");
    }

    private String getRequiredPermission(String path) {

        if (path.equals("/welcome") ||
                path.equals("/welcome.jsp") ||
                path.equals("/api/profile")) {

            return "PROFILE_READ";
        }

        if (path.equals("/settings") ||
                path.equals("/settings.jsp")) {

            return "PROFILE_READ";
        }

        if (path.equals("/users") ||
                path.equals("/users.jsp") ||
                path.equals("/auth/users") ||
                path.equals("/auth/user")) {

            return "USER_READ";
        }

        if (path.equals("/manage-user") ||
                path.equals("/manage-user.jsp") ||
                path.equals("/auth/update-status")) {

            return "USER_UPDATE";
        }

        if (path.equals("/auth/reset-password")) {
            return "USER_PASSWORD_RESET";
        }

        if (path.equals("/auth/update-role")) {
            return "ROLE_UPDATE";
        }

        if (path.equals("/logs")) {
            return "LOG_VIEW";
        }
        if (path.equals("/roles")) {
            return "ROLE_READ";
        }

        if (path.equals("/manage-role")) {
            return "ROLE_PERMISSION_MANAGE";
        }

        if (path.equals("/auth/roles/create")) {
            return "ROLE_CREATE";
        }

        if (path.equals("/auth/roles/update-status")) {
            return "ROLE_UPDATE";
        }

        if (path.equals("/auth/roles/update-permissions")) {
            return "ROLE_PERMISSION_MANAGE";
        }
        if (path.equals("/roles") ||
                path.equals("/roles.jsp")) {
            return "ROLE_READ";
        }
        if (path.equals("/auth/roles/create")) {
            return "ROLE_CREATE";
        }
        return null;
    }
}