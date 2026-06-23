<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.session.model.Role" %>
<%@ page import="com.example.session.model.Permission" %>
<%
    Role role = (Role) request.getAttribute("role");
    List<Permission> permissions = (List<Permission>) request.getAttribute("permissions");
    List<Integer> assignedPermissionIds = (List<Integer>) request.getAttribute("assignedPermissionIds");

    if (role == null || permissions == null || assignedPermissionIds == null) {
        response.sendRedirect(request.getContextPath() + "/roles");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>i.Core — Manage Role</title>
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
                <a href="<%= request.getContextPath() %>/roles"
                   style="color:var(--text-3); text-decoration:none;">Roles</a>
                <span class="ic-breadcrumb-sep">/</span>
                <span><%= role.getRoleName() %></span>
            </div>
            <div>
                <a href="<%= request.getContextPath() %>/roles" class="ic-btn ic-btn-sm">
                    <i class="ti ti-arrow-left" style="font-size:14px;"></i> Back to roles
                </a>
            </div>
        </div>

        <div class="ic-content">

            <div class="ic-page-header">
                <div>
                    <div style="display:flex; align-items:center; gap:10px;">
                        <div style="width:38px; height:38px; border-radius:10px;
                                    background:var(--brand-light);
                                    display:flex; align-items:center; justify-content:center;">
                            <i class="ti ti-shield-lock" style="font-size:20px; color:var(--brand);"></i>
                        </div>
                        <div>
                            <div class="ic-page-title" style="font-family:var(--mono);">
                                <%= role.getRoleName() %>
                            </div>
                            <div class="ic-page-sub">
                                Role ID #<%= role.getId() %> &nbsp;&bull;&nbsp;
                                <span class="ic-tag <%= "ACTIVE".equalsIgnoreCase(role.getStatus()) ? "tag-active" : "tag-inactive" %>"
                                      style="font-size:11.5px;">
                                    <%= role.getStatus() %>
                                </span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="ic-grid-2">

                <%-- Info panel --%>
                <div>
                    <div class="ic-card ic-card-padded" style="margin-bottom:14px;">
                        <div style="font-size:13px; font-weight:500; color:var(--text-2); margin-bottom:14px;">
                            Role information
                        </div>
                        <div class="ic-info-row">
                            <span class="ic-info-label">Role name</span>
                            <span class="ic-info-mono"><%= role.getRoleName() %></span>
                        </div>
                        <div class="ic-info-row">
                            <span class="ic-info-label">Role ID</span>
                            <span class="ic-info-mono">#<%= role.getId() %></span>
                        </div>
                        <div class="ic-info-row">
                            <span class="ic-info-label">Status</span>
                            <span class="ic-tag <%= "ACTIVE".equalsIgnoreCase(role.getStatus()) ? "tag-active" : "tag-inactive" %>">
                                <%= role.getStatus() %>
                            </span>
                        </div>
                        <div class="ic-info-row">
                            <span class="ic-info-label">Assigned permissions</span>
                            <span class="ic-info-value"><%= assignedPermissionIds.size() %> / <%= permissions.size() %></span>
                        </div>
                    </div>

                    <div class="ic-card ic-card-padded"
                         style="background:var(--brand-light); border-color:#C7D2FE;">
                        <div style="font-size:13px; font-weight:500; color:var(--brand); margin-bottom:8px;
                                    display:flex; align-items:center; gap:6px;">
                            <i class="ti ti-info-circle"></i> How permissions work
                        </div>
                        <p style="font-size:12px; color:var(--brand); line-height:1.7; opacity:.85;">
                            Permissions are resolved from the database on every request — not cached.
                            Users assigned this role will immediately reflect any changes made here,
                            on their next request.
                        </p>
                    </div>
                </div>

                <%-- Permission editor --%>
                <div class="ic-card ic-card-padded">

                    <div style="font-size:14px; font-weight:500; color:var(--text-1); margin-bottom:4px;">
                        Permission rules
                    </div>
                    <div style="font-size:12px; color:var(--text-3); margin-bottom:16px;">
                        Check the permissions this role should have. Save to apply.
                    </div>

                    <form action="<%= request.getContextPath() %>/auth/roles/update-permissions"
                          method="post">

                        <input type="hidden" name="roleId" value="<%= role.getId() %>">

                        <%-- Select all toggle --%>
                        <div style="display:flex; align-items:center; gap:8px; margin-bottom:12px;
                                    padding-bottom:12px; border-bottom:1px solid var(--border);">
                            <input type="checkbox" id="selectAll"
                                   style="accent-color:var(--brand); transform:scale(1.15);">
                            <label for="selectAll"
                                   style="font-size:12px; font-weight:500; color:var(--text-2); cursor:pointer;">
                                Select / deselect all
                            </label>
                        </div>

                        <div style="display:flex; flex-direction:column; gap:6px;
                                    max-height:360px; overflow-y:auto; padding-right:4px;">
                            <%
                                for (Permission permission : permissions) {
                                    boolean checked = assignedPermissionIds.contains(permission.getId());
                            %>
                            <label class="ic-perm-item">
                                <input type="checkbox"
                                       class="perm-cb"
                                       name="permissionIds"
                                       value="<%= permission.getId() %>"
                                       <%= checked ? "checked" : "" %>>
                                <div>
                                    <div class="ic-perm-key"><%= permission.getPermissionKey() %></div>
                                    <div class="ic-perm-desc"><%= permission.getDescription() %></div>
                                </div>
                            </label>
                            <% } %>
                        </div>

                        <div style="margin-top:16px; display:flex; gap:10px;">
                            <a href="<%= request.getContextPath() %>/roles"
                               class="ic-btn" style="flex:1; justify-content:center;">
                                Cancel
                            </a>
                            <button type="submit"
                                    class="ic-btn ic-btn-primary"
                                    style="flex:2; justify-content:center;">
                                <i class="ti ti-device-floppy"></i> Save permissions
                            </button>
                        </div>

                    </form>
                </div>

            </div>

        </div>
    </main>
</div>

<script>
const selectAll = document.getElementById('selectAll');
const permCbs   = document.querySelectorAll('.perm-cb');

selectAll.addEventListener('change', function() {
    permCbs.forEach(cb => cb.checked = this.checked);
});

permCbs.forEach(cb => {
    cb.addEventListener('change', function() {
        selectAll.checked = [...permCbs].every(c => c.checked);
        selectAll.indeterminate = !selectAll.checked && [...permCbs].some(c => c.checked);
    });
});

// Set initial indeterminate state
const checkedCount = [...permCbs].filter(c => c.checked).length;
if (checkedCount > 0 && checkedCount < permCbs.length) selectAll.indeterminate = true;
else if (checkedCount === permCbs.length) selectAll.checked = true;
</script>

</body>
</html>
