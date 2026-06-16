package com.example.session.api;

import java.io.IOException;
import java.util.List;

import com.example.session.DAO.UserDAO;
import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.DatabaseException;
import com.example.session.model.User;
import com.example.session.util.JsonUtil;
import com.example.session.util.PermissionValidator;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/auth/users")
public class UsersApiServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("user") == null) {
                res.setStatus(401);
                JsonUtil.writeJson(res, new ErrorResponseDTO(401, "AUTHENTICATION_ERROR", "Login Required"));
                return;
            }
            User you = (User) session.getAttribute("user");

            if (!PermissionValidator.hasPermission(you.getId(),"USER_READ")){
                res.setStatus(403);
                JsonUtil.writeJson(res, new ErrorResponseDTO(403, "AUTHORIZATION_ERROR", "Access Denied"));
                return;
            }

            UserDAO dao = new UserDAO();
            List<User> users = dao.getAllUsers();
            JsonUtil.writeJson(res, new ApiResponseDTO(true, "User List Fetch Successful", users));
            return;
        } catch (DatabaseException e) {

            res.setStatus(500);

            JsonUtil.writeJson( res,new ErrorResponseDTO(500, "DATABASE_ERROR", e.getMessage()));
        }
    }
}
