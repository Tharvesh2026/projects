package com.example.session.api.auth;

import com.example.session.DAO.UserDAO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.PasswordHasher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet("/manage-user")
public class ManageUserServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(ManageUserServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        try {
            int userId = Integer.parseInt(req.getParameter("id"));

            UserDAO dao = new UserDAO();

            User selectedUser = dao.getUserById(userId);

            if (selectedUser == null) {
                res.sendError(404, "User not found");
                return;
            }

            req.setAttribute("selectedUser", selectedUser);

            req.getRequestDispatcher("/manage-user.jsp") .forward(req, res);

        } catch (NumberFormatException e) {
            res.sendError(400, "Invalid user id");

        } catch (DatabaseException e) {
            throw new ServletException("Unable to load user details", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException, ServletException {

        try {
            int userId = Integer.parseInt(req.getParameter("id"));
            String action = req.getParameter("action");

            UserDAO dao = new UserDAO();

            User targetUser = dao.getUserById(userId);

            if (targetUser == null) {
                res.sendError(404, "User not found");
                return;
            }

            HttpSession session = req.getSession(false);
            User currentUser = (User) session.getAttribute("user");

            if ("updateRole".equals(action)) {

                String newRole = req.getParameter("role");

                dao.updateUserRole(userId, newRole);

                logger.info(
                        "ACTION=ROLE_UPDATE | INITIATED_BY={} | TARGET_USER={} | OLD_ROLE={} | NEW_ROLE={} | STATUS=SUCCESS",
                        currentUser.getUsername(),
                        targetUser.getUsername(),
                        targetUser.getRole(),
                        newRole
                );

                res.sendRedirect(req.getContextPath()
                        + "/manage-user?id=" + userId
                        + "&success=Role updated");

                return;
            }

            if ("updateStatus".equals(action)) {

                String newStatus = req.getParameter("status");

                dao.updateUserStatus(userId, newStatus);

                logger.info(
                        "ACTION=STATUS_UPDATE | INITIATED_BY={} | TARGET_USER={} | OLD_STATUS={} | NEW_STATUS={} | STATUS=SUCCESS",
                        currentUser.getUsername(),
                        targetUser.getUsername(),
                        targetUser.getStatus(),
                        newStatus
                );

                res.sendRedirect(req.getContextPath()
                        + "/manage-user?id=" + userId
                        + "&success=Status updated");

                return;
            }

            if ("resetPassword".equals(action)) {

                String newPassword = req.getParameter("newPassword");

                if (newPassword == null || newPassword.length() < 6) {
                    res.sendRedirect(req.getContextPath()
                            + "/manage-user?id=" + userId
                            + "&error=Password must be at least 6 characters");
                    return;
                }

                String hashedPassword =
                        PasswordHasher.hash(newPassword);

                dao.resetPassword(userId, hashedPassword);

                logger.info(
                        "ACTION=PASSWORD_RESET | INITIATED_BY={} | TARGET_USER={} | STATUS=SUCCESS",
                        currentUser.getUsername(),
                        targetUser.getUsername()
                );

                res.sendRedirect(req.getContextPath()
                        + "/manage-user?id=" + userId
                        + "&success=Password reset successful");

                return;
            }

            res.sendError(400, "Invalid action");

        } catch (NumberFormatException e) {
            res.sendError(400, "Invalid user id");

        } catch (DatabaseException e) {
            throw new ServletException("Unable to manage user", e);
        }
    }
}