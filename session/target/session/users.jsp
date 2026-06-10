<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.session.model.User" %>

<%
    List<User> users = (List<User>) request.getAttribute("users");

    if (users == null) {
        response.sendRedirect(request.getContextPath() + "/users");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Users Management</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

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
        }

        .page-wrapper {
            padding: 40px 15px;
        }

        .users-box {
            max-width: 1100px;
            margin: auto;
            background: linear-gradient(135deg, #0399f5, #00508f);
            border-radius: 18px;
            padding: 45px;
            box-shadow: 0 18px 35px rgba(0,0,0,0.25);
            position: relative;
            overflow: hidden;
        }

        .users-box::before {
            content: "";
            position: absolute;
            width: 420px;
            height: 420px;
            background: rgba(255,255,255,0.08);
            border-radius: 50%;
            left: -120px;
            top: -70px;
        }

        .users-header {
            color: white;
            position: relative;
            z-index: 1;
            margin-bottom: 30px;
        }

        .users-header h1 {
            font-size: 38px;
            font-weight: 800;
            margin-bottom: 8px;
        }

        .users-header p {
            opacity: 0.95;
            margin: 0;
        }

        .user-card {
            background: white;
            border-radius: 18px;
            padding: 25px;
            box-shadow: 0 12px 25px rgba(0,0,0,0.18);
            height: 100%;
            position: relative;
            z-index: 1;
        }

        .avatar {
            width: 78px;
            height: 78px;
            border-radius: 50%;
            object-fit: cover;
            border: 4px solid #eaf7ff;
            margin-bottom: 15px;
        }

        .user-card h4 {
            color: #04336b;
            font-weight: 800;
            margin-bottom: 5px;
        }

        .user-card small {
            color: #777;
            display: block;
            margin-bottom: 18px;
        }

        .detail-item {
            border: 1px solid #e5e5e5;
            border-radius: 10px;
            padding: 10px 12px;
            margin-bottom: 10px;
            font-size: 14px;
            background: #fafafa;
        }

        .detail-item strong {
            color: #04336b;
        }

        .role-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 50px;
            font-size: 12px;
            font-weight: 700;
            background: #eaf7ff;
            color: #00508f;
        }

        .status-active {
            background: #e8fff0;
            color: #0b7a34;
        }

        .status-inactive {
            background: #ffeaea;
            color: #a10000;
        }

        .manage-btn {
            display: block;
            width: 100%;
            margin-top: 15px;
            padding: 12px;
            border-radius: 10px;
            background: linear-gradient(135deg, #0079d6, #00518d);
            color: white;
            text-align: center;
            font-weight: 700;
            text-decoration: none;
        }

        .manage-btn:hover {
            color: white;
            opacity: 0.95;
        }
    </style>
</head>

<body>

<%@ include file="navbar.jsp" %>

<div class="page-wrapper">

    <div class="users-box">

        <div class="users-header">
            <h1>Users</h1>
            <p>View registered users, roles and account status.</p>
        </div>

        <div class="row g-4">

            <%
                for (User user : users) {
                    String username = user.getUsername();
                    String avatarUrl = "https://unavatar.io/github/" + username;

                    String statusClass = "status-active";

                    if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
                        statusClass = "status-inactive";
                    }
            %>

            <div class="col-md-4">
                <div class="user-card">

                    <div class="text-center">
                        <img class="avatar"
                             src="<%= avatarUrl %>"
                             alt="<%= username %>">

                        <h4><%= user.getName() %></h4>
                        <small>@<%= user.getUsername() %></small>
                    </div>

                    <div class="detail-item">
                        <strong>ID:</strong>
                        <%= user.getId() %>
                    </div>

                    <div class="detail-item">
                        <strong>Email:</strong>
                        <%= user.getEmail() %>
                    </div>

                    <div class="detail-item">
                        <strong>Role:</strong>
                        <span class="role-badge"><%= user.getRole() %></span>
                    </div>

                    <div class="detail-item">
                        <strong>Status:</strong>
                        <span class="role-badge <%= statusClass %>">
                            <%= user.getStatus() %>
                        </span>
                    </div>

                    <a href="manage-user?id=<%= user.getId() %>" class="manage-btn">
                        Manage User
                    </a>

                </div>
            </div>

            <%
                }
            %>

        </div>

    </div>

</div>

</body>
</html>