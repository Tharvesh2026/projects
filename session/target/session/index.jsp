<!-- "Simple Form register / login page" -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <% String mail="" ; Cookie[] cookies=request.getCookies(); if(cookies !=null){ for(Cookie cookie : cookies){
        if(cookie.getName().equals("email")){ mail=cookie.getValue(); break; } } } %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Session Management</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

            <style>
                body {
                    background: linear-gradient(135deg,
                            #667eea 0%,
                            #764ba2 100%);
                    min-height: 100vh;
                }

                .registerFrom {
                    display: none;
                }

                .card {
                    border: none;
                    border-radius: 15px;
                }
            </style>

        </head>

        <body class="bg-light">

            <div class="container py-5">

                <div class="row justify-content-center">

                    <!-- Login -->
                    <div class="col-md-5">
                        <div class="card shadow loginForm">
                            <div class="card-body">

                                <h3 class="text-center mb-4">Login</h3>

                                <% if(request.getParameter("error") !=null){ %>
                                    <div class="alert alert-danger">
                                        <%= request.getParameter("error") %>
                                    </div>
                                    <% } %>

                                        <% if(request.getParameter("logout") !=null){ %>
                                            <div class="alert alert-success">
                                                Logged out successfully.
                                            </div>
                                            <% } %>

                                                <form action="login" method="post">

                                                    <div class="mb-3">
                                                        <label class="form-label">Email</label>
                                                        <input type="email" class="form-control" id="email" name="email"
                                                            value="<%= mail %>" required>

                                                    </div>

                                                    <div class="mb-3">
                                                        <label class="form-label">Password</label>
                                                        <input type="password" class="form-control" id="password"
                                                            name="password" required>
                                                    </div>

                                                    <div class="form-check mb-3">
                                                        <input class="form-check-input" type="checkbox"
                                                            name="rememberMe" value="true" <%=mail.isEmpty() ? ""
                                                            : "checked" %>>

                                                        <label class="form-check-label">
                                                            Remember Me
                                                        </label>
                                                    </div>

                                                    <button class="btn btn-primary w-100">
                                                        Login
                                                    </button>

                                                </form>

                                                <p class="text-center mt-3">
                                                    Don't have an account?
                                                    <a href="#" id="switchToRegister">Register here</a>
                                                </p>

                            </div>
                        </div>
                    </div>

                    <!-- Register -->
                    <div class="col-md-5">
                        <div class="card shadow registerFrom">
                            <div class="card-body">

                                <h3 class="text-center mb-4">Register</h3>

                                <form action="register" method="post">

                                    <div class="mb-3">
                                        <label class="form-label">Name</label>
                                        <input type="text" class="form-control" id="name" name="name" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Username</label>
                                        <input type="text" class="form-control" name="username" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Email</label>
                                        <input type="email" class="form-control" id="email" name="email" required>
                                    </div>

                                    <div class="mb-3">
                                        <label class="form-label">Password</label>
                                        <input type="password" class="form-control" id="password" name="password"
                                            required>
                                    </div>

                                    <button class="btn btn-success w-100">
                                        Register
                                    </button>

                                </form>

                                <p class="text-center mt-3">
                                    Already have an account?
                                    <a href="#" id="switchToLogin">Login here</a>
                                </p>

                            </div>
                        </div>
                    </div>

                </div>
            </div>


            <script>
                // Default javascript form switching
                document.getElementById("switchToRegister").addEventListener("click", function (e) {
                    e.preventDefault();
                    document.querySelector(".loginForm").style.display = "none";
                    document.querySelector(".registerFrom").style.display = "block";
                });

                document.getElementById("switchToLogin").addEventListener("click", function (e) {
                    e.preventDefault();
                    document.querySelector(".registerFrom").style.display = "none";
                    document.querySelector(".loginForm").style.display = "block";
                });

                var name = document.getElementById("name");
                var pwd = document.getElementById("password");
                var email = document.getElementById("email");

                if (name != null) {
                    name.addEventListener("input", function () {
                        if (name.value.trim() === "") {
                            name.setCustomValidity("Name cannot be empty");
                        } else {
                            name.setCustomValidity("");
                        }
                    });
                }
                if (email != null) {
                    email.addEventListener("input", function () {
                        if (email.value.trim() === "") {
                            email.setCustomValidity("Email cannot be empty");
                        } else if (!email.value.includes("@")) {
                            email.setCustomValidity("Please enter a valid email address");
                        } else {
                            email.setCustomValidity("");
                        }
                    });
                }
                if (pwd != null) {
                    pwd.addEventListener("input", function () {
                        if (pwd.value.trim() === "") {
                            pwd.setCustomValidity("Password cannot be empty");
                        } else if (pwd.value.length < 6) {
                            pwd.setCustomValidity("Password must be at least 6 characters long");
                        } else {
                            pwd.setCustomValidity("");
                        }
                    });
                }
            </script>

        </body>

        </html>