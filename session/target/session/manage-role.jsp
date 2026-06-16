<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<%@ page import="java.util.List" %>
<%@ page import="com.example.session.model.Role" %>
<%@ page import="com.example.session.model.Permission" %>

<%
    Role role =
            (Role) request.getAttribute("role");

    List<Permission> permissions =
            (List<Permission>) request.getAttribute("permissions");

    List<Integer> assignedPermissionIds =
            (List<Integer>) request.getAttribute("assignedPermissionIds");

    if (role == null || permissions == null || assignedPermissionIds == null) {
        response.sendRedirect(request.getContextPath() + "/roles");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Manage Role Permissions</title>

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
            background: #f4f4f4;
        }

        .page-wrapper {
            min-height: calc(100vh - 70px);
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 15px;
        }

        .welcome-box {
            width: 1000px;
            min-height: 560px;
            background: linear-gradient(135deg, #0399f5, #00508f);
            border-radius: 18px;
            display: flex;
            align-items: stretch;
            justify-content: space-between;
            padding: 55px;
            box-shadow: 0 18px 35px rgba(0,0,0,0.25);
            position: relative;
            overflow: hidden;
            gap: 35px;
        }

        .welcome-box::before {
            content: "";
            position: absolute;
            width: 420px;
            height: 420px;
            background: rgba(255,255,255,0.08);
            border-radius: 50%;
            left: -120px;
            top: -70px;
        }

        .welcome-box::after {
            content: "";
            position: absolute;
            width: 360px;
            height: 360px;
            background: rgba(0,180,255,0.35);
            border-radius: 50%;
            left: -40px;
            bottom: -170px;
        }

        .welcome-left {
            color: white;
            width: 38%;
            z-index: 1;
        }

        .welcome-left h1 {
            font-size: 38px;
            font-weight: 800;
            letter-spacing: 1px;
            margin-bottom: 15px;
        }

        .welcome-left h4 {
            font-size: 18px;
            font-weight: 700;
            margin-bottom: 15px;
        }

        .welcome-left p {
            font-size: 14px;
            line-height: 1.8;
            opacity: 0.95;
        }

        .role-badge {
            display: inline-block;
            margin-top: 12px;
            padding: 8px 14px;
            border-radius: 50px;
            background: rgba(255,255,255,0.18);
            border: 1px solid rgba(255,255,255,0.35);
            font-size: 13px;
            font-weight: 700;
        }

        .user-card {
            width: 560px;
            background: white;
            border-radius: 18px;
            padding: 35px;
            z-index: 1;
            box-shadow: 0 12px 25px rgba(0,0,0,0.25);
        }

        .user-card h3 {
            color: #04336b;
            font-size: 26px;
            font-weight: 800;
            margin-bottom: 8px;
        }

        .user-card small {
            color: #777;
            display: block;
            margin-bottom: 22px;
        }

        .permission-list {
            max-height: 360px;
            overflow-y: auto;
            padding-right: 6px;
        }

        .permission-item {
            border: 1px solid #e5e5e5;
            border-radius: 12px;
            padding: 13px 14px;
            margin-bottom: 12px;
            background: #fafafa;
            display: flex;
            gap: 12px;
            align-items: flex-start;
        }

        .permission-item input {
            margin-top: 4px;
            transform: scale(1.15);
        }

        .permission-key {
            color: #04336b;
            font-weight: 800;
            font-size: 14px;
        }

        .permission-desc {
            color: #777;
            font-size: 13px;
            margin-top: 3px;
        }

        .btn-main {
            width: 100%;
            margin-top: 18px;
            padding: 13px;
            border: none;
            border-radius: 10px;
            background: linear-gradient(135deg, #0079d6, #00518d);
            color: white;
            font-weight: 800;
        }

        .back-link {
            display: inline-block;
            margin-top: 18px;
            color: white;
            text-decoration: none;
            font-weight: 700;
        }

        .back-link:hover {
            color: white;
            text-decoration: underline;
        }

        @media(max-width: 768px) {
            .welcome-box {
                width: 92%;
                flex-direction: column;
                padding: 35px 22px;
                gap: 30px;
            }

            .welcome-left,
            .user-card {
                width: 100%;
            }
        }
    </style>
</head>

<body>

<%@ include file="navbar.jsp" %>

<div class="page-wrapper">

    <div class="welcome-box">

        <div class="welcome-left">

            <h1>Manage Role</h1>

            <h4>
                <%= role.getRoleName() %>
            </h4>

            <p>
                Enable or disable permission rules for this role.
                Users assigned to this role will receive access based on
                the selected permissions.
            </p>

            <div class="role-badge">
                Role ID: #<%= role.getId() %>
            </div>

            <br>

            <div class="role-badge">
                Status: <%= role.getStatus() %>
            </div>

            <br>

            <a href="<%= request.getContextPath() %>/roles"
               class="back-link">
                ← Back to Roles
            </a>

        </div>

        <div class="user-card">

            <h3>Permission Rules</h3>
            <small>Select the permissions allowed for this role.</small>

            <form action="<%= request.getContextPath() %>/auth/roles/update-permissions"
                  method="post">

                <input type="hidden"
                       name="roleId"
                       value="<%= role.getId() %>">

                <div class="permission-list">

                    <%
                        for (Permission permission : permissions) {

                            boolean checked =
                                    assignedPermissionIds.contains(
                                            permission.getId()
                                    );
                    %>

                    <label class="permission-item">

                        <input type="checkbox"
                               name="permissionIds"
                               value="<%= permission.getId() %>"
                               <%= checked ? "checked" : "" %>>

                        <div>
                            <div class="permission-key">
                                <%= permission.getPermissionKey() %>
                            </div>

                            <div class="permission-desc">
                                <%= permission.getDescription() %>
                            </div>
                        </div>

                    </label>

                    <%
                        }
                    %>

                </div>

                <button type="submit"
                        class="btn-main">
                    Update Permissions
                </button>

            </form>

        </div>

    </div>

</div>

</body>
</html>