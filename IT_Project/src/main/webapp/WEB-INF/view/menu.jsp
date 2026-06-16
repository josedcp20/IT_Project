<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    int privileges = (user != null) ? user.getPrivileges() : -1;
%>

<ul>
    <li><a href="?page=main">Main page</a></li>
    <li><a href="?page=quadratic">ax<sup>2</sup>+bx+c=0</a></li>
    <li><a href="?page=third">Link3</a></li>
    <li><a href="comics">Comics</a></li>
    <% if (privileges > 0) { %>
    <li><a href="?page=settings">Settings</a></li>
    <% } %>
    <% if (privileges == 2) { %>
    <li><a href="?page=administration">Administration</a></li>
    <% } %>
</ul>

<div id="news">
        <% if (user == null) { %>
    <form action="LoginServlet" method="post">
        Login: <input type="text" name="username"><br/>
        Password: <input type="password" name="password"><br/>
        <input type="submit" value="Login"><br/>
    </form>
        <% } else { %>
    <form action="DG?action=logout" method="post">
        You are logged in as <b><%= user.getUser() %></b>
        <input type="submit" value="Logout"><br/>
    </form>
        <% } %>

    <p id="news1"></p>
    <p