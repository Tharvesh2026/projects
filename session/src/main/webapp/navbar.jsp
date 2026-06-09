<nav class="navbar navbar-expand-lg navbar-dark bg-dark shadow">
    <div class="container">

        <a class="navbar-brand fw-bold" href="#">
            Session App
        </a>

        <div class="d-flex align-items-center gap-2">

            <a href="welcome.jsp" class="btn btn-outline-light">
                Home
            </a>

            <a href="settings.jsp" class="btn btn-outline-info">
                Settings
            </a>

            <form action="logout" method="post">
                <button class="btn btn-danger"
                        onclick="return confirmLogout()">
                    Logout
                </button>
            </form>

        </div>

    </div>
</nav>