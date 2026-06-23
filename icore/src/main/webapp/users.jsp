<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.session.model.User" %>
<%@ page import="com.example.session.util.PermissionValidator" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    List<User> users = (List<User>) request.getAttribute("users");

    if (users == null) {
        response.sendRedirect(request.getContextPath() + "/users");
        return;
    }

    int activeUsers = 0;
    int adminUsers  = 0;

    for (User user : users) {
        if ("ACTIVE".equalsIgnoreCase(user.getStatus())) activeUsers++;
        if ("ADMIN".equalsIgnoreCase(user.getRole()) || "SYS_ADMIN".equalsIgnoreCase(user.getRole())) adminUsers++;
    }

    User currentUser = (User) session.getAttribute("user");
    boolean canManageUsers = false;
    try {
        if (currentUser != null)
            canManageUsers = PermissionValidator.hasPermission(currentUser.getId(), "USER_UPDATE");
    } catch (Exception e) { canManageUsers = false; }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>i.Core — Users</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=JetBrains+Mono:wght@400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@3.x/tabler-icons.min.css">
    <link rel="stylesheet" href="assets/icore.css">
</head>
<body>

<div class="ic-shell">

    <%@ include file="navbar.jsp" %>

    <main class="ic-main">

        <div class="ic-topbar">
            <div class="ic-breadcrumb">
                <span>i.Core</span>
                <span class="ic-breadcrumb-sep">/</span>
                <span>Users</span>
            </div>
        </div>

        <div class="ic-content">

            <div class="ic-page-header">
                <div>
                    <div class="ic-page-title">User management</div>
                    <div class="ic-page-sub">Manage registered users, roles and account access.</div>
                </div>
            </div>

            <%-- Stat cards --%>
            <div class="ic-grid-3" style="margin-bottom: 20px;">

                <div class="ic-metric">
                    <div class="ic-metric-label">
                        <i class="ti ti-users" style="font-size:14px;"></i> Total users
                    </div>
                    <div class="ic-metric-value"><%= users.size() %></div>
                    <div class="ic-metric-sub">Registered accounts</div>
                </div>

                <div class="ic-metric">
                    <div class="ic-metric-label">
                        <i class="ti ti-circle-check" style="font-size:14px;color:var(--green);"></i> Active accounts
                    </div>
                    <div class="ic-metric-value" style="color:var(--green);"><%= activeUsers %></div>
                    <div class="ic-metric-sub">Currently enabled</div>
                </div>

                <div class="ic-metric">
                    <div class="ic-metric-label">
                        <i class="ti ti-shield-check" style="font-size:14px;color:var(--brand);"></i> Admin accounts
                    </div>
                    <div class="ic-metric-value" style="color:var(--brand);"><%= adminUsers %></div>
                    <div class="ic-metric-sub">SYS_ADMIN or ADMIN role</div>
                </div>

            </div>

            <%-- Search --%>
            <div style="margin-bottom:14px;">
                <div class="ic-search">
                    <i class="ti ti-search"></i>
                    <input type="text" id="searchUser"
                           placeholder="Search by name, username or email…">
                </div>
            </div>

            <%-- Table --%>
            <div class="ic-table-wrap">
                <div style="overflow-x:auto;">
                    <table class="ic-table" id="usersTable">
                        <thead>
                            <tr>
                                <th>User</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Status</th>
                                <th>ID</th>
                                <th>Action</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            String[] avColors = {"av-purple","av-blue","av-teal","av-amber","av-green"};
                            int avIdx = 0;

                            for (User user : users) {
                                pageContext.setAttribute("rowUser", user);
                                String username   = user.getUsername();
                                String displayName = user.getName();

                                String[] parts = displayName != null ? displayName.trim().split("\\s+") : new String[]{"U"};
                                String initials = parts.length >= 2
                                    ? String.valueOf(parts[0].charAt(0)) + String.valueOf(parts[1].charAt(0))
                                    : (parts[0].length() > 0 ? String.valueOf(parts[0].charAt(0)) : "U");

                                String statusClass  = "tag-active";
                                String statusIcon   = "ti-circle-check";
                                if (!"ACTIVE".equalsIgnoreCase(user.getStatus())) {
                                    statusClass = "tag-inactive";
                                    statusIcon  = "ti-circle-x";
                                }

                                String roleClass = "tag-generic";
                                String roleUpper = user.getRole() != null ? user.getRole().toUpperCase() : "";
                                if (roleUpper.contains("ADMIN"))   roleClass = "tag-admin";
                                else if (roleUpper.contains("AUDIT"))   roleClass = "tag-auditor";
                                else if (roleUpper.contains("MANAGER")) roleClass = "tag-manager";
                                else if (roleUpper.contains("USER"))    roleClass = "tag-user";

                                String avColor = avColors[avIdx % avColors.length];
                                pageContext.setAttribute("rowInitials", initials.toUpperCase());
                                avIdx++;
                        %>
                        <tr>
                            <td>
                                <div style="display:flex; align-items:center; gap:10px;">
                                    <div class="ic-avatar <%= avColor %>">
                                        <c:out value="${rowInitials}"/>
                                    </div>
                                    <div>
                                        <div style="font-weight:500; color:var(--text-1); font-size:13.5px;">
                                            <c:out value="${rowUser.name}"/>
                                        </div>
                                        <div style="font-size:12px; color:var(--text-3);">
                                            @<c:out value="${rowUser.username}"/>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td style="color:var(--text-2); font-size:13px;"><c:out value="${rowUser.email}"/></td>
                            <td>
                                <span class="ic-tag <%= roleClass %>">
                                    <c:out value="${rowUser.role}"/>
                                </span>
                            </td>
                            <td>
                                <span class="ic-tag <%= statusClass %>">
                                    <i class="ti <%= statusIcon %>" style="font-size:13px;"></i>
                                    <c:out value="${rowUser.status}"/>
                                </span>
                            </td>
                            <td>
                                <span style="font-family:var(--mono); font-size:12px; color:var(--text-3);">
                                    #<%= user.getId() %>
                                </span>
                            </td>
                            <td>
                                <% if (canManageUsers) { %>
                                <a href="<%= request.getContextPath() %>/manage-user?id=<%= user.getId() %>"
                                   class="ic-btn ic-btn-sm">
                                    <i class="ti ti-settings" style="font-size:14px;"></i> Manage
                                </a>
                                <% } else { %>
                                <span style="font-size:12px; color:var(--text-3);">View only</span>
                                <% } %>
                            </td>
                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>

            <div style="margin-top:12px; font-size:12px; color:var(--text-3);">
                Showing <%= users.size() %> registered users
            </div>

        </div>
    </main>
</div>

<script>
const searchInput = document.getElementById("searchUser");
const table = document.getElementById("usersTable");

searchInput.addEventListener("keyup", function () {
    const q = this.value.toLowerCase();
    table.querySelectorAll("tbody tr").forEach(function(row) {
        row.style.display = row.innerText.toLowerCase().includes(q) ? "" : "none";
    });
});
</script>

</body>
</html>
