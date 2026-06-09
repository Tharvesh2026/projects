<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <%@ page isELIgnored="false" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
            <meta charset="UTF-8">
            <title>Welcome Session</title>

            <style>
                * {
                    font-family: Arial, sans-serif;
                }

                body {
                    margin: 20px;
                    background: #f4f4f4;
                }

                .container {
                    background: white;
                    padding: 25px;
                    border-radius: 10px;
                    box-shadow: 0 0 10px #ccc;
                }

                table {
                    width: 100%;
                    border-collapse: collapse;
                    margin-top: 20px;
                }

                th,
                td {
                    border: 1px solid #ddd;
                    padding: 10px;
                    text-align: left;
                }

                th {
                    background-color: #f2f2f2;
                }

                .note {
                    color: red;
                    display: block;
                    margin-top: 15px;
                }

                .actions {
                    margin-top: 25px;
                    text-align: center;
                }

                button {
                    background: red;
                    color: white;
                    font-weight: 700;
                    border: none;
                    padding: 10px 20px;
                    cursor: pointer;
                }

                a {
                    margin-right: 20px;
                }
            </style>
        </head>

        <body>
            <%@ include file="navbar.jsp" %>

            <div class="container">
                <h1>Welcome to the Session Management Application</h1>

                <p>Hello, ${sessionScope.user.name}</p>

                <table>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Username</th>
                        <th>Email</th>
                    </tr>

                    <tr>
                        <td>${sessionScope.user.id}</td>
                        <td>${sessionScope.user.name}</td>
                        <td>${sessionScope.user.uname}</td>
                        <td>${sessionScope.user.email}</td>
                    </tr>
                </table>
            </div>
            <p>
                Session expires in:
                <span id="countdown"></span>
            </p>
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

        </body>

        </html>