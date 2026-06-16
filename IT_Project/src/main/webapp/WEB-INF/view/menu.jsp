<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    int privileges = (user != null) ? user.getPrivileges() : -1;
%>

<ul>
    <li><a href="?page=main">Home</a></li>
    <li><a href="comics">Comics catalog</a></li>
    <% if (privileges > 0) { %>
        <li><a href="?page=settings">My favorites</a></li>
    <% } %>
    <% if (privileges == 2) { %>
        <li><a href="?page=administration">User admin</a></li>
    <% } %>
</ul>

<div id="news">
    <% if (user == null) { %>
        <form action="LoginServlet" method="post">
            <label>Username</label>
            <input type="text" name="username" required>
            <label>Password</label>
            <input type="password" name="password" required>
            <input type="submit" value="Log in">
        </form>
        <p style="margin-top:0.6rem;"><a href="register.jsp">Create account</a></p>
    <% } else { %>
        <form action="DG?action=logout" method="post">
            <p style="margin:0 0 0.4rem;">Logged in as <b><%= user.getUser() %></b></p>
            <input type="submit" value="Log out">
        </form>
    <% } %>
</div>
