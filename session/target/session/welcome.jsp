<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome session</title>
    <style>
        * {
            font-family: Arial, sans-serif;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        body {
            margin: 20px;
        }
    </style>
</head>
<body>
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
</body>
</html>