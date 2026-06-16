<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String ctx = request.getContextPath();
    String error = request.getParameter("error");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register - Comic Book Database</title>
    <link rel="stylesheet" type="text/css" href="<%= ctx %>/style.css"/>
</head>
<body>
<div class="auth-page">
    <div class="auth-box">
        <div class="brand">
            <h1>COMIC DB</h1>
            <div class="tag">Join the multiverse</div>
        </div>

        <h2>Create account</h2>

        <% if ("invalid".equals(error)) { %>
            <div class="alert alert-error">Invalid input or passwords don't match.</div>
        <% } else if ("exists".equals(error)) { %>
            <div class="alert alert-error">That username is already taken.</div>
        <% } else if ("db".equals(error)) { %>
            <div class="alert alert-error">Database error. Please try again.</div>
        <% } %>

        <form action="<%= ctx %>/register" method="post">
            <div>
                <label>Username</label>
                <input type="text" name="username" required autofocus style="width:100%;"/>
            </div>
            <div>
                <label>Password</label>
                <input type="password" name="password" required style="width:100%;"/>
            </div>
            <div>
                <label>Confirm password</label>
                <input type="password" name="confirm" required style="width:100%;"/>
            </div>
            <input type="submit" value="Create account"/>
        </form>

        <p class="meta">
            Already have an account? <a href="<%= ctx %>/login.jsp">Log in</a>
        </p>
        <p class="meta" style="margin-top:0.4rem;">
            <a href="<%= ctx %>/index.jsp?page=main">&laquo; Back to home</a>
        </p>
    </div>
</div>
</body>
</html>
