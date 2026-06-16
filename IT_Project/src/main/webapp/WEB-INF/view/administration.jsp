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
<p>Change roles (use <i>Blocked</i> to disable a user without deleting their account).</p>

<form action="DG?action=administration" method="post">
    <table>
        <tr>
            <th>ID</th>
            <th>Username</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        <% for (UserDTO u : adminList) { %>
            <tr>
                <td><%= u.getId() %></td>
                <td>
                    <b><%= u.getUser() %></b>
                    <% if (u.getId() == adminUser.getId()) { %>
                        <span style="color:#6b7280; font-size:0.85rem;">(you)</span>
                    <% } %>
                </td>
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
                <td>
                    <% if (u.getId() != adminUser.getId()) { %>
                        <%-- Form anidados no se pueden, así que el delete se hace fuera y se referencia por formaction --%>
                        <button type="submit" formaction="DG?action=deleteUser&userId=<%= u.getId() %>" formmethod="post"
                                onclick="return confirm('Delete user &quot;<%= u.getUser() %>&quot; and all their favorites?');"
                                style="background:#dc2626;">
                            Delete
                        </button>
                    <% } %>
                </td>
            </tr>
        <% } %>
    </table>

    <p><input type="submit" value="Save role changes"/></p>
</form>
