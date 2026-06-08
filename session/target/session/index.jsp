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
            <style>
                .loginForm,
                .registerFrom {
                    width: 300px;
                    margin: 50px auto;
                    padding: 20px;
                    border: 1px solid #ccc;
                    border-radius: 5px;
                }

                .registerFrom {
                    display: none;
                }

                label {
                    display: block;
                    margin-bottom: 5px;
                }

                input[type="email"],
                input[type="password"],
                input[type="text"] {
                    width: 100%;
                    padding: 8px;
                    margin-bottom: 10px;
                    border: 1px solid #ccc;
                    border-radius: 4px;
                }

                input[type="submit"] {
                    width: 100%;
                    padding: 10px;
                    background-color: #4CAF50;
                    color: white;
                    border: none;
                    border-radius: 4px;
                    cursor: pointer;
                }

                input[type="submit"]:hover {
                    background-color: #45a049;
                }
            </style>

        </head>

        <body>
            <div class="loginForm">
                <h2>Login</h2>
                <form action="login" method="post">
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" value="<%= mail %>" required><br><br>
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required><br><br>
                    <input type="checkbox" id="rememberMe" name="rememberMe" value="true" <%=mail.isEmpty() ? ""
                        : "checked" %>>
                    <label for="rememberMe">Remember Me</label>
                    <input type="submit" value="Login">
                </form>
                don't have an account? <a id="switchToRegister">Register here</a>
            </div>

            <div class="registerFrom">
                <h2>Register</h2>
                <form action="register" method="post">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" required><br><br>
                    <label for="email">Email:</label>
                    <input type="email" id="email" name="email" required><br><br>
                    <label for="password">Password:</label>
                    <input type="password" id="password" name="password" required><br><br>
                    <input type="submit" value="Register">
                </form>
                already have an account? <a id="switchToLogin">Login here</a>
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