<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page isELIgnored="false" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>Session Settings</title>

            <style>
                * {
                    font-family: Arial, sans-serif;
                }

                body {
                    margin: 20px;
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    flex-direction: column;
                    background: #f4f4f4;
                }

                .box {
                    background: white;
                    padding: 25px;
                    border-radius: 10px;
                    width: 500px;
                    box-shadow: 0 0 10px #ccc;
                }

                p {
                    font-size: 16px;
                }

                a,
                button {
                    margin-top: 15px;
                }
            </style>
        </head>

        <body>
            <%@ include file="navbar.jsp" %>

                <div class="box">
                    <h3>Session Details</h3>

                    <p>Name: ${sessionScope.user.name}</p>
                    <p>Session ID: ${pageContext.session.id}</p>

                    <p id="creationTime">${pageContext.session.creationTime}</p>
                    <p id="lastAccessTime">${pageContext.session.lastAccessedTime}</p>
                    <p id="timeout">${pageContext.session.maxInactiveInterval}</p>
                    TimeOut IN: <span id="countdown"></span>
                    <p id="isNew">${pageContext.session.isNew()}</p>

                    <p style="color:red;opacity: 30%;">${sessionScope.X_Secret_Token}</p>
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