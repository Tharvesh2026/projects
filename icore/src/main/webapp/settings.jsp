<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Session Settings</title>
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

        .session-box {
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

        .session-box::before {
            content: "";
            position: absolute;
            width: 420px;
            height: 420px;
            background: rgba(255,255,255,0.08);
            border-radius: 50%;
            left: -120px;
            top: -70px;
        }

        .session-box::after {
            content: "";
            position: absolute;
            width: 360px;
            height: 360px;
            background: rgba(0,180,255,0.35);
            border-radius: 50%;
            left: -40px;
            bottom: -170px;
        }

        .session-info {
            color: white;
            width: 42%;
            z-index: 1;
        }

        .session-info h1 {
            font-size: 40px;
            font-weight: 800;
            letter-spacing: 1px;
            margin-bottom: 15px;
        }

        .session-info p {
            font-size: 14px;
            line-height: 1.8;
            opacity: 0.95;
        }

        .session-card {
            width: 430px;
            background: white;
            border-radius: 18px;
            padding: 35px;
            z-index: 1;
            box-shadow: 0 12px 25px rgba(0,0,0,0.25);
        }

        .session-card h3 {
            color: #04336b;
            font-size: 28px;
            font-weight: 800;
            margin-bottom: 8px;
        }

        .session-card small {
            color: #777;
            display: block;
            margin-bottom: 25px;
        }

        .detail-item {
            border: 1px solid #e5e5e5;
            border-radius: 10px;
            padding: 12px 14px;
            margin-bottom: 12px;
            font-size: 14px;
            background: #fafafa;
        }

        .detail-item strong {
            color: #04336b;
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
            .session-box {
                width: 92%;
                flex-direction: column;
                padding: 35px 22px;
                gap: 30px;
            }

            .session-info,
            .session-card {
                width: 100%;
            }
        }
    </style>
</head>

<body>

<%@ include file="navbar.jsp" %>

<div class="page-wrapper">

    <div class="session-box">

        <div class="session-info">
            <h1>SESSION</h1>
            <p>
                View your current login session details, timeout status,
                creation time and last accessed time in one place.
            </p>
        </div>

        <div class="session-card">
            <h3>Session Details</h3>
            <small>Your active login session information</small>

            <div class="detail-item">
                <strong>Name:</strong>
                ${sessionScope.user.name}
            </div>

            <div class="detail-item">
                <strong>Session ID:</strong>
                ${pageContext.session.id}
            </div>

            <div class="detail-item" id="creationTime">
                ${pageContext.session.creationTime}
            </div>

            <div class="detail-item" id="lastAccessTime">
                ${pageContext.session.lastAccessedTime}
            </div>

            <div class="detail-item" id="timeout">
                ${pageContext.session.maxInactiveInterval}
            </div>

            <div class="detail-item" id="isNew">
                ${pageContext.session.isNew()}
            </div>

            <div class="countdown-box">
                Session expires in <span id="countdown"></span>
            </div>
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
                    remainingSeconds = sessionTimeoutSeconds;
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