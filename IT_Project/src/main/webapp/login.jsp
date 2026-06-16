<%--
  Created by IntelliJ IDEA.
  User: Jose Dcp
  Date: 16/06/2026
  Time: 19:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<div id="container">
    <h2>Login</h2>

    <% String error = request.getParameter("error"); %>
    <% if ("1".equals(error)) { %>
    <p style="color:red;">Invalid username or password.</p>
    <% } %>

    <form action="LoginServlet" method="post">
        <label>Username:</label><br>
        <input type="text" name="username" required><br><br>

        <label>Password:</label><br>
        <input type="password" name="password" required><br><br>

        <input type="submit" value="Login">
    </form>

    <p><a href="index.jsp?page=main">Back to main</a></p>
</div>
</body>
</html>
