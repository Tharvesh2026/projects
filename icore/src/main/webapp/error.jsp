<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ page isELIgnored="false" %>

<%
    Integer statusCode = (Integer) request.getAttribute("jakarta.servlet.error.status_code");

    String errorTitle   = "Something went wrong";
    String errorMessage = "The application could not process your request. Please try again.";
    String errorIcon    = "ti-alert-circle";
    String errorColor   = "#4F46E5";
    String errorBg      = "#EEF2FF";
    String errorBorder  = "#C7D2FE";

    if (statusCode != null) {
        if (statusCode == 404) {
            errorTitle   = "Page not found";
            errorMessage = "The page you're looking for doesn't exist or may have been moved.";
            errorIcon    = "ti-map-pin-off";
            errorColor   = "#B45309";
            errorBg      = "#FEF3C7";
            errorBorder  = "#FDE68A";
        } else if (statusCode == 403) {
            errorTitle   = "Access denied";
            errorMessage = "You don't have permission to access this page. Contact your administrator if you believe this is a mistake.";
            errorIcon    = "ti-shield-x";
            errorColor   = "#DC2626";
            errorBg      = "#FEE2E2";
            errorBorder  = "#FECACA";
        } else if (statusCode == 500) {
            errorTitle   = "Server error";
            errorMessage = "Something went wrong on the server. Our team has been notified. Please try again later.";
            errorIcon    = "ti-server-off";
            errorColor   = "#DC2626";
            errorBg      = "#FEE2E2";
            errorBorder  = "#FECACA";
        }
    }

    String codeText = statusCode != null ? String.valueOf(statusCode) : "ERR";
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>i.Core — <%= errorTitle %></title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&family=JetBrains+Mono:wght@400;500&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@tabler/icons-webfont@3.x/tabler-icons.min.css">
    <link rel="stylesheet" href="assets/icore.css">

    <style>
        body {
            background: var(--bg);
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 24px;
        }

        .error-shell {
            width: 100%;
            max-width: 520px;
        }

        /* Big code display */
        .error-code {
            font-size: 96px;
            font-weight: 600;
            font-family: var(--mono);
            color: var(--border);
            line-height: 1;
            text-align: center;
            letter-spacing: -4px;
            margin-bottom: 0;
            user-select: none;
        }

        .error-icon-wrap {
            width: 72px; height: 72px;
            border-radius: 18px;
            display: flex; align-items: center; justify-content: center;
            margin: 0 auto 16px;
        }

        .error-icon-wrap i {
            font-size: 36px;
        }

        .log-line {
            font-family: var(--mono);
            font-size: 11.5px;
            color: var(--text-3);
            padding: 4px 0;
            border-bottom: 1px solid var(--border);
        }

        .log-line:last-child { border-bottom: none; }

        .log-key   { color: var(--text-2); }
        .log-val   { color: var(--text-1); }
    </style>
</head>
<body>

<div class="error-shell">

    <%-- Code --%>
    <div class="error-code"><%= codeText %></div>

    <%-- Icon + title --%>
    <div class="ic-card ic-card-padded" style="text-align:center; margin-top:-10px;">

        <div class="error-icon-wrap"
             style="background:<%= errorBg %>; border:1px solid <%= errorBorder %>;">
            <i class="ti <%= errorIcon %>" style="color:<%= errorColor %>;"></i>
        </div>

        <div style="font-size:20px; font-weight:600; color:var(--text-1); margin-bottom:8px;">
            <%= errorTitle %>
        </div>

        <div style="font-size:14px; color:var(--text-3); line-height:1.7; margin-bottom:22px;">
            <%= errorMessage %>
        </div>

        <%-- Log-style detail block --%>
        <div class="ic-card" style="text-align:left; padding:12px 16px; margin-bottom:20px;
                                    background:var(--surface-2);">
            <div class="log-line">
                <span class="log-key">status_code    </span>
                <span class="log-val"><%= codeText %></span>
            </div>
            <div class="log-line">
                <span class="log-key">request_uri    </span>
                <span class="log-val">
                    <%
                        String reqUri = (String) request.getAttribute("jakarta.servlet.error.request_uri");
                        if (reqUri == null) reqUri = request.getRequestURI();
                    %>
                    <%= reqUri %>
                </span>
            </div>
            <div class="log-line">
                <span class="log-key">timestamp      </span>
                <span class="log-val" id="errTimestamp">—</span>
            </div>
            <div class="log-line">
                <span class="log-key">session        </span>
                <span class="log-val">
                    <%
                        jakarta.servlet.http.HttpSession errSession = request.getSession(false);
                        if (errSession != null) {
                    %>
                    <%= errSession.getId().substring(0, Math.min(16, errSession.getId().length())) %>…
                    <% } else { %>
                    none
                    <% } %>
                </span>
            </div>
        </div>

        <%-- Actions --%>
        <div style="display:flex; gap:10px; justify-content:center; flex-wrap:wrap;">

            <a href="javascript:history.back()" class="ic-btn" style="min-width:120px; justify-content:center;">
                <i class="ti ti-arrow-left"></i> Go back
            </a>

            <% if (errSession != null) { %>
            <a href="<%= request.getContextPath() %>/welcome"
               class="ic-btn ic-btn-primary" style="min-width:140px; justify-content:center;">
                <i class="ti ti-layout-dashboard"></i> Dashboard
            </a>
            <% } else { %>
            <a href="<%= request.getContextPath() %>/index.jsp"
               class="ic-btn ic-btn-primary" style="min-width:140px; justify-content:center;">
                <i class="ti ti-login"></i> Sign in
            </a>
            <% } %>

        </div>

        <%-- Brand footer --%>
        <div style="margin-top:20px; padding-top:14px; border-top:1px solid var(--border);
                    font-size:12px; color:var(--text-3); display:flex; align-items:center;
                    justify-content:center; gap:6px;">
            <div style="width:20px; height:20px; background:var(--brand); border-radius:5px;
                        display:flex; align-items:center; justify-content:center;">
                <i class="ti ti-cloud" style="color:#fff; font-size:12px;"></i>
            </div>
            i.Core &nbsp;&bull;&nbsp; Identity & Access Management
        </div>

    </div>

</div>

<script>
    document.getElementById('errTimestamp').textContent =
        new Date().toLocaleString('en-IN');
</script>

</body>
</html>
