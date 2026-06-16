<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<div class="auth-box">
    <h2>Create account</h2>

    <% String error = request.getParameter("error"); %>
    <% if ("invalid".equals(error)) { %>
        <div class="alert alert-error">Invalid input or passwords don't match.</div>
    <% } else if ("exists".equals(error)) { %>
        <div class="alert alert-error">That username is already taken.</div>
    <% } else if ("db".equals(error)) { %>
        <div class="alert alert-error">Database error. Try again.</div>
    <% } %>

    <form action="register" method="post">
        <label>Username</label>
        <input type="text" name="username" required/>

        <label>Password</label>
        <input type="password" name="password" required/>

        <label>Confirm password</label>
        <input type="password" name="confirm" required/>

        <input type="submit" value="Register"/>
    </form>

    <p>Already have an account? <a href="login.jsp">Log in</a></p>
</div>
</body>
</html>
