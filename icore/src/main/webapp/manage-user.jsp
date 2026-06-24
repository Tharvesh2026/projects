<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.example.session.model.User" %>
<%@ page import="com.example.session.model.Role" %>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%
    User selectedUser = (User) request.getAttribute("selectedUser");

    if (selectedUser == null) {
        response.sendRedirect(request.getContextPath() + "/users");
        return;
    }

    List<Role> roles = (List<Role>) request.getAttribute("roles");

    String[] parts = selectedUser.getName() != null
        ? selectedUser.getName().trim().split("\\s+") : new String[]{"U"};
    String initials = parts.length >= 2
        ? String.valueOf(parts[0].charAt(0)) + String.valueOf(parts[1].charAt(0))
        : (parts[0].length() > 0 ? String.valueOf(parts[0].charAt(0)) : "U");

    String statusTagClass = "ACTIVE".equalsIgnoreCase(selectedUser.getStatus())   ? "tag-active"
                          : "LOCKED".equalsIgnoreCase(selectedUser.getStatus())   ? "tag-locked"
                          : "tag-inactive";
    pageContext.setAttribute("selectedUser", selectedUser);
    pageContext.setAttribute("selectedInitials", initials.toUpperCase());
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>i.Core — Manage User</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=JetBrains+Mono:wght@400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@3.x/tabler-icons.min.css">
    <link rel="stylesheet" href="assets/icore.css">
</head>
<body>

