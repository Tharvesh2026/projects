<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>i.Core — Dashboard</title>
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
                    <span>Dashboard</span>
                </div>
            </div>
            <div class="ic-topbar-right">
                <span style="display:flex;align-items:center;gap:6px;font-size:12px;color:var(--text-3);">
                    <span class="ic-dot ic-dot-green"></span> All systems operational
                </span>
            </div>
        </div>

        <div class="ic-content">

            <%-- Page header --%>
            <div class="ic-page-header">
                <div>
                    <div class="ic-page-title">
                        Welcome back, ${sessionScope.user.name}
                    </div>
                    <div class="ic-page-sub">
                        Here's what's happening in your cloud environment today.
                    </div>
                </div>
            </div>

            <%-- User profile card --%>
            <div class="ic-card ic-card-padded" style="margin-bottom: 20px;">

                <div style="display:flex; align-items:center; justify-content:space-between; flex-wrap:wrap; gap:16px;">

                    <div style="display:flex; align-items:center; gap:14px;">
                        <%
                            String wName = (String) session.getAttribute("name");
                            if (wName == null) {
                                Object userObj = session.getAttribute("user");
                                if (userObj != null) {
                                    wName = ((com.example.session.model.User) userObj).getName();
                                }
                            }
                            if (wName == null) wName = "User";
                            String[] wParts = wName.trim().split("\\s+");
                            String wInitials = wParts.length >= 2
                                ? String.valueOf(wParts[0].charAt(0)) + String.valueOf(wParts[1].charAt(0))
                                : String.valueOf(wParts[0].charAt(0));
                        %>
                        <div class="ic-avatar ic-avatar-lg av-purple">
                            <%= wInitials.toUpperCase() %>
                        </div>
                        <div>
                            <div style="font-size:16px; font-weight:600; color:var(--text-1);">
                                <c:out value="${sessionScope.user.name}"/>
                            </div>
                            <div style="font-size:13px; color:var(--text-3); margin-top:2px;">
                                @${sessionScope.user.username}
                                &nbsp;&bull;&nbsp;
                                ${sessionScope.user.email}
                            </div>
                            <div style="margin-top:6px;">
                                <span class="ic-tag tag-admin">${sessionScope.user.role}</span>
                            </div>
                        </div>
                    </div>

                    <div style="display:flex; gap:10px; align-items:center;">
                        <div style="text-align:right;">
                            <div style="font-size:11px; color:var(--text-3);">User ID</div>
                            <div style="font-family:var(--mono); font-size:13px; color:var(--text-2);">
                                #${sessionScope.user.id}
                            </div>
                        </div>
                        <div style="width:1px; height:32px; background:var(--border);"></div>
                        <div style="text-align:right;">
                            <div style="font-size:11px; color:var(--text-3);">Status</div>
                            <span class="ic-tag tag-active"
                                  style="font-size:12px; margin-top:3px; display:inline-flex;">
                                <i class="ti ti-circle-check" style="font-size:13px;"></i>
                                Active
                            </span>
                        </div>
                    </div>

                </div>

                <%-- Session countdown bar --%>
                <div class="ic-countdown" style="margin-top:16px;">
                    <div class="ic-countdown-label">
                        <i class="ti ti-clock"></i> Session expires in
                    </div>
                    <div class="ic-countdown-value" id="countdown">--</div>
                </div>

            </div>

            <%-- Quick-access info rows --%>
            <div class="ic-grid-2" style="margin-bottom:0;">

                <div class="ic-card ic-card-padded">
                    <div style="font-size:13px; font-weight:500; color:var(--text-2); margin-bottom:14px;
                                display:flex; align-items:center; gap:6px;">
                        <i class="ti ti-id-badge" style="font-size:16px;"></i> Account details
                    </div>
                    <div class="ic-info-row">
                        <span class="ic-info-label">Full name</span>
                        <span class="ic-info-value">${sessionScope.user.name}</span>
                    </div>
                    <div class="ic-info-row">
                        <span class="ic-info-label">Username</span>
                        <span class="ic-info-mono">@${sessionScope.user.username}</span>
                    </div>
                    <div class="ic-info-row">
                        <span class="ic-info-label">Email</span>
                        <span class="ic-info-value">${sessionScope.user.email}</span>
                    </div>
                    <div class="ic-info-row">
                        <span class="ic-info-label">Role</span>
                        <span class="ic-tag tag-admin">${sessionScope.user.role}</span>
                    </div>
                </div>

                <div class="ic-card ic-card-padded">
                    <div style="font-size:13px; font-weight:500; color:var(--text-2); margin-bottom:14px;
                                display:flex; align-items:center; gap:6px;">
                        <i class="ti ti-shield" style="font-size:16px;"></i> Session security
                    </div>
                    <div class="ic-info-row">
                        <span class="ic-info-label">Session ID</span>
                        <span class="ic-info-mono" style="font-size:11px;"
                              title="${pageContext.session.id}">
                            ${pageContext.session.id}
                        </span>
                    </div>
                    <div class="ic-info-row">
                        <span class="ic-info-label">CSRF protection</span>
                        <span class="ic-tag tag-active" style="font-size:11.5px;">
                            <i class="ti ti-check" style="font-size:12px;"></i> Active
                        </span>
                    </div>
                    <div class="ic-info-row">
                        <span class="ic-info-label">Permissions source</span>
                        <span style="font-size:12px; color:var(--text-2);">DB-resolved per request</span>
                    </div>
                    <div class="ic-info-row">
                        <span class="ic-info-label">Timeout</span>
                        <span class="ic-info-mono">${pageContext.session.maxInactiveInterval}s</span>
                    </div>
                </div>

            </div>

        </div>
    </main>

</div>

<script>
    let timeout = Number("${pageContext.session.maxInactiveInterval}");
    const sessionTimeoutSeconds = timeout;
    const countdownEl = document.getElementById("countdown");
    let userActive = false;

    ["mousemove","keydown","click","input","scroll"].forEach(function(ev) {
        document.addEventListener(ev, function() { userActive = true; });
    });

    function fmt(s) {
        const m = Math.floor(s / 60);
        const sec = s % 60;
        return m + "m " + String(sec).padStart(2,"0") + "s";
    }

    function updateCountdown() {
        countdownEl.textContent = fmt(timeout);
        if (timeout <= 0) {
            clearInterval(timer);
            window.location.href = "index.jsp?error=Session expired. Please login again.";
            return;
        }
        timeout--;
    }

    updateCountdown();
    const timer = setInterval(updateCountdown, 1000);

    setInterval(function() {
        if (!userActive) return;
        fetch("refresh-session", { method: "POST" })
            .then(function(r) {
                if (r.status === 200) { userActive = false; timeout = sessionTimeoutSeconds; }
                if (r.status === 401) { clearInterval(timer); window.location.href = "index.jsp?error=Session expired."; }
            })
            .catch(function(e) { console.error("Session refresh failed", e); });
    }, (sessionTimeoutSeconds - 60) * 1000);
</script>

</body>
</html>
