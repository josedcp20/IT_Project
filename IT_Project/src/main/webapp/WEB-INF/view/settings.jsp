<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    if (user == null || user.getPrivileges() <= 0) {
%>
<p>You must be logged in to access the settings page.</p>
<%
        return;
    }
    boolean saved = "1".equals(request.getParameter("saved"));
%>

<h3>Settings for <%= user.getUser() %></h3>
<p>Favorites management will go here in a later step.</p>

<% if (saved) { %>
<div style="margin-top: 1rem; padding: 1rem; border: 1px solid #999; background: #eee; text-align: center;">
    Settings have been saved correctly<br/>
    Click <a href="?page=main">here</a> to return to the main page.
</div>
<% } %>