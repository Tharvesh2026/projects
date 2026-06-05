<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    
</body>
</html>

<h2>Testing JSP</h2>

<%= session.getAttribute("user") %>

${sessionScope.user}
<br>
ID: ${sessionScope.user.id}
<br>
Name: ${sessionScope.user.name}
<br>
Username: ${sessionScope.user.uname}
<br>
Email: ${sessionScope.user.email}

<br><br>

${1+1}