package com.example.session.api;

import java.io.IOException;

import com.example.session.DTO.Res.ApiResponseDTO;
import com.example.session.DTO.Res.ErrorResponseDTO;
import com.example.session.exceptions.AuthenticationException;
import com.example.session.exceptions.ValidationException;
import com.example.session.util.JsonUtil;
import com.example.session.util.csrfValidator;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/user/logout")
public class LogoutApiServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
        try {
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("user") == null) {
                throw new AuthenticationException("session Expired, Login Again");
            }
            String sessionToken = session.getAttribute("csrfToken").toString();
            String cookieToken = getCookieValue(req, "CSRF_TOKEN");

            boolean valid = csrfValidator.validateCSRF(sessionToken, cookieToken);

            if (!valid) {
                throw new ValidationException("Invalid Access");
            }

            session.invalidate();
            deleteCsrfCookie(req, res);
            JsonUtil.writeJson(res, new ApiResponseDTO(true, "Logout successful"));

        } catch (ValidationException e) {
            res.setStatus(403);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            403,
                            "CSRF_ERROR",
                            e.getMessage())
                        );
        } catch (AuthenticationException e) {

            res.setStatus(401);

            JsonUtil.writeJson(
                    res,
                    new ErrorResponseDTO(
                            401,
                            "AUTHENTICATION_ERROR",
                            e.getMessage()));
        }
    }

    private String getCookieValue(HttpServletRequest req, String name) {

        Cookie[] cookies = req.getCookies();
        String value;

        if (cookies == null) {
            return null;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                value = cookie.getValue();
                return value;
            }
        }

        return null;
    }

    private void deleteCsrfCookie(HttpServletRequest req, HttpServletResponse res) {

        String cookieHeader = "CSRF_TOKEN=" +
                "; Path=" + req.getContextPath() +
                "; Max-Age=0" +
                "; HttpOnly" +
                "; SameSite=Strict";

        res.addHeader("Set-Cookie", cookieHeader);
    }
}
