<%@ page contentType="text/html;charset=UTF-8" language="java" isErrorPage="true" %>
<%@ page isELIgnored="false" %>

<%
    Integer statusCode =
            (Integer) request.getAttribute("jakarta.servlet.error.status_code");

    String errorTitle = "Something went wrong";
    String errorMessage = "The application could not process your request. Please try again later.";

    if (statusCode != null) {
        if (statusCode == 404) {
            errorTitle = "Page Not Found";
            errorMessage = "The page you are looking for does not exist.";
        } else if (statusCode == 403) {
            errorTitle = "Access Denied";
            errorMessage = "You do not have permission to access this page.";
        } else if (statusCode == 500) {
            errorTitle = "Server Error";
            errorMessage = "Something went wrong on the server side.";
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title><%= errorTitle %></title>

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
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 40px 15px;
        }

        .error-box {
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

        .error-box::before {
            content: "";
            position: absolute;
            width: 420px;
            height: 420px;
            background: rgba(255,255,255,0.08);
            border-radius: 50%;
            left: -120px;
            top: -70px;
        }

        .error-box::after {
            content: "";
            position: absolute;
            width: 360px;
            height: 360px;
            background: rgba(0,180,255,0.35);
            border-radius: 50%;
            left: -40px;
            bottom: -170px;
        }

        .error-info {
            color: white;
            width: 42%;
            z-index: 1;
        }

        .error-info h1 {
            font-size: 54px;
            font-weight: 900;
            letter-spacing: 1px;
            margin-bottom: 15px;
        }

        .error-info p {
            font-size: 15px;
            line-height: 1.8;
            opacity: 0.95;
        }

        .error-card {
            width: 430px;
            background: white;
            border-radius: 18px;
            padding: 35px;
            z-index: 1;
            box-shadow: 0 12px 25px rgba(0,0,0,0.25);
        }

        .error-card h3 {
            color: #04336b;
            font-size: 28px;
            font-weight: 800;
            margin-bottom: 8px;
        }

        .error-card small {
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

        .error-button {
            display: block;
            margin-top: 18px;
            padding: 14px;
            border-radius: 10px;
            background: linear-gradient(135deg, #0079d6, #00518d);
            color: white;
            text-align: center;
            font-weight: 700;
            text-decoration: none;
        }

        .error-button:hover {
            color: white;
            opacity: 0.95;
        }

        @media(max-width: 768px) {
            .error-box {
                width: 92%;
                flex-direction: column;
                padding: 35px 22px;
                gap: 30px;
            }

            .error-info,
            .error-card {
                width: 100%;
            }
        }
    </style>
</head>

<body>

<div class="page-wrapper">

    <div class="error-box">

        <div class="error-info">
            <h1>ERROR</h1>
            <p>
                The request could not be completed.
                Please review the details and return to the login page.
            </p>
        </div>

        <div class="error-card">
            <h3><%= errorTitle %></h3>
            <small>Application error details</small>

            <div class="detail-item">
                <strong>Status Code:</strong>
                <%= statusCode != null ? statusCode : "Unknown" %>
            </div>

            <div class="detail-item">
                <strong>Message:</strong>
                <%= errorMessage %>
            </div>

            <a href="<%= request.getContextPath() %>/welcome" class="error-button">
                Back
            </a>
        </div>

    </div>

</div>

</body>
</html>