package com.example.session.api.auth;

import com.example.session.DAO.UserDAO;
import com.example.session.DAO.PermissionDAO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.PasswordHasher;
import com.example.session.util.ValidationDAOUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * ProfileServlet — /profile
 *
 * GET /profile → render profile.jsp
 * POST /profile action=updateProfile → update name, username, mailId
 * POST /profile action=changePassword → verify current pwd, store new hash
 */
@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {

    private UserDAO userDAO;
    private PermissionDAO permissionDAO;

    @Override
    public void init() {
        userDAO = new UserDAO();
        permissionDAO = new PermissionDAO();
    }

    // ── GET ──────────────────────────────────────────────────────────────────

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");

        /* ── Resolved permissions for the "Access" tab ── */
        try {
            List<String> perms = permissionDAO.getPermissionsByUserId(user.getId());
            request.setAttribute("userPermissions", perms);
        } catch (DatabaseException e) {
            request.setAttribute("userPermissions", new ArrayList<>());
        }

        /* ── Activity stub (replace with real AuditLogDAO if you add one) ── */
        request.setAttribute("activities", buildActivityStub());

        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }

    // ── POST ─────────────────────────────────────────────────────────────────

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        User user = (User) session.getAttribute("user");
        String action = request.getParameter("action");

        if (action == null) {
            response.sendRedirect(request.getContextPath() + "/profile?error=Invalid+request.");
            return;
        }

        switch (action) {
            case "updateProfile":
                handleUpdateProfile(request, response, session, user);
                break;
            case "changePassword":
                handleChangePassword(request, response, user);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/profile?error=Unknown+action.");
        }
    }

    // ── Handler: update profile ───────────────────────────────────────────────

    private void handleUpdateProfile(HttpServletRequest request,
            HttpServletResponse response,
            HttpSession session,
            User user)
            throws IOException {

        String name = ValidationDAOUtil.sanitizeName(request.getParameter("name"));
        String username = ValidationDAOUtil.sanitizeUsername(request.getParameter("username"));
        String email = ValidationDAOUtil.sanitizeEmail(request.getParameter("email")); // stored as mailId in DB

        /* Validation */
        if (name.isEmpty() || username.isEmpty() || email.isEmpty()) {
            redirect(response, request, "/profile?tab=edit&error=All+fields+are+required.");
            return;
        }

        if (!email.matches("^[\\w.+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$")) {
            redirect(response, request, "/profile?tab=edit&error=Invalid+email+address.");
            return;
        }

        if (username.length() < 3 || username.length() > 30) {
            redirect(response, request, "/profile?tab=edit&error=Username+must+be+3-30+characters.");
            return;
        }

        if (!username.matches("^[a-z0-9_]+$")) {
            redirect(response, request,
                    "/profile?tab=edit&error=Username+may+only+contain+lowercase+letters,+numbers+and+underscores.");
            return;
        }

        /* Persist */
        try {
            boolean updated = userDAO.updateProfile(user.getId(), name, username, email);

            if (!updated) {
                redirect(response, request,
                        "/profile?tab=edit&error=Update+failed.+Username+or+email+may+already+be+taken.");
                return;
            }

            /*
             * User is immutable — build a fresh object with updated fields.
             * passwordHash is preserved from the existing session object.
             */
            User refreshed = new User(
                    username,
                    email, // mailId
                    user.getPasswordHash(),
                    name,
                    user.getRole(),
                    user.getStatus(),
                    user.getId());
            session.setAttribute("user", refreshed);

            redirect(response, request, "/profile?success=Profile+updated+successfully.");

        } catch (DatabaseException e) {
            getServletContext().log("ProfileServlet.handleUpdateProfile", e);
            redirect(response, request, "/profile?tab=edit&error=A+database+error+occurred.");
        }
    }

    // ── Handler: change password ──────────────────────────────────────────────

    private void handleChangePassword(HttpServletRequest request,
            HttpServletResponse response,
            User user)
            throws IOException {

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        /* Validation */
        if (isBlank(currentPassword) || isBlank(newPassword) || isBlank(confirmPassword)) {
            redirect(response, request,
                    "/profile?tab=password&error=All+password+fields+are+required.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            redirect(response, request,
                    "/profile?tab=password&error=New+passwords+do+not+match.");
            return;
        }

        if (newPassword.length() < 6) {
            redirect(response, request,
                    "/profile?tab=password&error=New+password+must+be+at+least+6+characters.");
            return;
        }

        if (newPassword.equals(currentPassword)) {
            redirect(response, request,
                    "/profile?tab=password&error=New+password+must+differ+from+current+password.");
            return;
        }

        /*
         * Verify current password using PasswordHasher.verify() — same as
         * validateUser() in UserDAO
         */
        try {
            boolean valid = userDAO.verifyPassword(user.getId(), currentPassword);

            if (!valid) {
                redirect(response, request,
                        "/profile?tab=password&error=Current+password+is+incorrect.");
                return;
            }

            /* Hash the new password and store via existing resetPassword() */
            String newHash = PasswordHasher.hash(newPassword);
            boolean changed = userDAO.resetPassword(user.getId(), newHash);

            if (!changed) {
                redirect(response, request,
                        "/profile?tab=password&error=Password+update+failed.+Please+try+again.");
                return;
            }

            redirect(response, request,
                    "/profile?success=Password+changed+successfully.");

        } catch (DatabaseException e) {
            getServletContext().log("ProfileServlet.handleChangePassword", e);
            redirect(response, request,
                    "/profile?tab=password&error=A+database+error+occurred.");
        }
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    /**
     * Returns a placeholder activity list.
     * When you add an AuditLogDAO, replace this with a real DB call:
     *
     * return auditLogDAO.getRecentByUser(userId, 10);
     *
     * Each map must have keys "time" (String) and "message" (String).
     */
    private List<Map<String, String>> buildActivityStub() {
        List<Map<String, String>> list = new ArrayList<>();
        list.add(entry("just now", "Viewed profile page"));
        list.add(entry("this session", "Session active — permissions resolved from DB"));
        return list;
    }

    private Map<String, String> entry(String time, String message) {
        Map<String, String> m = new LinkedHashMap<>();
        m.put("time", time);
        m.put("message", message);
        return m;
    }

    private void redirect(HttpServletResponse response,
            HttpServletRequest request,
            String path) throws IOException {
        response.sendRedirect(request.getContextPath() + path);
    }   

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}