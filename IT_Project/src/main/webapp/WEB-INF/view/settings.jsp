<%@ page import="pk.wieik.it_project.model.DGuser" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean id="user" class="pk.wieik.it_project.model.DGuser" scope="session"/>
User: <%=user.getPrivileges()%>

<%
    if (user.getPrivileges() <= 0) {
%>
    <p>You must be logged in to access the settings page.</p>
<%
        return;
    }

    boolean saved = "1".equals(request.getParameter("saved"));
%>

<form action="DG?action=savesettings" method="post">
    Name:<input type="text" id="name" name="name" value="${user.name}"><br/>
    Surname:<input type="text" id="surname" name="surname" value="${user.surname}"><br/>
    Age:<input type="number" id="age" name="age" min="0" max="150" value="${user.ageS}"><br/>
    <input type="submit" value="Save">
</form>

<% if (saved) { %>
<div style="margin-top: 1rem; padding: 1rem; border: 1px solid #999; background: #eee; text-align: center;">
    Settings have been saved correctly<br/>
    Click <a href="?page=main">here</a> to return to the main page.
</div>
<% } %>
