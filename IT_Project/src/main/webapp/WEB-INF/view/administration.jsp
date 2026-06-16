<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%@ page import="pk.wieik.it_project.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<%
    UserDTO currentUser = (UserDTO) session.getAttribute("user");
    if (currentUser == null || currentUser.getPrivileges() != 2) {
%>
<p>Access denied.</p>
<%
        return;
    }
    List<UserDTO> users = new UserDAO().getAllUsers();
%>

<h3>User administration</h3>

<form action="DG?action=administration" method="post">
    Background color:
    <input type="text" name="backgroundColor" value="${applicationScope.backgroundColor}"/><br/><br/>

    <% for (UserDTO u : users) { %>
    <b><%= u.getUser() %></b> (id=<%= u.getId() %>):
    <input type="radio" name="priv_<%= u.getId() %>" value="0" <%= u.getPrivileges() == 0 ? "checked" : "" %>> Blocked
    <input type="radio" name="priv_<%= u.getId() %>" value="1" <%= u.getPrivileges() == 1 ? "checked" : "" %>> User
    <input type="radio" name="priv_<%= u.getId() %>" value="2" <%= u.getPrivileges() == 2 ? "checked" : "" %>> Admin
    <br/>
    <% } %>

    <br/><input type="submit" value="Save changes">
</form>