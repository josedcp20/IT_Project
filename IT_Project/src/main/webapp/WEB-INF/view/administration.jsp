<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%@ page import="pk.wieik.it_project.dao.UserDAO" %>
<%@ page import="java.util.List" %>
<%
    UserDTO adminUser = (UserDTO) session.getAttribute("user");
    if (adminUser == null || adminUser.getPrivileges() != 2) {
%>
    <div class="alert alert-error">Access denied.</div>
<%
        return;
    }
    List<UserDTO> adminList = new UserDAO().getAllUsers();
%>

<h2>User administration</h2>

<form action="DG?action=administration" method="post">
    <table>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Role</th>
        </tr>
        <% for (UserDTO u : adminList) { %>
            <tr>
                <td><%= u.getId() %></td>
                <td><b><%= u.getUser() %></b></td>
                <td>
                    <label style="margin-right:0.6rem;">
                        <input type="radio" name="priv_<%= u.getId() %>" value="0"
                            <%= u.getPrivileges() == 0 ? "checked" : "" %>> Blocked
                    </label>
                    <label style="margin-right:0.6rem;">
                        <input type="radio" name="priv_<%= u.getId() %>" value="1"
                            <%= u.getPrivileges() == 1 ? "checked" : "" %>> User
                    </label>
                    <label>
                        <input type="radio" name="priv_<%= u.getId() %>" value="2"
                            <%= u.getPrivileges() == 2 ? "checked" : "" %>> Admin
                    </label>
                </td>
            </tr>
        <% } %>
    </table>

    <p><input type="submit" value="Save changes"/></p>
</form>
