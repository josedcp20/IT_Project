<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%
    UserDTO mainUser = (UserDTO) session.getAttribute("user");
%>

<h2>Welcome to the Comic Book Database</h2>

<p>This system lets you browse a curated catalog of comic books, search by title,
series, artist or publisher, and keep track of your favorites.</p>

<div class="card">
    <h3>Get started</h3>
    <ul>
        <li>Browse the full catalog in <a href="comics">Comics catalog</a>.</li>
        <% if (mainUser == null) { %>
            <li>Log in or <a href="register.jsp">create an account</a> to save your favorites.</li>
        <% } else { %>
            <li>Visit <a href="?page=settings">My favorites</a> to manage your saved comics.</li>
        <% } %>
        <% if (mainUser != null && mainUser.getPrivileges() == 2) { %>
            <li>As an administrator, you can manage users and add new comics.</li>
        <% } %>
    </ul>
</div>
