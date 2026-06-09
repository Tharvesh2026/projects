<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Welcome Session</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">

    <style>
        * {
            box-sizing: border-box;
            font-family: Arial, sans-serif;
        }

        body {
            margin: 0;
            min-height: 100vh;
            background: #f4f4f4;
        }

        .page-wrapper {
            min-height: calc(100vh - 70px);
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 15px;
        }

        .welcome-box {
            width: 900px;
            min-height: 520px;
            background: linear-gradient(135deg, #0399f5, #00508f);
            border-radius: 18px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            padding: 55px;
            box-shadow: 0 18px 35px rgba(0,0,0,0.25);
            position: relative;
            overflow: hidden;
        }

        .welcome-box::before {
            content: "";
            position: absolute;
            width: 420px;
            height: 420px;
            background: rgba(255,255,255,0.08);
            border-radius: 50%;
            left: -120px;
            top: -70px;
        }

        .welcome-box::after {
            content: "";
            position: absolute;
            width: 360px;
            height: 360px;
            background: rgba(0,180,255,0.35);
            border-radius: 50%;
            left: -40px;
            bottom: -170px;
        }

        .welcome-left {
            color: white;
            width: 42%;
            z-index: 1;
        }

        .welcome-left h1 {
            font-size: 40px;
            font-weight: 800;
            letter-spacing: 1px;
            margin-bottom: 15px;
        }

        .welcome-left h4 {
            font-size: 18px;
            font-weight: 700;
            margin-bottom: 15px;
        }

        .welcome-left p {
            font-size: 14px;
            line-height: 1.8;
            opacity: 0.95;
        }

        .user-card {
            width: 430px;
            background: white;
            border-radius: 18px;
            padding: 35px;
            z-index: 1;
            box-shadow: 0 12px 25px rgba(0,0,0,0.25);
        }

        .user-card h3 {
            color: #04336b;
            font-size: 28px;
            font-weight: 800;
            margin-bottom: 8px;
        }

        .user-card small {
            color: #777;
            display: block;
            margin-bottom: 25px;
        }

        .info-row {
            border: 1px solid #e5e5e5;
            border-radius: 10px;
            padding: 12px 14px;
            margin-bottom: 12px;
            font-size: 14px;
            background: #fafafa;
            display: flex;
            justify-content: space-between;
            gap: 15px;
        }

        .info-row strong {
            color: #04336b;
            min-width: 90px;
        }

        .info-row span {
            text-align: right;
            word-break: break-word;
        }

        .countdown-box {
            margin-top: 18px;
            padding: 14px;
            border-radius: 10px;
            background: linear-gradient(135deg, #0079d6, #00518d);
            color: white;
            text-align: center;
            font-weight: 700;
        }

        @media(max-width: 768px) {
            .welcome-box {
                width: 92%;
                flex-direction: column;
                padding: 35px 22px;
                gap: 30px;
            }

            .welcome-left,
            .user-card {
                width: 100%;
            }

            .info-row {
                flex-direction: column;
                gap: 5px;
            }

            .info-row span {
                text-align: left;
            }
        }
    </style>
</head>

<body>

<%@ include file="navbar.jsp" %>

<div class="page-wrapper">

    <div class="welcome-box">

        <div class="welcome-left">
            <h1>WELCOME</h1>
            <h4>${sessionScope.user.name}</h4>
            <p>
                You have successfully logged in. Your profile details
                and active session countdown are shown here.
            </p>
        </div>

        <div class="user-card">
            <h3>User Details</h3>
            <small>Your registered account information</small>

            <div class="info-row">
                <strong>ID</strong>
                <span>${sessionScope.user.id}</span>
            </div>

            <div class="info-row">
                <strong>Name</strong>
                <span>${sessionScope.user.name}</span>
            </div>

            <div class="info-row">
                <strong>Username</strong>
                <span>${sessionScope.user.uname}</span>
            </div>

            <div class="info-row">
                <strong>Email</strong>
                <span>${sessionScope.user.email}</span>
            </div>

            <div class="countdown-box">
                Session expires in <span id="countdown"></span>
            </div>
        </div>

    </div>

</div>

<script>

    let timeout =
        Number("${pageContext.session.maxInactiveInterval}");

    const sessionTimeoutSeconds = timeout;

    const countdown =
        document.getElementById("countdown");

    let userActive = false;

    ["mousemove", "keydown", "click", "input", "scroll"]
        .forEach(function (eventName) {

            document.addEventListener(eventName, function () {
                userActive = true;
            });

        });

    function updateCountdown() {

        countdown.innerText =
            timeout + " seconds";

        if (timeout <= 0) {

            clearInterval(timer);

            window.location.href =
                "index.jsp?error=Session expired. Please login again.";

            return;
        }

        timeout--;
    }

    updateCountdown();

    const timer =
        setInterval(updateCountdown, 1000);

    const refreshBeforeSeconds = 60;

    setInterval(function () {

        if (!userActive) {
            return;
        }

        fetch("refresh-session", {
            method: "POST"
        })

        .then(function (response) {

            if (response.status === 200) {

                console.log("Session refreshed");

                userActive = false;

                timeout = sessionTimeoutSeconds;
            }

            if (response.status === 401) {

                clearInterval(timer);

                window.location.href =
                    "index.jsp?error=Session expired. Please login again.";
            }
        })

        .catch(function (error) {

            console.error(
                "Session refresh failed",
                error
            );

        });

    }, (sessionTimeoutSeconds - refreshBeforeSeconds) * 1000);

</script>

</body>
</html>