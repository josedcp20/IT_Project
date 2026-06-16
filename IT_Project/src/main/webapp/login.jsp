<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String ctx = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Log in - Comic Book Database</title>
    <link rel="stylesheet" type="text/css" href="<%= ctx %>/style.css"/>
</head>
<body>
<div class="auth-page">
    <div class="auth-box">
        <div class="brand">
            <h1>COMIC DB</h1>
            <div class="tag">Your favorite comics, one place</div>
        </div>

        <h2>Log in</h2>

        <% if ("1".equals(request.getParameter("registered"))) { %>
            <div class="alert alert-success">Account created! You can log in now.</div>
        <% } %>
        <% if ("1".equals(request.getParameter("error"))) { %>
            <div class="alert alert-error">Invalid username or password.</div>
        <% } %>

        <form action="<%= ctx %>/LoginServlet" method="post">
            <div>
                <label>Username</label>
                <input type="text" name="username" required autofocus style="width:100%;">
            </div>
            <div>
                <label>Password</label>
                <input type="password" name="password" required style="width:100%;">
            </div>
            <input type="submit" value="Log in">
        </form>

        <p class="meta">
            New here? <a href="<%= ctx %>/register.jsp">Create an account</a>
        </p>
        <p class="meta" style="margin-top:0.4rem;">
            <a href="<%= ctx %>/index.jsp?page=main">&laquo; Back to home</a>
        </p>
    </div>
</div>
</body>
</html>
