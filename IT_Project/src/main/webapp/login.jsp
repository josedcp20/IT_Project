<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Log in</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<div class="auth-box">
    <h2>Log in</h2>

    <% if ("1".equals(request.getParameter("registered"))) { %>
        <div class="alert alert-success">Account created. You can log in now.</div>
    <% } %>
    <% if ("1".equals(request.getParameter("error"))) { %>
        <div class="alert alert-error">Invalid username or password.</div>
    <% } %>

    <form action="LoginServlet" method="post">
        <label>Username</label>
        <input type="text" name="username" required>

        <label>Password</label>
        <input type="password" name="password" required>

        <input type="submit" value="Log in">
    </form>

    <p>Don't have an account? <a href="register.jsp">Register here</a>.</p>
    <p><a href="index.jsp?page=main">Back to home</a></p>
</div>
</body>
</html>
