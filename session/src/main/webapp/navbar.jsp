<%@ page isELIgnored="false" %>
    <%@ page import="com.example.session.model.User" %>
        <%@ page import="com.example.session.util.PermissionValidator" %>

            <% User navUser=(User) session.getAttribute("user"); 
            boolean canViewUsers=false; 
            boolean canViewLogs=false; 
            boolean canManageRoles=false; 
            if (navUser !=null) {
                canViewUsers=PermissionValidator.hasPermission(navUser.getId(), "USER_READ" );
                canViewLogs=PermissionValidator.hasPermission(navUser.getId(), "LOG_VIEW" );
                canManageRoles=PermissionValidator.hasPermission(navUser.getId(), "ROLE_UPDATE" ); } %>

                <style>
                    .app-navbar {
                        background: linear-gradient(135deg, #0399f5, #00508f);
                        padding: 14px 0;
                        box-shadow: 0 8px 20px rgba(0, 80, 143, 0.25);
                        position: sticky;
                        top: 0;
                        z-index: 1000;
                    }

                    .app-navbar .navbar-brand {
                        color: white;
                        font-weight: 800;
                        letter-spacing: 0.5px;
                        font-size: 22px;
                    }

                    .app-navbar .nav-actions {
                        display: flex;
                        align-items: center;
                        gap: 12px;
                    }

                    .app-navbar .nav-link-btn {
                        color: white;
                        text-decoration: none;
                        padding: 8px 18px;
                        border-radius: 50px;
                        border: 1px solid rgba(255, 255, 255, 0.35);
                        background: rgba(255, 255, 255, 0.12);
                        transition: 0.3s ease;
                        font-weight: 600;
                        font-size: 14px;
                    }

                    .app-navbar .nav-link-btn:hover {
                        background: white;
                        color: #00508f;
                        transform: translateY(-1px);
                    }

                    .app-navbar .logout-btn {
                        border: none;
                        padding: 8px 18px;
                        border-radius: 50px;
                        background: #ffffff;
                        color: #00508f;
                        font-weight: 700;
                        font-size: 14px;
                        transition: 0.3s ease;
                    }

                    .app-navbar .logout-btn:hover {
                        background: #eaf7ff;
                        color: #003b68;
                        transform: translateY(-1px);
                    }

                    @media(max-width: 576px) {
                        .app-navbar .container {
                            flex-direction: column;
                            gap: 12px;
                        }

                        .app-navbar .nav-actions {
                            flex-wrap: wrap;
                            justify-content: center;
                        }
                    }
                </style>

                <nav class="app-navbar">
                    <div class="container d-flex justify-content-between align-items-center">

                        <a class="navbar-brand text-decoration-none" href="welcome">
                            Session App
                        </a>

                        <div class="nav-actions">

                            <a href="welcome" class="nav-link-btn">
                                Home
                            </a>

                            <a href="settings" class="nav-link-btn">
                                Settings
                            </a>
                            <% if (canViewUsers) { %>
                                <a href="<%= request.getContextPath() %>/users" class="nav-link-btn">Users</a>
                                <% } %>

                                    <% if (canViewLogs) { %>
                                        <a href="<%= request.getContextPath() %>/logs" class="nav-link-btn">Logs</a>
                                        <% } %>

                                            <% if (canManageRoles) { %>
                                                <a href="<%= request.getContextPath() %>/roles"
                                                    class="nav-link-btn">Roles</a>
                                                <% } %>

                                                    <form action="logout" method="post" style="margin:0;">
                                                        <button type="submit" class="logout-btn"
                                                            onclick="return confirmLogout();">
                                                            Logout
                                                        </button>
                                                    </form>

                        </div>

                    </div>
                </nav>

                <script>
                    function confirmLogout() {
                        return confirm("Are you sure you want to logout?");
                    }
                </script>