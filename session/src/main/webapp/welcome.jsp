<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page isELIgnored="false" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>Welcome Session</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

            <style>
                body {
                    background: linear-gradient(135deg,
                            #667eea 0%,
                            #764ba2 100%);
                    min-height: 100vh;
                }

                .card {
                    border: none;
                    border-radius: 15px;
                }
            </style>
        </head>

        <body>
            <%@ include file="navbar.jsp" %>
                <div class="container mt-4">

                    <div class="card shadow">

                        <div class="card-body">

                            <h2 class="mb-3">
                                Welcome, ${sessionScope.user.name}
                            </h2>

                            <div class="table-responsive">

                                <table class="table table-bordered table-hover">

                                    <thead class="table-dark">
                                        <tr>
                                            <th>ID</th>
                                            <th>Name</th>
                                            <th>Username</th>
                                            <th>Email</th>
                                        </tr>
                                    </thead>

                                    <tbody>
                                        <tr>
                                            <td>${sessionScope.user.id}</td>
                                            <td>${sessionScope.user.name}</td>
                                            <td>${sessionScope.user.uname}</td>
                                            <td>${sessionScope.user.email}</td>
                                        </tr>
                                    </tbody>

                                </table>

                            </div>

                            <div class="alert alert-warning">
                                Session expires in:
                                <strong id="countdown"></strong>
                            </div>

                        </div>

                    </div>

                </div>

                <script>
                    let timeout = Number("${pageContext.session.maxInactiveInterval}");

                    const countdown = document.getElementById("countdown");

                    function updateCountdown() {
                        countdown.innerText = timeout + " seconds";

                        if (timeout <= 0) {
                            window.location.href =
                                "index.jsp?error=Session expired. Please login again.";
                        }

                        timeout--;
                    }

                    updateCountdown();
                    setInterval(updateCountdown, 1000);
                </script>
                <script>
                    const timeoutSeconds = Number("${pageContext.session.maxInactiveInterval}");

                    setTimeout(function () {
                        window.location.href =
                            "index.jsp?error=Session expired. Please login again.";
                    }, timeoutSeconds * 1000);
                </script>
                <script>
                    let userActive = false;

                    ["mousemove", "keydown", "click", "input", "scroll"].forEach(function (eventName) {
                        document.addEventListener(eventName, function () {
                            userActive = true;
                        });
                    });

                    const sessionTimeoutSeconds = Number("${pageContext.session.maxInactiveInterval}");
                    const refreshBeforeSeconds = 60;

                    setInterval(function () {
                        if (userActive) {
                            fetch("refresh-session", {
                                method: "POST"
                            })
                                .then(function (response) {
                                    if (response.status === 200) {
                                        console.log("Session refreshed");
                                        userActive = false;
                                    }

                                    if (response.status === 401) {
                                        window.location.href =
                                            "index.jsp?error=Session expired. Please login again.";
                                    }
                                });
                        }
                    }, (sessionTimeoutSeconds - refreshBeforeSeconds) * 1000);
                </script>

        </body>

        </html>