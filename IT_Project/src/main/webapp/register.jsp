<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<div id="container">
    <h2>Register new user</h2>

    <% String error = request.getParameter("error"); %>
    <% if ("invalid".equals(error)) { %>
    <p style="color:red;">Invalid input or passwords don't match.</p>
    <% } else if ("exists".equals(error)) { %>
    <p style="color:red;">That username is already taken.</p>
    <% } else if ("db".equals(error)) { %>
    <p style="color:red;">Database error. Try again.</p>
    <% } %>

    <form action="register" method="post">
        Username: <input type="text" name="username" required/><br/><br/>
        Password: <input type="password" name="password" required/><br/><br/>
        Confirm: <input type="password" name="confirm" required/><br/><br/>
        <input type="submit" value="Register"/>
    </form>

    <p><a href="login.jsp">Back to login</a></p>
</div>
</body>
</html>