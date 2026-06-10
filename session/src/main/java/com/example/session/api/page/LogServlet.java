package com.example.session.api.page;

import com.example.session.model.User;

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
        String role = user.getRole();

        if (!"ADMIN".equals(role) && !"SYS_ADMIN".equals(role)) {
            res.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            return;
        }

        res.setContentType("text/plain;charset=UTF-8");

        PrintWriter out = res.getWriter();

        Path logPath = Paths.get(LOG_FILE_PATH);

        out.println("===== APPLICATION LOGS =====");
        out.println("Requested By : " + user.getUsername());
        out.println("Role         : " + user.getRole());
        out.println("----------------------------------------");

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