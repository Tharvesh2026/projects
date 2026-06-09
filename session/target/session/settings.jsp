<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page isELIgnored="false" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>Session Settings</title>
            <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
            <style>
                body {
                    background: linear-gradient(135deg,
                            #667eea 0%,
                            #764ba2 100%);
                    min-height: 100vh;
                }
            </style>

        </head>

        <body>
            <%@ include file="navbar.jsp" %>

                <div class="container mt-4">

                    <div class="card shadow">

                        <div class="card-header bg-primary text-white">
                            Session Details
                        </div>

                        <div class="card-body">

                            <ul class="list-group">

                                <li class="list-group-item">
                                    <strong>Name:</strong>
                                    ${sessionScope.user.name}
                                </li>

                                <li class="list-group-item">
                                    <strong>Session ID:</strong>
                                    ${pageContext.session.id}
                                </li>

                                <li class="list-group-item" id="creationTime"></li>

                                <li class="list-group-item" id="lastAccessTime"></li>

                                <li class="list-group-item" id="timeout"></li>

                                <li class="list-group-item">
                                    Remaining Time :
                                    <span class="badge bg-danger" id="countdown"></span>
                                </li>

                                <li class="list-group-item" id="isNew"></li>

                            </ul>

                        </div>

                    </div>

                </div>

                <script>
                    const timeoutSeconds = Number("${pageContext.session.maxInactiveInterval}");

                    const creationTime = document.getElementById("creationTime");
                    const lastAccessTime = document.getElementById("lastAccessTime");
                    const timeout = document.getElementById("timeout");
                    const isNew = document.getElementById("isNew");

                    creationTime.innerText =
                        "Creation Time: " +
                        new Date(Number(creationTime.innerText)).toLocaleString("en-IN");

                    lastAccessTime.innerText =
                        "Last Access Time: " +
                        new Date(Number(lastAccessTime.innerText)).toLocaleString("en-IN");

                    timeout.innerText =
                        "Timeout In: " + timeoutSeconds + " seconds";

                    isNew.innerText =
                        "Is New Session?: " + isNew.innerText;

                    setTimeout(function () {
                        window.location.href =
                            "index.jsp?error=Session expired. Please login again.";
                    }, timeoutSeconds * 1000);
                </script>

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

        </body>

        </html>