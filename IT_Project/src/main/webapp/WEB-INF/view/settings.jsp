<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%@ page import="pk.wieik.it_project.dto.SettingsDTO" %>
<%@ page import="pk.wieik.it_project.dao.SettingsDAO" %>
<%@ page import="java.util.List" %>
<%
    UserDTO settingsUser = (UserDTO) session.getAttribute("user");
    if (settingsUser == null || settingsUser.getPrivileges() <= 0) {
%>
    <div class="alert alert-error">You must be logged in to view your favorites.</div>
<%
        return;
    }
    List<SettingsDTO> favorites = new SettingsDAO().getByUserId(settingsUser.getId());
%>

<h2>My favorites</h2>
<p>Welcome, <b><%= settingsUser.getUser() %></b>. You have <%= favorites.size() %> favorite comic<%= favorites.size() == 1 ? "" : "s" %>.</p>

<% if (favorites.isEmpty()) { %>
    <div class="card">
        <p>You haven't added any favorites yet.</p>
        <p>Browse the <a href="comics">catalog</a> and click the star to save comics you like.</p>
    </div>
<% } else { %>
    <table>
        <tr>
            <th>Title</th>
            <th>Series / Author</th>
            <th>Actions</th>
        </tr>
        <% for (SettingsDTO f : favorites) { %>
            <tr>
                <td><%= f.getName() %></td>
                <td><%= f.getSurname() %></td>
                <td>
                    <a href="comics?action=detail&id=<%= f.getAge() %>">View</a>
                    <form style="display:inline" action="comics" method="post">
                        <input type="hidden" name="action" value="removeFav"/>
                        <input type="hidden" name="comicId" value="<%= f.getAge() %>"/>
                        <input type="submit" value="Remove"/>
                    </form>
                </td>
            </tr>
        <% } %>
    </table>
<% } %>
