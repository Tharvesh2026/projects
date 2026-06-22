package com.example.session.api.page;

import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.PermissionValidator;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@WebServlet("/logs")
public class LogServlet extends HttpServlet {

    private static final String LOG_FILE_PATH = "logs/application.log";

    @Override
    protected void doGet(HttpServletRequest req,
            HttpServletResponse res)
            throws IOException {

        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            res.sendRedirect(req.getContextPath() + "/index.jsp?error=loginRequired");
            return;
        }

        User user = (User) session.getAttribute("user");
        try {

            if (!PermissionValidator.hasPermission(
                    user.getId(),
                    "LOG_VIEW")) {

                res.sendError(
                        HttpServletResponse.SC_FORBIDDEN,
                        "Access Denied");

                return;
            }

        } catch (DatabaseException e) {

            res.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Permission validation failed");

            return;
        }

        res.setContentType("text/plain;charset=UTF-8");

        PrintWriter out = res.getWriter();

        Path logPath = Paths.get(LOG_FILE_PATH);

        out.println("===== APPLICATION LOGS =====");
        out.println("Requested By : " + user.getUsername());
        out.println("Mail Id      : " + user.getEmail());
        out.println("Endpoint     : " + req.getServletPath());
        out.println("Client IP    : " + req.getRemoteAddr());
        out.println("Timestamp    : " + new java.util.Date());
        out.println("----------------------------------------\n");

        if (!Files.exists(logPath)) {
            out.println("Log file not found: " + logPath.toAbsolutePath());
            return;
        }

        List<String> lines = Files.readAllLines(logPath);

        int start = Math.max(0, lines.size() - 100);

        for (int i = start; i < lines.size(); i++) {
            out.println(lines.get(i));
        }
    }
}