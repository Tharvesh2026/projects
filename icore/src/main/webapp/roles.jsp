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

    <meta name="viewport" content="width=device-width, initial-scale=1.0">

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
            max-width: 1200px;
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
            padding: 8px 12px;
            border-radius: 10px;
            text-decoration: none;
            color: white;
            font-weight: 700;
            background: linear-gradient(135deg, #0079d6, #00518d);
        }

        .role-table {
            background: white;
            border-radius: 12px;
            overflow: hidden;
            box-shadow: 0 10px 20px rgba(0,0,0,.15);
        }

        .role-table th {
            color: #04336b;
            font-weight: 700;
        }

        .role-table td {
            vertical-align: middle;
        }

        .role-name {
            font-weight: 800;
            color: #04336b;
        }

        .role-id {
            color: #777;
            font-size: 13px;
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

        <!-- CREATE ROLE -->
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
                    <button type="submit" class="btn-main w-100">
                        Create Role
                    </button>
                </div>

            </form>
        </div>

        <!-- TABLE -->
        <div class="table-responsive">

            <table class="table role-table">

                <thead class="table-light">
                <tr>
                    <th>Role</th>
                    <th>Role ID</th>
                    <th>Status</th>
                    <th>Manage</th>
                    <th>Rename</th>
                    <th>Update Status</th>
                </tr>
                </thead>

                <tbody>

                <%
                    for (Role role : roles) {

                        String statusClass = "status-active";

                        if (!"ACTIVE".equalsIgnoreCase(role.getStatus())) {
                            statusClass = "status-inactive";
                        }
                %>

                <tr>

                    <td class="role-name">
                        <%= role.getRoleName() %>
                    </td>

                    <td class="role-id">
                        #<%= role.getId() %>
                    </td>

                    <td>
                        <span class="status-badge <%= statusClass %>">
                            <%= role.getStatus() %>
                        </span>
                    </td>

                    <td>
                        <a class="manage-btn"
                           href="<%= request.getContextPath() %>/manage-role?id=<%= role.getId() %>">
                            Manage
                        </a>
                    </td>

                    <!-- RENAME -->
                    <td>

                        <form action="<%= request.getContextPath() %>/roles/rename"
                              method="post"
                              class="d-flex flex-column gap-2">

                            <input type="hidden" name="roleId" value="<%= role.getId() %>">

                            <input type="text"
                                   name="roleName"
                                   class="form-control"
                                   value="<%= role.getRoleName() %>"
                                   required>

                            <button type="submit" class="btn-main">
                                Rename
                            </button>

                        </form>

                    </td>

                    <!-- STATUS -->
                    <td>

                        <form action="<%= request.getContextPath() %>/roles/status"
                              method="post"
                              class="d-flex flex-column gap-2">

                            <input type="hidden" name="roleId" value="<%= role.getId() %>">

                            <select name="status" class="form-control">

                                <option value="ACTIVE"
                                        <%= "ACTIVE".equalsIgnoreCase(role.getStatus()) ? "selected" : "" %>>
                                    ACTIVE
                                </option>

                                <option value="INACTIVE"
                                        <%= "INACTIVE".equalsIgnoreCase(role.getStatus()) ? "selected" : "" %>>
                                    INACTIVE
                                </option>

                            </select>

                            <button type="submit" class="btn-main">
                                Update
                            </button>

                        </form>

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

</body>
</html>