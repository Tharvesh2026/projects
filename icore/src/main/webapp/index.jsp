<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String mail = "";
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("email")) {
                mail = cookie.getValue();
                break;
            }
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Session Management</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        * {
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: #f4f4f4;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .main-box {
            width: 900px;
            min-height: 520px;
            background: linear-gradient(135deg, #0399f5, #00508f);
            border-radius: 18px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 55px;
            box-shadow: 0 18px 35px rgba(0,0,0,0.25);
            position: relative;
            overflow: hidden;
        }

        .main-box::before {
            content: "";
            position: absolute;
            width: 420px;
            height: 420px;
            background: rgba(255,255,255,0.08);
            border-radius: 50%;
            left: -120px;
            top: -70px;
        }

        .main-box::after {
            content: "";
            position: absolute;
            width: 360px;
            height: 360px;
            background: rgba(0,180,255,0.35);
            border-radius: 50%;
            left: -40px;
            bottom: -170px;
        }

        .welcome {
            color: white;
            width: 45%;
            z-index: 1;
        }

        .welcome h1 {
            font-size: 42px;
            font-weight: 800;
            letter-spacing: 2px;
        }

        .welcome h4 {
            font-size: 16px;
            font-weight: 700;
            margin-bottom: 15px;
        }

        .welcome p {
            font-size: 13px;
            line-height: 1.8;
            max-width: 350px;
        }

        .form-card {
            width: 360px;
            background: white;
            border-radius: 18px;
            padding: 35px;
            z-index: 1;
            box-shadow: 0 12px 25px rgba(0,0,0,0.25);
        }

        .form-card h3 {
            color: #04336b;
            font-size: 30px;
            font-weight: 800;
            margin-bottom: 8px;
        }

        .form-card small {
            color: #777;
            display: block;
            margin-bottom: 25px;
        }

        .form-control {
            height: 45px;
            border-radius: 8px;
            font-size: 14px;
        }

        .btn-main {
            background: linear-gradient(135deg, #0079d6, #00518d);
            border: none;
            height: 45px;
            border-radius: 8px;
            color: white;
            font-weight: 700;
            width: 100%;
        }

        .bottom-text {
            text-align: center;
            font-size: 12px;
            margin-top: 25px;
        }

        .bottom-text a,
        .forgot-link {
            color: #006fc9;
            text-decoration: none;
            font-weight: 700;
        }

        .registerFrom {
            display: none;
        }

        @media(max-width: 768px) {
            .main-box {
                width: 92%;
                flex-direction: column;
                padding: 35px 22px;
                gap: 30px;
            }

            .welcome,
            .form-card {
                width: 100%;
            }
        }
    </style>
</head>

<body>

<div class="main-box">

    <div class="welcome">
        <h1>WELCOME</h1>
        <h4>YOUR HEADLINE NAME</h4>
        <p>
            Lorem ipsum dolor sit amet, consectetur adipiscing elit.
            Sed diam nonummy nibh euismod tincidunt ut laoreet dolore.
        </p>
    </div>

    <div class="form-card loginForm">
        <h3>Sign in</h3>
        <small>Lorem ipsum dolor sit amet, consectetur adipiscing elit</small>

        <% if(request.getParameter("error") != null){ %>
            <div class="alert alert-danger">
                <%= request.getParameter("error") %>
            </div>
        <% } %>

        <% if(request.getParameter("logout") != null){ %>
            <div class="alert alert-success">
                Logged out successfully.
            </div>
        <% } %>

        <form action="login" method="post">

            <div class="mb-3">
                <input type="email" class="form-control" id="loginEmail" name="email"
                       placeholder="User Name" value="<%= mail %>" required>
            </div>

            <div class="mb-2">
                <input type="password" class="form-control" id="loginPassword" name="password"
                       placeholder="Password" required>
            </div>

            <div class="d-flex justify-content-between align-items-center mb-3">
                <div class="form-check">
                    <input class="form-check-input" type="checkbox"
                           name="rememberMe" value="true"
                           <%= mail.isEmpty() ? "" : "checked" %>>

                    <label class="form-check-label small">
                        Remember me
                    </label>
                </div>

                <a href="#" class="small forgot-link">Forgot Password?</a>
            </div>

            <button class="btn-main">
                Sign in
            </button>

        </form>

        <div class="bottom-text">
            Don't have an account?
            <a href="#" id="switchToRegister">Sign Up</a>
        </div>
    </div>

    <div class="form-card registerFrom">
        <h3>Sign up</h3>
        <small>Create your new account</small>

        <form action="register" method="post">

            <div class="mb-3">
                <input type="text" class="form-control" id="name" name="name"
                       placeholder="Name" required>
            </div>

            <div class="mb-3">
                <input type="text" class="form-control" name="username"
                       placeholder="Username" required>
            </div>

            <div class="mb-3">
                <input type="email" class="form-control" id="registerEmail" name="email"
                       placeholder="Email" required>
            </div>

            <div class="mb-3">
                <input type="password" class="form-control" id="registerPassword" name="password"
                       placeholder="Password" required>
            </div>

            <button class="btn-main">
                Register
            </button>

        </form>

        <div class="bottom-text">
            Already have an account?
            <a href="#" id="switchToLogin">Sign in</a>
        </div>
    </div>

</div>

<script>
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

    const name = document.getElementById("name");
    const loginPassword = document.getElementById("loginPassword");
    const registerPassword = document.getElementById("registerPassword");

    if (name) {
        name.addEventListener("input", function () {
            if (name.value.trim() === "") {
                name.setCustomValidity("Name cannot be empty");
            } else {
                name.setCustomValidity("");
            }
        });
    }

    if (loginPassword) {
        loginPassword.addEventListener("input", function () {
            if (loginPassword.value.length < 6) {
                loginPassword.setCustomValidity("Password must be at least 6 characters long");
            } else {
                loginPassword.setCustomValidity("");
            }
        });
    }

    if (registerPassword) {
        registerPassword.addEventListener("input", function () {
            if (registerPassword.value.length < 6) {
                registerPassword.setCustomValidity("Password must be at least 6 characters long");
            } else {
                registerPassword.setCustomValidity("");
            }
        });
    }
</script>

</body>
</html>