<div class="ic-shell">

    <%@ include file="navbar.jsp" %>

    <main class="ic-main">

        <%-- Topbar --%>
        <div class="ic-topbar">
            <div class="ic-topbar-left">
                <div class="ic-breadcrumb">
                    <span>i.Core</span>
                    <span class="ic-breadcrumb-sep">/</span>
                    <a href="<%= request.getContextPath() %>/users"
                       style="color:var(--text-3); text-decoration:none;">Users</a>
                    <span class="ic-breadcrumb-sep">/</span>
                    <span><c:out value="${selectedUser.username}"/></span>
                </div>
            </div>
            <div>
                <a href="<%= request.getContextPath() %>/users" class="ic-btn ic-btn-sm">
                    <i class="ti ti-arrow-left" style="font-size:14px;"></i> Back to users
                </a>
            </div>
        </div>

        <div class="ic-content">

            <%-- Alerts --%>
            <% if (request.getParameter("success") != null) { %>
            <div class="ic-alert ic-alert-success">
                <i class="ti ti-circle-check"></i>
                <c:out value="${param.success}"/>
            </div>
            <% } %>

            <% if (request.getParameter("error") != null) { %>
            <div class="ic-alert ic-alert-error">
                <i class="ti ti-alert-circle"></i>
                <c:out value="${param.error}"/>
            </div>
            <% } %>

            <%-- Profile header card --%>
            <div class="ic-card" style="margin-bottom:20px; overflow:hidden;">

                <%-- Banner --%>
                <div style="height:72px;
                            background: linear-gradient(135deg, #312E81 0%, #4338CA 60%, #6366F1 100%);
                            position:relative;">
                </div>

                <div style="padding: 0 24px 22px; position:relative;">

                    <%-- Avatar overlapping banner --%>
                    <div class="ic-avatar ic-avatar-lg av-purple"
                         style="position:absolute; top:-26px; left:24px;
                                border:3px solid var(--surface); font-size:18px;">
                        <c:out value="${selectedInitials}"/>
                    </div>

                    <div style="padding-top:36px; display:flex;
                                justify-content:space-between; align-items:flex-start; flex-wrap:wrap; gap:12px;">
                        <div>
                            <div style="font-size:17px; font-weight:600; color:var(--text-1);">
                                <c:out value="${selectedUser.name}"/>
                            </div>
                            <div style="font-size:13px; color:var(--text-3); margin-top:3px;">
                                @<c:out value="${selectedUser.username}"/>
                                &nbsp;&bull;&nbsp;
                                <c:out value="${selectedUser.email}"/>
                            </div>
                            <div style="margin-top:8px; display:flex; gap:6px; flex-wrap:wrap;">
                                <%
                                    String roleUpper = selectedUser.getRole() != null ? selectedUser.getRole().toUpperCase() : "";
                                    String roleTagClass = "tag-generic";
                                    if (roleUpper.contains("ADMIN"))    roleTagClass = "tag-admin";
                                    else if (roleUpper.contains("AUDIT"))    roleTagClass = "tag-auditor";
                                    else if (roleUpper.contains("MANAGER")) roleTagClass = "tag-manager";
                                    else if (roleUpper.contains("USER"))    roleTagClass = "tag-user";
                                %>
                                <span class="ic-tag <%= roleTagClass %>">
                                    <i class="ti ti-shield" style="font-size:12px;"></i>
                                    <c:out value="${selectedUser.role}"/>
                                </span>
                                <span class="ic-tag <%= statusTagClass %>">
                                    <i class="ti ti-circle-check" style="font-size:12px;"></i>
                                    <c:out value="${selectedUser.status}"/>
                                </span>
                            </div>
                        </div>

                        <%-- Quick stat pills --%>
                        <div style="display:flex; gap:10px;">
                            <div style="text-align:center; padding:8px 14px;
                                        background:var(--surface-2); border-radius:var(--radius-sm);">
                                <div style="font-size:10px; color:var(--text-3); margin-bottom:2px;">User ID</div>
                                <div style="font-family:var(--mono); font-size:13px; font-weight:500;
                                            color:var(--text-1);">#<%= selectedUser.getId() %></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <%-- Three action panels --%>
            <div class="ic-grid-3">

                <%-- ① Change Role --%>
                <div class="ic-card ic-card-padded">
                    <div style="display:flex; align-items:center; gap:8px; margin-bottom:16px;">
                        <div style="width:32px; height:32px; border-radius:8px;
                                    background:var(--brand-light);
                                    display:flex; align-items:center; justify-content:center;">
                            <i class="ti ti-shield-lock" style="font-size:17px; color:var(--brand);"></i>
                        </div>
                        <div>
                            <div style="font-size:14px; font-weight:600; color:var(--text-1);">Change role</div>
                            <div style="font-size:12px; color:var(--text-3);">Reassign to a different role</div>
                        </div>
                    </div>

                    <form action="<%= request.getContextPath() %>/manage-user" method="post">
                        <input type="hidden" name="id"     value="<%= selectedUser.getId() %>">
                        <input type="hidden" name="action" value="updateRole">

                        <div class="ic-form-group">
                            <label class="ic-label">Select new role</label>
                            <select name="roleId" class="ic-input ic-select">
                                <% if (roles != null) {
                                    for (Role role : roles) { pageContext.setAttribute("roleOption", role); %>
                                <option value="<%= role.getId() %>"
                                        <%= role.getRoleName().equals(selectedUser.getRole()) ? "selected" : "" %>>
                                    <c:out value="${roleOption.roleName}"/>
                                </option>
                                <% } } %>
                            </select>
                        </div>

                        <div style="padding:10px 12px; background:var(--brand-light);
                                    border:1px solid #C7D2FE; border-radius:var(--radius-sm);
                                    font-size:12px; color:var(--brand); margin-bottom:14px;">
                            <i class="ti ti-info-circle" style="font-size:14px;"></i>
                            Permissions update instantly on the user's next request.
                        </div>

                        <button type="submit" class="ic-btn ic-btn-primary" style="width:100%; justify-content:center;">
                            <i class="ti ti-device-floppy"></i> Update role
                        </button>
                    </form>
                </div>

                <%-- ② Change Status --%>
                <div class="ic-card ic-card-padded">
                    <div style="display:flex; align-items:center; gap:8px; margin-bottom:16px;">
                        <div style="width:32px; height:32px; border-radius:8px;
                                    background:#FEF3C7;
                                    display:flex; align-items:center; justify-content:center;">
                            <i class="ti ti-toggle-right" style="font-size:17px; color:var(--amber);"></i>
                        </div>
                        <div>
                            <div style="font-size:14px; font-weight:600; color:var(--text-1);">Account status</div>
                            <div style="font-size:12px; color:var(--text-3);">Enable, lock or disable access</div>
                        </div>
                    </div>

                    <form action="<%= request.getContextPath() %>/manage-user" method="post">
                        <input type="hidden" name="id"     value="<%= selectedUser.getId() %>">
                        <input type="hidden" name="action" value="updateStatus">

                        <div class="ic-form-group">
                            <label class="ic-label">New status</label>
                            <select name="status" class="ic-input ic-select">
                                <option value="ACTIVE"
                                        <%= "ACTIVE".equals(selectedUser.getStatus())    ? "selected" : "" %>>
                                    ACTIVE
                                </option>
                                <option value="DISABLED"
                                        <%= "DISABLED".equals(selectedUser.getStatus())  ? "selected" : "" %>>
                                    DISABLED
                                </option>
                                <option value="LOCKED"
                                        <%= "LOCKED".equals(selectedUser.getStatus())    ? "selected" : "" %>>
                                    LOCKED
                                </option>
                            </select>
                        </div>

                        <div style="display:flex; flex-direction:column; gap:6px; margin-bottom:14px;">
                            <div style="display:flex; align-items:center; gap:8px; font-size:12px; color:var(--text-3);">
                                <span class="ic-dot ic-dot-green"></span> ACTIVE — full access
                            </div>
                            <div style="display:flex; align-items:center; gap:8px; font-size:12px; color:var(--text-3);">
                                <span class="ic-dot ic-dot-red"></span> DISABLED — cannot log in
                            </div>
                            <div style="display:flex; align-items:center; gap:8px; font-size:12px; color:var(--text-3);">
                                <span class="ic-dot ic-dot-amber"></span> LOCKED — temporarily blocked
                            </div>
                        </div>

                        <button type="submit" class="ic-btn" style="width:100%; justify-content:center;
                                background:#FEF3C7; border-color:#FDE68A; color:var(--amber);">
                            <i class="ti ti-toggle-right"></i> Update status
                        </button>
                    </form>
                </div>

                <%-- ③ Reset Password --%>
                <div class="ic-card ic-card-padded">
                    <div style="display:flex; align-items:center; gap:8px; margin-bottom:16px;">
                        <div style="width:32px; height:32px; border-radius:8px;
                                    background:var(--red-bg);
                                    display:flex; align-items:center; justify-content:center;">
                            <i class="ti ti-lock-open" style="font-size:17px; color:var(--red);"></i>
                        </div>
                        <div>
                            <div style="font-size:14px; font-weight:600; color:var(--text-1);">Reset password</div>
                            <div style="font-size:12px; color:var(--text-3);">Force a new password for this user</div>
                        </div>
                    </div>

                    <form action="<%= request.getContextPath() %>/manage-user" method="post"
                          onsubmit="return confirm('Reset password for this user? This cannot be undone.');">
                        <input type="hidden" name="id"     value="<%= selectedUser.getId() %>">
                        <input type="hidden" name="action" value="resetPassword">

                        <div class="ic-form-group">
                            <label class="ic-label">New password</label>
                            <div class="ic-input-icon" style="position:relative;">
                                <i class="ti ti-lock"></i>
                                <input type="password" class="ic-input" id="newPwd"
                                       name="newPassword"
                                       placeholder="At least 6 characters"
                                       minlength="6" required>
                                <i class="ti ti-eye" id="toggleNewPwd"
                                   style="position:absolute; right:10px; top:50%; transform:translateY(-50%);
                                          cursor:pointer; color:var(--text-3); font-size:16px; left:auto;"></i>
                            </div>
                        </div>

                        <div style="padding:10px 12px; background:var(--red-bg);
                                    border:1px solid #FECACA; border-radius:var(--radius-sm);
                                    font-size:12px; color:var(--red); margin-bottom:14px;">
                            <i class="ti ti-alert-triangle" style="font-size:14px;"></i>
                            This will immediately invalidate the user's current password.
                        </div>

                        <button type="submit" class="ic-btn ic-btn-danger" style="width:100%; justify-content:center;">
                            <i class="ti ti-key"></i> Reset password
                        </button>
                    </form>
                </div>

            </div>

        </div>
    </main>
</div>

<script>
    const toggleNewPwd = document.getElementById('toggleNewPwd');
    const newPwd       = document.getElementById('newPwd');
    if (toggleNewPwd && newPwd) {
        toggleNewPwd.addEventListener('click', function () {
            const isText = newPwd.type === 'text';
            newPwd.type = isText ? 'password' : 'text';
            toggleNewPwd.className = isText ? 'ti ti-eye' : 'ti ti-eye-off';
        });
    }
</script>

</body>
</html>
