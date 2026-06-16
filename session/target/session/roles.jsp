<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.session.model.Role" %>

<%
    List<Role> roles =
            (List<Role>) request.getAttribute("roles");

    if (roles == null) {
        response.sendRedirect(request.getContextPath() + "/roles");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Roles Management</title>

    <meta name="viewport"
          content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
          rel="stylesheet">

    <style>
        body {
            margin: 0;
            min-height: 100vh;
            background: #eef3f8;
            font-family: Arial, sans-serif;
        }

        .page-wrapper {
            padding: 35px 15px;
        }

        .roles-box {
            max-width: 1100px;
            margin: auto;
            background: linear-gradient(135deg, #062b52, #00508f);
            border-radius: 18px;
            padding: 40px;
            box-shadow: 0 18px 35px rgba(0,0,0,0.25);
        }

        .roles-header {
            color: white;
            margin-bottom: 25px;
        }

        .roles-header h1 {
            font-size: 36px;
            font-weight: 800;
            margin-bottom: 6px;
        }

        .roles-header p {
            margin: 0;
            opacity: .95;
        }

        .role-card {
            background: white;
            border-radius: 16px;
            padding: 22px;
            box-shadow: 0 10px 20px rgba(0,0,0,.15);
            height: 100%;
        }

        .role-name {
            color: #04336b;
            font-weight: 800;
            font-size: 20px;
            margin-bottom: 8px;
        }

        .role-id {
            color: #777;
            font-size: 13px;
            margin-bottom: 12px;
        }

        .status-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 50px;
            font-size: 12px;
            font-weight: 700;
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
            display: inline-block;
            margin-top: 16px;
            padding: 10px 16px;
            border-radius: 10px;
            text-decoration: none;
            color: white;
            font-weight: 700;
            background: linear-gradient(135deg, #0079d6, #00518d);
        }

        .manage-btn:hover {
            color: white;
            opacity: .95;
        }

        .create-box {
            background: white;
            border-radius: 16px;
            padding: 22px;
            margin-bottom: 25px;
            box-shadow: 0 10px 20px rgba(0,0,0,.15);
        }

        .btn-main {
            border: none;
            padding: 10px 16px;
            border-radius: 10px;
            color: white;
            font-weight: 700;
            background: linear-gradient(135deg, #0079d6, #00518d);
        }
    </style>
</head>

<body>

<%@ include file="navbar.jsp" %>

<div class="page-wrapper">

    <div class="roles-box">

        <div class="roles-header">
            <h1>Roles Management</h1>
            <p>Create custom roles and manage role based permission groups.</p>
        </div>

        <div class="create-box">
            <h5>Create Custom Role</h5>

            <form action="<%= request.getContextPath() %>/roles"
      method="post"
      class="row g-3">

                <div class="col-md-9">
                    <input type="text"
                           name="roleName"
                           class="form-control"
                           placeholder="Example: SUPPORT_ENGINEER"
                           required>
                </div>

                <div class="col-md-3">
                    <button type="submit"
                            class="btn-main w-100">
                        Create Role
                    </button>
                </div>

            </form>
        </div>

        <div class="row g-4">

            <%
                for (Role role : roles) {

                    String statusClass = "status-active";

                    if (!"ACTIVE".equalsIgnoreCase(role.getStatus())) {
                        statusClass = "status-inactive";
                    }
            %>

            <div class="col-md-4">

                <div class="role-card">

                    <div class="role-name">
                        <%= role.getRoleName() %>
                    </div>

                    <div class="role-id">
                        Role ID: #<%= role.getId() %>
                    </div>

                    <span class="status-badge <%= statusClass %>">
                        <%= role.getStatus() %>
                    </span>

                    <br>

                    <a href="<%= request.getContextPath() %>/manage-role?id=<%= role.getId() %>"
                       class="manage-btn">
                        Manage Permissions
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