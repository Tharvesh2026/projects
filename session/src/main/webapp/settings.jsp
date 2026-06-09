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

                                <li class="list-group-item" id="creationTime">
                                    ${pageContext.session.creationTime}
                                </li>

                                <li class="list-group-item" id="lastAccessTime">
                                    ${pageContext.session.lastAccessedTime}
                                </li>

                                <li class="list-group-item" id="timeout">
                                    ${pageContext.session.maxInactiveInterval}
                                </li>

                                <li class="list-group-item" id="isNew">
                                    ${pageContext.session.isNew()}
                                </li>
                            </ul>

                        </div>

                    </div>

                </div>

                <script>
                    const timeoutSeconds = Number("${pageContext.session.maxInactiveInterval}");

                    const creationTime = document.getElementById("creationTime");
                    const lastAccessTime = document.getElementById("lastAccessTime");
                    const timeoutText = document.getElementById("timeout");
                    const isNew = document.getElementById("isNew");

                    creationTime.innerText =
                        "Creation Time: " +
                        new Date(Number(creationTime.innerText)).toLocaleString("en-IN");

                    lastAccessTime.innerText =
                        "Last Access Time: " +
                        new Date(Number(lastAccessTime.innerText)).toLocaleString("en-IN");

                    timeoutText.innerText =
                        "Timeout In: " + timeoutSeconds + " seconds";

                    isNew.innerText =
                        "Is New Session?: " + isNew.innerText;
                </script>

                <script>
                    let remainingSeconds = Number("${pageContext.session.maxInactiveInterval}");

                    const countdown = document.getElementById("countdown");

                    function updateCountdown() {
                        countdown.innerText = remainingSeconds + " seconds";

                        if (remainingSeconds <= 0) {
                            window.location.href =
                                "index.jsp?error=Session expired. Please login again.";
                        }

                        remainingSeconds--;
                    }

                    updateCountdown();
                    setInterval(updateCountdown, 1000);
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