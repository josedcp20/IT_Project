<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<jsp:useBean id="user" class="pk.wieik.it_project.model.DGuser" scope="session"/>

<ul>
    <li><a href="?page=main">Main page</a></li>
    <li><a href="?page=quadratic">ax<sup>2</sup>+bx+c=0</a></li>
    <li><a href="?page=third">Link3</a></li>
    ${ (user.privileges > 0) ? '<li><a href="?page=settings">Settings</a></li>' : ''}
    <% if (user.getPrivileges() == 2) { %>
    <li><a href="?page=administration">Administration</a></li>
    <% } %>
</ul>
<div id="news">
    <% if (user.getPrivileges() < 0) { %>
    <form action="DG?action=login" method="post">
        Login: <input type="text" name="login"><br/>
        Password: <input type="password" name="password"><br/>
        <input type="submit" value="Login"><br/>
    </form>
    <% } else { %>
    <form action="DG?action=logout" method="post">
        You are logged in as <b><%= user.getLogin() %></b>
        <input type="submit" value="Logout"><br/>
    </form>
    <% } %>

    <p id="news1"></p>
    <p id="news2"></p>
</div>

