<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.session.model.User" %>
<%@ page import="com.example.session.util.PermissionValidator" %>

<%
    List<User> users = (List<User>) request.getAttribute("users");

    if (users == null) {
        response.sendRedirect(request.getContextPath() + "/users");
        return;
    }

    int activeUsers = 0;
    int adminUsers = 0;

    for (User user : users) {

        if ("ACTIVE".equalsIgnoreCase(user.getStatus())) {
            activeUsers++;
        }

        if ("ADMIN".equalsIgnoreCase(user.getRole())
                || "SYS_ADMIN".equalsIgnoreCase(user.getRole())) {
            adminUsers++;
        }
    }

    User currentUser =
            (User) session.getAttribute("user");

    boolean canManageUsers = false;

    try {

        if (currentUser != null) {

            canManageUsers =
                    PermissionValidator.hasPermission(
                            currentUser.getId(),
                            "USER_UPDATE"
                    );
        }

    } catch (Exception e) {

        canManageUsers = false;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Users Management</title>

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">
    <style>
        * {
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: #eef3f8;
        }

        .page-wrapper {
            padding: 35px 15px;
        }

        .users-box {
            max-width: 1300px;
            margin: auto;
            background: linear-gradient(135deg, #062b52, #00508f);
            border-radius: 18px;
            padding: 40px;
            box-shadow: 0 18px 35px rgba(0, 0, 0, 0.25);
            position: relative;
            overflow: hidden;
        }

        .users-box::before {
            content: "";
            position: absolute;
            width: 420px;
            height: 420px;
            background: rgba(255, 255, 255, 0.08);
            border-radius: 50%;
            left: -120px;
            top: -70px;
        }

        .users-header {
            position: relative;
            z-index: 1;
            color: white;
            margin-bottom: 30px;
        }

        .users-header h1 {
            font-size: 38px;
            font-weight: 800;
            margin-bottom: 8px;
        }

        .users-header p {
            margin: 0;
            opacity: .95;
        }

        .stat-card {
            position: relative;
            z-index: 1;
            background: white;
            border-radius: 16px;
            padding: 22px;
            text-align: center;
            box-shadow: 0 10px 20px rgba(0, 0, 0, .15);
            height: 100%;
        }

        .stat-card h3 {
            color: #00508f;
            font-weight: 800;
            margin: 0;
            font-size: 30px;
        }

        .stat-card span {
            color: #666;
            font-size: 14px;
        }

        .search-box {
            position: relative;
            z-index: 1;
            margin-bottom: 20px;
        }

        .search-box input {
            border-radius: 12px;
            padding: 12px 16px;
            border: none;
        }

        .table-container {
            position: relative;
            z-index: 1;
            background: white;
            border-radius: 18px;
            overflow: hidden;
            box-shadow: 0 12px 25px rgba(0, 0, 0, .15);
        }

        .cloud-table {
            margin: 0;
        }

        .cloud-table thead {
            background: #004d88;
            color: white;
        }

        .cloud-table th {
            padding: 18px;
            border: none;
            font-weight: 700;
        }

        .cloud-table td {
            padding: 16px;
            vertical-align: middle;
        }

        .cloud-table tbody tr:hover {
            background: #f5faff;
        }

        .table-avatar {
            width: 52px;
            height: 52px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #eaf7ff;
        }

        .user-name {
            font-weight: 700;
            color: #04336b;
        }

        .username {
            color: #777;
            font-size: 13px;
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
            border: none;
            padding: 10px 16px;
            border-radius: 10px;
            text-decoration: none;
            color: white;
            font-weight: 700;
            background: linear-gradient(135deg, #0079d6, #00518d);
            display: inline-block;
        }

        .manage-btn:hover {
            color: white;
            opacity: .95;
        }

        @media (max-width: 768px) {
            .users-box {
                padding: 25px;
            }

            .users-header h1 {
                font-size: 28px;
            }

            .cloud-table {
                min-width: 900px;
            }
        }
    </style>
</head>

<body>

<%@ include file="navbar.jsp" %>

<div class="page-wrapper">

    <div class="users-box">

        <div class="users-header">
            <h1>Users Management</h1>
            <p>Manage registered users, roles and account access.</p>
        </div>

        <div class="row g-3 mb-4">

            <div class="col-md-4">
                <div class="stat-card">
                    <h3><%= users.size() %></h3>
                    <span>Total Users</span>
                </div>
            </div>

            <div class="col-md-4">
                <div class="stat-card">
                    <h3><%= activeUsers %></h3>
                    <span>Active Accounts</span>
                </div>
            </div>

            <div class="col-md-4">
                <div class="stat-card">
                    <h3><%= adminUsers %></h3>
                    <span>Administrative Accounts</span>
                </div>
            </div>

        </div>

        <div class="search-box">
            <input
                    type="text"
                    id="searchUser"
                    class="form-control"
                    placeholder="Search users by name, username or email...">
        </div>

        <div class="table-container">
            <div class="table-responsive">

                <table class="table cloud-table align-middle" id="usersTable">

                    <thead>
                    <tr>
                        <th>User</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Status</th>
                        <th>User ID</th>
                        <th>Action</th>
                    </tr>
                    </thead>

                    <tbody>

                    <%
                        for (User user : users) {

                            String username = user.getUsername();
                            String avatarUrl = "https://unavatar.io/github/" + username;

                            String statusClass = "status-active";

                            if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
                                statusClass = "status-inactive";
                            }
                    %>

                    <tr>

                        <td>
                            <div class="d-flex align-items-center">
                                <img src="<%= avatarUrl %>"
                                     class="table-avatar"
                                     alt="<%= username %>">

                                <div class="ms-3">
                                    <div class="user-name">
                                        <%= user.getName() %>
                                    </div>

                                    <div class="username">
                                        @<%= user.getUsername() %>
                                    </div>
                                </div>
                            </div>
                        </td>

                        <td>                            <%= user.getEmail() %>
                        </td>

                        <td>
                            <span class="role-badge">
                                <%= user.getRole() %>
                            </span>
                        </td>

                        <td>
                            <span class="role-badge <%= statusClass %>">
                                <%= user.getStatus() %>
                            </span>
                        </td>

                        <td>
                            #<%= user.getId() %>
                        </td>

                        <td>
                            <% if (canManageUsers) { %>
                            <a href="manage-user?id=<%= user.getId() %>"
                               class="manage-btn">
                                Manage User
                            </a>
                            <% } else { %>
                            <span class="text-muted">
                                View Only
                            </span>
                            <% } %>
                        </td>

                    </tr>

                    <%
                        }
                    %>

                    </tbody>

                </table>

            </div>
        </div>

    </div>

</div>

<script>
const searchInput = document.getElementById("searchUser");
const table = document.getElementById("usersTable");

searchInput.addEventListener("keyup", function () {
    const searchValue = this.value.toLowerCase();
    const rows = table.querySelectorAll("tbody tr");

    rows.forEach(function (row) {
        const rowText = row.innerText.toLowerCase();

        if (rowText.includes(searchValue)) {
            row.style.display = "";
        } else {
            row.style.display = "none";
        }
    });
});
</script>

</body>
</html>