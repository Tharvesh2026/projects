    package com.example.session.api.page;

    import jakarta.servlet.ServletException;
    import jakarta.servlet.annotation.WebServlet;
    import jakarta.servlet.http.*;

    import java.io.IOException;

    @WebServlet("/settings")
    public class SettingsServlet extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req,
                            HttpServletResponse res)
                throws ServletException, IOException {

            req.getRequestDispatcher("/settings.jsp")
                    .forward(req, res);
        }
    }