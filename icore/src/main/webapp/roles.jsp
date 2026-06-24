<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.session.model.Role" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    List<Role> roles = (List<Role>) request.getAttribute("roles");
    if (roles == null) {
        response.sendRedirect(request.getContextPath() + "/roles");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>i.Core — Roles</title>
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
                <span>Roles</span>
            </div>
        </div>

        <div class="ic-content">

            <div class="ic-page-header">
                <div>
                    <div class="ic-page-title">Role management</div>
                    <div class="ic-page-sub">
                        Create custom roles and manage permission groups.
                    </div>
                </div>
            </div>

            <%-- Create new role --%>
            <div class="ic-card ic-card-padded" style="margin-bottom: 20px;">
                <div style="font-size:14px; font-weight:500; color:var(--text-1); margin-bottom:14px;
                            display:flex; align-items:center; gap:7px;">
                    <i class="ti ti-plus-circle" style="font-size:18px; color:var(--brand);"></i>
                    Create a new role
                </div>
                <form action="<%= request.getContextPath() %>/roles" method="post">
                    <div style="display:flex; gap:10px; align-items:flex-end; flex-wrap:wrap;">
                        <div style="flex:1; min-width:220px;">
                            <label class="ic-label">Role name</label>
                            <div class="ic-input-icon">
                                <i class="ti ti-shield"></i>
                                <input type="text" class="ic-input" name="roleName"
                                       placeholder="e.g. SUPPORT_ENGINEER" required
                                       style="text-transform:uppercase;"
                                       oninput="this.value=this.value.toUpperCase().replace(/\s/g,'_')">
                            </div>
                        </div>
                        <div>
                            <button type="submit" class="ic-btn ic-btn-primary"
                                    style="padding: 8px 18px; height:38px;">
                                <i class="ti ti-plus"></i> Create role
                            </button>
                        </div>
                    </div>
                    <div style="font-size:12px; color:var(--text-3); margin-top:6px;">
                        New roles are assigned <code style="background:var(--surface-2);padding:1px 5px;border-radius:4px;">PROFILE_READ</code>
                        permission by default.
                    </div>
                </form>
            </div>

            <%-- Roles table --%>
            <div class="ic-table-wrap">
                <div style="overflow-x:auto;">
                    <table class="ic-table">
                        <thead>
                            <tr>
                                <th>Role</th>
                                <th>ID</th>
                                <th>Status</th>
                                <th>Permissions</th>
                                <th>Rename</th>
                                <th>Update status</th>
                            </tr>
                        </thead>
                        <tbody>
                        <%
                            for (Role role : roles) {
                                pageContext.setAttribute("rowRole", role);
                                boolean isActive = "ACTIVE".equalsIgnoreCase(role.getStatus());
                                String roleUpper = role.getRoleName() != null ? role.getRoleName().toUpperCase() : "";
                                String roleClass = "tag-generic";
                                if (roleUpper.contains("ADMIN"))   roleClass = "tag-admin";
                                else if (roleUpper.contains("AUDIT"))   roleClass = "tag-auditor";
                                else if (roleUpper.contains("MANAGER")) roleClass = "tag-manager";
                                else if (roleUpper.contains("USER"))    roleClass = "tag-user";
                        %>
                        <tr>
                            <td>
                                <div style="display:flex; align-items:center; gap:9px;">
                                    <div style="width:30px; height:30px; border-radius:8px;
                                                background:var(--brand-light);
                                                display:flex; align-items:center; justify-content:center;">
                                        <i class="ti ti-shield-lock" style="font-size:15px; color:var(--brand);"></i>
                                    </div>
                                    <div>
                                        <div style="font-weight:600; color:var(--text-1); font-size:13.5px;
                                                    font-family:var(--mono);">
                                            <c:out value="${rowRole.roleName}"/>
                                        </div>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <span style="font-family:var(--mono); font-size:12px; color:var(--text-3);">
                                    #<%= role.getId() %>
                                </span>
                            </td>
                            <td>
                                <span class="ic-tag <%= isActive ? "tag-active" : "tag-inactive" %>">
                                    <i class="ti <%= isActive ? "ti-circle-check" : "ti-circle-x" %>"
                                       style="font-size:13px;"></i>
                                    <c:out value="${rowRole.status}"/>
                                </span>
                            </td>
                            <td>
                                <a href="<%= request.getContextPath() %>/manage-role?id=<%= role.getId() %>"
                                   class="ic-btn ic-btn-sm">
                                    <i class="ti ti-adjustments" style="font-size:14px;"></i> Manage
                                </a>
                            </td>

                            <%-- Rename form --%>
                            <td>
                                <form action="<%= request.getContextPath() %>/roles/rename"
                                      method="post"
                                      style="display:flex; gap:6px; align-items:center;">
                                    <input type="hidden" name="roleId" value="<%= role.getId() %>">
                                    <input type="text" class="ic-input" name="roleName"
                                           value="<c:out value='${rowRole.roleName}'/>"
                                           style="width:150px; font-family:var(--mono); font-size:12px;"
                                           oninput="this.value=this.value.toUpperCase().replace(/\s/g,'_')"
                                           required>
                                    <button type="submit" class="ic-btn ic-btn-sm">
                                        <i class="ti ti-pencil" style="font-size:13px;"></i>
                                    </button>
                                </form>
                            </td>

                            <%-- Status form --%>
                            <td>
                                <form action="<%= request.getContextPath() %>/roles/status"
                                      method="post"
                                      style="display:flex; gap:6px; align-items:center;">
                                    <input type="hidden" name="roleId" value="<%= role.getId() %>">
                                    <select name="status" class="ic-input ic-select"
                                            style="width:130px; font-size:12px;">
                                        <option value="ACTIVE"
                                                <%= "ACTIVE".equalsIgnoreCase(role.getStatus()) ? "selected" : "" %>>
                                            ACTIVE
                                        </option>
                                        <option value="INACTIVE"
                                                <%= "INACTIVE".equalsIgnoreCase(role.getStatus()) ? "selected" : "" %>>
                                            INACTIVE
                                        </option>
                                    </select>
                                    <button type="submit" class="ic-btn ic-btn-sm">
                                        <i class="ti ti-check" style="font-size:13px;"></i>
                                    </button>
                                </form>
                            </td>

                        </tr>
                        <% } %>
                        </tbody>
                    </table>
                </div>
            </div>

            <div style="margin-top:12px; font-size:12px; color:var(--text-3);">
                <%= roles.size() %> role<%= roles.size() != 1 ? "s" : "" %> configured
            </div>

        </div>
    </main>
</div>

</body>
</html>
