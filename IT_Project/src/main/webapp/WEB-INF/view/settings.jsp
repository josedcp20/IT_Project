<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%
    UserDTO settingsUser = (UserDTO) session.getAttribute("user");
    if (settingsUser == null || settingsUser.getPrivileges() <= 0) {
%>
    <div class="alert alert-error">You must be logged in to view your favorites.</div>
<%
        return;
    }
%>

<h2>My favorites</h2>
<p>Welcome, <b><%= settingsUser.getUser() %></b>.</p>

<div class="card">
    <p>Favorites management is not yet wired up.</p>
    <p>Browse the <a href="comics">catalog</a> to find comics you like.</p>
</div>
