<%@ page isELIgnored="false" %>
<%@ page import="com.example.session.model.User" %>
<%@ page import="com.example.session.util.PermissionValidator" %>

<%
    User navUser = (User) session.getAttribute("user");
    boolean canViewUsers  = false;
    boolean canViewLogs   = false;
    boolean canManageRoles = false;

    if (navUser != null) {
        canViewUsers   = PermissionValidator.hasPermission(navUser.getId(), "USER_READ");
        canViewLogs    = PermissionValidator.hasPermission(navUser.getId(), "LOG_VIEW");
        canManageRoles = PermissionValidator.hasPermission(navUser.getId(), "ROLE_UPDATE");
    }

    String currentPath = request.getServletPath();
%>

<%-- Determine initials for avatar --%>
<%
    String navName = (navUser != null && navUser.getName() != null) ? navUser.getName() : "User";
    String[] navParts = navName.trim().split("\\s+");
    String navInitials = navParts.length >= 2
        ? String.valueOf(navParts[0].charAt(0)) + String.valueOf(navParts[1].charAt(0))
        : String.valueOf(navParts[0].charAt(0));
    navInitials = navInitials.toUpperCase();
%>

<aside class="ic-sidebar">

    <%-- Logo --%>
    <div class="ic-sidebar-logo">
        <div class="ic-logo-mark">
            <i class="ti ti-cloud"></i>
        </div>
        <div>
            <div class="ic-logo-text">i.Core</div>
            <div class="ic-logo-ver">v1.2.0-STABLE</div>
        </div>
    </div>

    <%-- User pill --%>
    <div style="padding: 12px 10px;">
        <div style="display:flex; align-items:center; gap:9px; padding: 8px 10px;
                    background: var(--surface-2); border-radius: var(--radius-sm);">
            <div class="ic-avatar av-purple" style="font-size:12px;">
                <%= navInitials %>
            </div>
            <div style="overflow:hidden;">
                <div style="font-size:13px; font-weight:500; color:var(--text-1);
                            white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">
                    <%= navUser != null ? navUser.getName() : "Guest" %>
                </div>
                <div style="font-size:11px; color:var(--text-3);">
                    <%= navUser != null ? navUser.getRole() : "" %>
                </div>
            </div>
        </div>
    </div>

    <%-- Main nav --%>
    <div class="ic-nav-section">Main</div>

    <a href="<%= request.getContextPath() %>/welcome"
       class="ic-nav-item <%= currentPath != null && currentPath.contains("welcome") ? "active" : "" %>">
        <i class="ti ti-layout-dashboard"></i> Dashboard
    </a>

        <a href="<%= request.getContextPath() %>/profile"
       class="ic-nav-item <%= currentPath != null && currentPath.contains("welcome") ? "active" : "" %>">
        <i class="ti ti-layout-dashboard"></i> profile
    </a>

    <% if (canViewUsers) { %>
    <a href="<%= request.getContextPath() %>/users"
       class="ic-nav-item <%= currentPath != null && currentPath.contains("users") ? "active" : "" %>">
        <i class="ti ti-users"></i> Users
    </a>
    <% } %>

    <% if (canManageRoles) { %>
    <a href="<%= request.getContextPath() %>/roles"
       class="ic-nav-item <%= currentPath != null && currentPath.contains("roles") ? "active" : "" %>">
        <i class="ti ti-shield-lock"></i> Roles
    </a>
    <% } %>

    <%-- System nav --%>
    <div class="ic-nav-section">System</div>

    <% if (canViewLogs) { %>
    <a href="<%= request.getContextPath() %>/logs"
       class="ic-nav-item <%= currentPath != null && currentPath.contains("logs") ? "active" : "" %>">
        <i class="ti ti-file-description"></i> Audit Logs
    </a>
    <% } %>

    <a href="<%= request.getContextPath() %>/settings"
       class="ic-nav-item <%= currentPath != null && currentPath.contains("settings") ? "active" : "" %>">
        <i class="ti ti-settings"></i> Settings
    </a>

    <%-- Footer --%>
    <div class="ic-sidebar-footer">
        <form action="<%= request.getContextPath() %>/logout" method="post" style="margin:0;">
            <button type="submit" class="ic-nav-item danger"
                    style="width:100%; border:none; background:none; cursor:pointer; text-align:left;"
                    onclick="return confirm('Sign out of i.Core?')">
                <i class="ti ti-logout"></i> Sign out
            </button>
        </form>
    </div>

</aside>

<script>
function confirmLogout() {
    return confirm("Sign out of i.Core?");
}
</script>
