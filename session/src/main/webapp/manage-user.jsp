<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.example.session.model.User" %>

<%
    User selectedUser = (User) request.getAttribute("selectedUser");

    if (selectedUser == null) {
        response.sendRedirect(request.getContextPath() + "/users");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage User</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <style>
        * {
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: #f4f4f4;
        }

        .page-wrapper {
            min-height: calc(100vh - 70px);
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 15px;
        }

        .main-box {
            width: 1000px;
            min-height: 560px;
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
            width: 38%;
            z-index: 1;
        }

        .welcome h1 {
            font-size: 42px;
            font-weight: 800;
            letter-spacing: 2px;
            margin-bottom: 12px;
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
            width: 520px;
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
            margin-bottom: 20px;
        }

        .user-summary {
            background: #f7fbff;
            border: 1px solid #dcefff;
            border-radius: 12px;
            padding: 15px;
            margin-bottom: 18px;
            font-size: 14px;
        }

        .user-summary p {
            margin: 6px 0;
        }

        .user-summary strong {
            color: #04336b;
        }

        .form-section {
            border: 1px solid #e8e8e8;
            border-radius: 12px;
            padding: 15px;
            margin-bottom: 15px;
            background: #fafafa;
        }

        .form-section h5 {
            margin: 0 0 12px;
            color: #04336b;
            font-size: 16px;
            font-weight: 800;
        }

        .form-control {
            width: 100%;
            height: 42px;
            border-radius: 8px;
            font-size: 14px;
            border: 1px solid #ccc;
            padding: 8px 10px;
            margin-bottom: 10px;
        }

        .btn-main {
            background: linear-gradient(135deg, #0079d6, #00518d);
            border: none;
            height: 42px;
            border-radius: 8px;
            color: white;
            font-weight: 700;
            width: 100%;
            cursor: pointer;
        }

        .btn-danger {
            background: linear-gradient(135deg, #e74c3c, #b82015);
        }

        .alert-success {
            background: #e8fff0;
            color: #0b7a34;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 12px;
            font-size: 14px;
        }

        .alert-error {
            background: #ffeaea;
            color: #a10000;
            padding: 10px;
            border-radius: 8px;
            margin-bottom: 12px;
            font-size: 14px;
        }

        .back-link {
            display: block;
            margin-top: 16px;
            text-align: center;
            color: #006fc9;
            text-decoration: none;
            font-weight: 700;
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

<%@ include file="navbar.jsp" %>

<div class="page-wrapper">
    <div class="main-box">

        <div class="welcome">
            <h1>MANAGE</h1>
            <h4><%= selectedUser.getUsername() %></h4>
            <p>
                Update user role, account status, or reset password.
                These actions are restricted to SYS_ADMIN users only.
            </p>
        </div>

        <div class="form-card">
            <h3>User Control</h3>
            <small>Manage selected user account safely</small>

            <% if (request.getParameter("success") != null) { %>
                <div class="alert-success"><%= request.getParameter("success") %></div>
            <% } %>

            <% if (request.getParameter("error") != null) { %>
                <div class="alert-error"><%= request.getParameter("error") %></div>
            <% } %>

            <div class="user-summary">
                <p><strong>ID:</strong> <%= selectedUser.getId() %></p>
                <p><strong>Name:</strong> <%= selectedUser.getName() %></p>
                <p><strong>Username:</strong> <%= selectedUser.getUsername() %></p>
                <p><strong>Email:</strong> <%= selectedUser.getEmail() %></p>
                <p><strong>Current Role:</strong> <%= selectedUser.getRole() %></p>
                <p><strong>Current Status:</strong> <%= selectedUser.getStatus() %></p>
            </div>

            <div class="form-section">
                <h5>Change Role</h5>
                <form action="<%= request.getContextPath() %>/manage-user" method="post">
                    <input type="hidden" name="id" value="<%= selectedUser.getId() %>">
                    <input type="hidden" name="action" value="updateRole">

                    <select name="role" class="form-control">
                        <option value="USER" <%= "USER".equals(selectedUser.getRole()) ? "selected" : "" %>>USER</option>
                        <option value="ADMIN" <%= "ADMIN".equals(selectedUser.getRole()) ? "selected" : "" %>>ADMIN</option>
                        <option value="SYS_ADMIN" <%= "SYS_ADMIN".equals(selectedUser.getRole()) ? "selected" : "" %>>SYS_ADMIN</option>
                    </select>

                    <button type="submit" class="btn-main">Update Role</button>
                </form>
            </div>

            <div class="form-section">
                <h5>Change Status</h5>
                <form action="<%= request.getContextPath() %>/manage-user" method="post">
                    <input type="hidden" name="id" value="<%= selectedUser.getId() %>">
                    <input type="hidden" name="action" value="updateStatus">

                    <select name="status" class="form-control">
                        <option value="ACTIVE" <%= "ACTIVE".equals(selectedUser.getStatus()) ? "selected" : "" %>>ACTIVE</option>
                        <option value="DISABLED" <%= "DISABLED".equals(selectedUser.getStatus()) ? "selected" : "" %>>DISABLED</option>
                        <option value="LOCKED" <%= "LOCKED".equals(selectedUser.getStatus()) ? "selected" : "" %>>LOCKED</option>
                    </select>

                    <button type="submit" class="btn-main">Update Status</button>
                </form>
            </div>

            <div class="form-section">
                <h5>Reset Password</h5>
                <form action="<%= request.getContextPath() %>/manage-user" method="post">
                    <input type="hidden" name="id" value="<%= selectedUser.getId() %>">
                    <input type="hidden" name="action" value="resetPassword">

                    <input type="password"
                           name="newPassword"
                           class="form-control"
                           placeholder="Enter new password"
                           required>

                    <button type="submit"
                            class="btn-main btn-danger"
                            onclick="return confirm('Are you sure you want to reset this password?');">
                        Reset Password
                    </button>
                </form>
            </div>

            <a href="<%= request.getContextPath() %>/users" class="back-link">
                Back to Users
            </a>
        </div>

    </div>
</div>

</body>
</html>