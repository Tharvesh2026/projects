<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ page isELIgnored="false" %>
<%@ page import="com.example.session.model.User" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%
    User profileUser = (User) session.getAttribute("user");
    if (profileUser == null) {
        response.sendRedirect(request.getContextPath() + "/index.jsp");
        return;
    }

    /* Avatar initials */
    String pName   = profileUser.getName() != null ? profileUser.getName().trim() : "User";
    String[] pParts = pName.split("\\s+");
    String pInit   = pParts.length >= 2
        ? String.valueOf(pParts[0].charAt(0)) + String.valueOf(pParts[1].charAt(0))
        : String.valueOf(pParts[0].charAt(0));
    pInit = pInit.toUpperCase();

    /* Role badge class */
    String pRole = profileUser.getRole() != null ? profileUser.getRole().toUpperCase() : "USER";
    String pRoleClass = "tag-generic";
    if      (pRole.contains("ADMIN"))   pRoleClass = "tag-admin";
    else if (pRole.contains("AUDIT"))   pRoleClass = "tag-auditor";
    else if (pRole.contains("MANAGER")) pRoleClass = "tag-manager";
    else if (pRole.contains("USER"))    pRoleClass = "tag-user";

    /* Status badge class */
    String pStatus = profileUser.getStatus() != null ? profileUser.getStatus() : "ACTIVE";
    String pStatusClass = "ACTIVE".equalsIgnoreCase(pStatus)   ? "tag-active"
                        : "LOCKED".equalsIgnoreCase(pStatus)   ? "tag-locked"
                        : "tag-inactive";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>i.Core — My Profile</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=JetBrains+Mono:wght@400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@3.x/tabler-icons.min.css">
    <link rel="stylesheet" href="assets/icore.css">

    <style>
        /* Profile banner */
        .profile-banner {
            height: 96px;
            background: linear-gradient(135deg, #312E81 0%, #4338CA 55%, #818CF8 100%);
            border-radius: var(--radius-lg) var(--radius-lg) 0 0;
            position: relative;
        }

        .profile-banner-dots {
            position: absolute; inset: 0;
            background-image: radial-gradient(circle, rgba(255,255,255,.12) 1px, transparent 1px);
            background-size: 22px 22px;
            border-radius: inherit;
        }

        /* Avatar sits on border of banner */
        .profile-avatar-wrap {
            position: absolute;
            bottom: -30px;
            left: 24px;
        }

        .profile-avatar {
            width: 62px; height: 62px;
            border-radius: 50%;
            background: #EEF2FF;
            color: #4338CA;
            font-size: 22px;
            font-weight: 600;
            display: flex; align-items: center; justify-content: center;
            border: 3px solid var(--surface);
            box-shadow: var(--shadow-sm);
        }

        /* Tab nav */
        .profile-tabs {
            display: flex;
            gap: 2px;
            border-bottom: 1px solid var(--border);
            padding: 0 24px;
            margin-bottom: 22px;
        }

        .profile-tab {
            display: flex; align-items: center; gap: 6px;
            padding: 10px 14px;
            font-size: 13.5px;
            color: var(--text-3);
            cursor: pointer;
            border-bottom: 2px solid transparent;
            margin-bottom: -1px;
            transition: color .15s, border-color .15s;
            background: none; border-top: none; border-left: none; border-right: none;
            font-family: var(--font);
        }

        .profile-tab.active {
            color: var(--brand);
            border-bottom-color: var(--brand);
            font-weight: 500;
        }

        .profile-tab:hover:not(.active) { color: var(--text-2); }

        /* Tab panels */
        .profile-panel { display: none; }
        .profile-panel.active { display: block; }

        /* Strength meter */
        .strength-bar {
            height: 5px;
            border-radius: 3px;
            background: var(--border);
            margin-top: 6px;
            overflow: hidden;
        }

        .strength-fill {
            height: 100%;
            border-radius: 3px;
            width: 0%;
            transition: width .3s, background .3s;
        }

        /* Activity timeline */
        .timeline-item {
            display: flex;
            gap: 12px;
            padding: 10px 0;
            border-bottom: 1px solid var(--border);
            font-size: 13px;
        }
        .timeline-item:last-child { border-bottom: none; }

        .timeline-dot {
            width: 8px; height: 8px;
            border-radius: 50%;
            flex-shrink: 0;
            margin-top: 5px;
        }

        .timeline-time {
            font-size: 11px;
            color: var(--text-3);
            width: 70px;
            flex-shrink: 0;
            font-family: var(--mono);
        }

        .timeline-msg { color: var(--text-2); flex: 1; }
    </style>
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
                    <span>My Profile</span>
                </div>
            </div>
        </div>

        <div class="ic-content">

            <%-- Flash messages --%>
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
            <div class="ic-card" style="margin-bottom: 20px; overflow: hidden;">

                <div class="profile-banner">
                    <div class="profile-banner-dots"></div>
                    <div class="profile-avatar-wrap">
                        <div class="profile-avatar" id="avatarDisplay">
                            <c:out value="${pInit}"/>
                        </div>
                    </div>
                </div>

                <div style="padding: 44px 24px 20px;">
                    <div style="display:flex; justify-content:space-between;
                                align-items:flex-start; flex-wrap:wrap; gap:12px;">
                        <div>
                            <div style="font-size:18px; font-weight:600; color:var(--text-1);">
                                <c:out value="${sessionScope.user.name}"/>
                            </div>
                            <div style="font-size:13px; color:var(--text-3); margin-top:3px;">
                                @<c:out value="${sessionScope.user.username}"/>
                                &nbsp;&bull;&nbsp;
                                <c:out value="${sessionScope.user.email}"/>
                            </div>
                            <div style="margin-top:10px; display:flex; gap:7px; flex-wrap:wrap;">
                                <span class="ic-tag <%= pRoleClass %>">
                                    <i class="ti ti-shield" style="font-size:12px;"></i>
                                    <c:out value="${sessionScope.user.role}"/>
                                </span>
                                <span class="ic-tag <%= pStatusClass %>">
                                    <i class="ti ti-circle-check" style="font-size:12px;"></i>
                                    <c:out value="${sessionScope.user.status}"/>
                                </span>
                            </div>
                        </div>

                        <%-- Stat chips --%>
                        <div style="display:flex; gap:10px; flex-wrap:wrap;">
                            <div style="padding:8px 16px; background:var(--surface-2);
                                        border-radius:var(--radius-sm); text-align:center;">
                                <div style="font-size:10px; color:var(--text-3); margin-bottom:2px;">User ID</div>
                                <div style="font-family:var(--mono); font-size:13px; font-weight:500;">
                                    #<c:out value="${sessionScope.user.id}"/>
                                </div>
                            </div>
                            <div style="padding:8px 16px; background:var(--surface-2);
                                        border-radius:var(--radius-sm); text-align:center;">
                                <div style="font-size:10px; color:var(--text-3); margin-bottom:2px;">Session</div>
                                <div style="font-family:var(--mono); font-size:11px; font-weight:500;
                                            color:var(--text-2);">
                                    Active
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <%-- Tab nav --%>
                <div class="profile-tabs">
                    <button class="profile-tab active" onclick="switchTab('info', this)">
                        <i class="ti ti-user"></i> Account
                    </button>
                    <button class="profile-tab" onclick="switchTab('edit', this)">
                        <i class="ti ti-edit"></i> Edit profile
                    </button>
                    <button class="profile-tab" onclick="switchTab('password', this)">
                        <i class="ti ti-lock"></i> Change password
                    </button>
                    <button class="profile-tab" onclick="switchTab('activity', this)">
                        <i class="ti ti-activity"></i> Activity
                    </button>
                </div>

                <%-- ═══ TAB: Account info ═══ --%>
                <div id="tab-info" class="profile-panel active" style="padding: 0 24px 24px;">
                    <div class="ic-grid-2">

                        <div>
                            <div style="font-size:12px; font-weight:500; color:var(--text-3);
                                        text-transform:uppercase; letter-spacing:.06em;
                                        margin-bottom:10px;">
                                Personal information
                            </div>
                            <div class="ic-info-row">
                                <span class="ic-info-label">Full name</span>
                                <span class="ic-info-value"><c:out value="${sessionScope.user.name}"/></span>
                            </div>
                            <div class="ic-info-row">
                                <span class="ic-info-label">Username</span>
                                <span class="ic-info-mono">@<c:out value="${sessionScope.user.username}"/></span>
                            </div>
                            <div class="ic-info-row">
                                <span class="ic-info-label">Email address</span>
                                <span class="ic-info-value"><c:out value="${sessionScope.user.email}"/></span>
                            </div>
                            <div class="ic-info-row">
                                <span class="ic-info-label">Account status</span>
                                <span class="ic-tag <%= pStatusClass %>"><c:out value="${sessionScope.user.status}"/></span>
                            </div>
                        </div>

                        <div>
                            <div style="font-size:12px; font-weight:500; color:var(--text-3);
                                        text-transform:uppercase; letter-spacing:.06em;
                                        margin-bottom:10px;">
                                Access & permissions
                            </div>
                            <div class="ic-info-row">
                                <span class="ic-info-label">Role</span>
                                <span class="ic-tag <%= pRoleClass %>"><c:out value="${sessionScope.user.role}"/></span>
                            </div>
                            <div class="ic-info-row">
                                <span class="ic-info-label">User ID</span>
                                <span class="ic-info-mono">#<c:out value="${sessionScope.user.id}"/></span>
                            </div>
                            <div class="ic-info-row">
                                <span class="ic-info-label">Permission source</span>
                                <span style="font-size:12px; color:var(--text-2);">DB per-request</span>
                            </div>
                            <div class="ic-info-row">
                                <span class="ic-info-label">Session ID</span>
                                <span class="ic-info-mono"
                                      style="font-size:11px; max-width:160px;
                                             overflow:hidden; text-overflow:ellipsis; white-space:nowrap;"
                                      title="<c:out value='${pageContext.session.id}'/>">
                                    <c:out value="${pageContext.session.id}"/>
                                </span>
                            </div>
                        </div>

                    </div>
                </div>

                <%-- ═══ TAB: Edit profile ═══ --%>
                <div id="tab-edit" class="profile-panel" style="padding: 0 24px 24px;">
                    <div style="max-width: 520px;">

                        <div style="font-size:12px; color:var(--text-3); margin-bottom:18px;">
                            Update your display name and username. Email changes require admin approval.
                        </div>

                        <form action="<%= request.getContextPath() %>/profile" method="post">
                            <input type="hidden" name="action" value="updateProfile">

                            <div class="ic-form-group">
                                <label class="ic-label" for="editName">Full name</label>
                                <div class="ic-input-icon">
                                    <i class="ti ti-user"></i>
                                    <input type="text" class="ic-input" id="editName"
                                           name="name"
                                           value="<c:out value='${sessionScope.user.name}'/>"
                                           placeholder="Your full name"
                                           required>
                                </div>
                            </div>

                            <div class="ic-form-group">
                                <label class="ic-label" for="editUsername">Username</label>
                                <div class="ic-input-icon">
                                    <i class="ti ti-at"></i>
                                    <input type="text" class="ic-input" id="editUsername"
                                           name="username"
                                           value="<c:out value='${sessionScope.user.username}'/>"
                                           placeholder="your_username"
                                           required
                                           oninput="this.value=this.value.toLowerCase().replace(/\s/g,'_')">
                                </div>
                            </div>

                            <div class="ic-form-group">
                                <label class="ic-label" for="editEmail">Email address</label>
                                <div class="ic-input-icon">
                                    <i class="ti ti-mail"></i>
                                    <input type="email" class="ic-input" id="editEmail"
                                           name="email"
                                           value="<c:out value='${sessionScope.user.email}'/>"
                                           placeholder="you@example.com"
                                           required>
                                </div>
                            </div>

                            <div style="display:flex; gap:10px; margin-top:4px;">
                                <button type="button"
                                        class="ic-btn"
                                        onclick="switchTab('info', document.querySelector('.profile-tab'))">
                                    Cancel
                                </button>
                                <button type="submit" class="ic-btn ic-btn-primary">
                                    <i class="ti ti-device-floppy"></i> Save changes
                                </button>
                            </div>
                        </form>
                    </div>
                </div>

                <%-- ═══ TAB: Change password ═══ --%>
                <div id="tab-password" class="profile-panel" style="padding: 0 24px 24px;">
                    <div style="max-width: 520px;">

                        <div style="font-size:12px; color:var(--text-3); margin-bottom:18px;">
                            Choose a strong password. It must be at least 6 characters long.
                        </div>

                        <form action="<%= request.getContextPath() %>/profile" method="post"
                              id="pwdForm" onsubmit="return validatePasswords()">
                            <input type="hidden" name="action" value="changePassword">

                            <div class="ic-form-group">
                                <label class="ic-label" for="currentPwd">Current password</label>
                                <div class="ic-input-icon" style="position:relative;">
                                    <i class="ti ti-lock"></i>
                                    <input type="password" class="ic-input" id="currentPwd"
                                           name="currentPassword"
                                           placeholder="Your current password"
                                           required>
                                    <i class="ti ti-eye" id="toggleCurrent"
                                       style="position:absolute; right:10px; top:50%;
                                              transform:translateY(-50%); cursor:pointer;
                                              color:var(--text-3); font-size:16px; left:auto;"></i>
                                </div>
                            </div>

                            <div class="ic-form-group">
                                <label class="ic-label" for="newPwd">New password</label>
                                <div class="ic-input-icon" style="position:relative;">
                                    <i class="ti ti-lock-open"></i>
                                    <input type="password" class="ic-input" id="newPwd"
                                           name="newPassword"
                                           placeholder="At least 6 characters"
                                           minlength="6" required
                                           oninput="updateStrength(this.value)">
                                    <i class="ti ti-eye" id="toggleNew"
                                       style="position:absolute; right:10px; top:50%;
                                              transform:translateY(-50%); cursor:pointer;
                                              color:var(--text-3); font-size:16px; left:auto;"></i>
                                </div>
                                <%-- Strength bar --%>
                                <div class="strength-bar">
                                    <div class="strength-fill" id="strengthFill"></div>
                                </div>
                                <div style="font-size:11px; color:var(--text-3); margin-top:4px;"
                                     id="strengthLabel"></div>
                            </div>

                            <div class="ic-form-group">
                                <label class="ic-label" for="confirmPwd">Confirm new password</label>
                                <div class="ic-input-icon" style="position:relative;">
                                    <i class="ti ti-lock-check"></i>
                                    <input type="password" class="ic-input" id="confirmPwd"
                                           name="confirmPassword"
                                           placeholder="Repeat new password"
                                           required>
                                    <i class="ti ti-eye" id="toggleConfirm"
                                       style="position:absolute; right:10px; top:50%;
                                              transform:translateY(-50%); cursor:pointer;
                                              color:var(--text-3); font-size:16px; left:auto;"></i>
                                </div>
                                <div style="font-size:11px; color:var(--red); margin-top:4px;
                                            display:none;" id="matchError">
                                    <i class="ti ti-alert-circle"></i> Passwords do not match
                                </div>
                            </div>

                            <button type="submit" class="ic-btn ic-btn-primary">
                                <i class="ti ti-key"></i> Update password
                            </button>
                        </form>
                    </div>
                </div>

                <%-- ═══ TAB: Activity ═══ --%>
                <div id="tab-activity" class="profile-panel" style="padding: 0 24px 24px;">

                    <div style="font-size:12px; color:var(--text-3); margin-bottom:16px;">
                        Recent account events resolved from the audit log.
                    </div>

                    <%
                        /* Activity pulled from request attribute set by ProfileServlet */
                        @SuppressWarnings("unchecked")
                        java.util.List<java.util.Map<String,String>> activities =
                            (java.util.List<java.util.Map<String,String>>) request.getAttribute("activities");

                        if (activities == null || activities.isEmpty()) {
                    %>
                    <div style="text-align:center; padding:32px 0; color:var(--text-3); font-size:13px;">
                        <i class="ti ti-clock-off" style="font-size:32px; display:block; margin-bottom:8px;
                                                           color:var(--border);"></i>
                        No recent activity found.
                    </div>
                    <%
                        } else {
                            String[] dotColors = {"#16A34A","#4F46E5","#DC2626","#B45309","#0891B2"};
                            int di = 0;
                            for (java.util.Map<String,String> act : activities) {
                    %>
                    <div class="timeline-item">
                        <div class="timeline-dot"
                             style="background:<%= dotColors[di % dotColors.length] %>;"></div>
                        <span class="timeline-time"><c:out value="${activity.time}"/></span>
                        <span class="timeline-msg"><c:out value="${activity.message}"/></span>
                    </div>
                    <%      di++;
                            }
                        }
                    %>
                </div>

            </div>

        </div>
    </main>
</div>

<script>
    /* ── Tab switching ── */
    function switchTab(name, clickedBtn) {
        document.querySelectorAll('.profile-panel').forEach(p => p.classList.remove('active'));
        document.querySelectorAll('.profile-tab').forEach(b => b.classList.remove('active'));
        document.getElementById('tab-' + name).classList.add('active');
        if (clickedBtn) clickedBtn.classList.add('active');
    }

    /* ── Password visibility toggles ── */
    function makeToggle(toggleId, inputId) {
        const toggle = document.getElementById(toggleId);
        const input  = document.getElementById(inputId);
        if (!toggle || !input) return;
        toggle.addEventListener('click', function () {
            const isText = input.type === 'text';
            input.type = isText ? 'password' : 'text';
            toggle.className = isText ? 'ti ti-eye' : 'ti ti-eye-off';
        });
    }
    makeToggle('toggleCurrent', 'currentPwd');
    makeToggle('toggleNew',     'newPwd');
    makeToggle('toggleConfirm', 'confirmPwd');

    /* ── Password strength meter ── */
    function updateStrength(val) {
        const fill  = document.getElementById('strengthFill');
        const label = document.getElementById('strengthLabel');
        if (!fill || !label) return;

        let score = 0;
        if (val.length >= 6)  score++;
        if (val.length >= 10) score++;
        if (/[A-Z]/.test(val)) score++;
        if (/[0-9]/.test(val)) score++;
        if (/[^A-Za-z0-9]/.test(val)) score++;

        const map = [
            { pct:'0%',   color:'transparent', text:'' },
            { pct:'25%',  color:'#DC2626',      text:'Weak' },
            { pct:'50%',  color:'#F59E0B',      text:'Fair' },
            { pct:'75%',  color:'#3B82F6',      text:'Good' },
            { pct:'100%', color:'#16A34A',      text:'Strong' },
        ];

        const s = map[Math.min(score, 4)];
        fill.style.width      = s.pct;
        fill.style.background = s.color;
        label.textContent     = s.text;
    }

    /* ── Password match validation ── */
    function validatePasswords() {
        const newPwd     = document.getElementById('newPwd').value;
        const confirmPwd = document.getElementById('confirmPwd').value;
        const matchError = document.getElementById('matchError');

        if (newPwd !== confirmPwd) {
            matchError.style.display = 'block';
            document.getElementById('confirmPwd').focus();
            return false;
        }
        matchError.style.display = 'none';
        return true;
    }

    document.getElementById('confirmPwd').addEventListener('input', function () {
        const newPwd = document.getElementById('newPwd').value;
        document.getElementById('matchError').style.display =
            this.value && this.value !== newPwd ? 'block' : 'none';
    });

    /* ── Auto-open tab from URL param ── */
    const urlParams = new URLSearchParams(window.location.search);
    const tabParam  = urlParams.get('tab');
    if (tabParam) {
        const targetBtn = document.querySelector('[onclick*="' + tabParam + '"]');
        if (targetBtn) switchTab(tabParam, targetBtn);
    }
</script>

</body>
</html>
