<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Set" %>
<%@ page import="java.util.HashSet" %>
<%@ page import="pk.wieik.it_project.dto.ComicDTO" %>
<%@ page import="pk.wieik.it_project.dto.SettingsDTO" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%@ page import="pk.wieik.it_project.dao.SettingsDAO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    boolean isAdmin = user != null && user.getPrivileges() == 2;
    boolean isLogged = user != null && user.getPrivileges() > 0;
    List<ComicDTO> comics = (List<ComicDTO>) request.getAttribute("comics");

    // Precarga el set de IDs favoritos del usuario actual para no consultar BD por cada fila
    Set<Integer> favIds = new HashSet<>();
    if (isLogged) {
        for (SettingsDTO s : new SettingsDAO().getByUserId(user.getId())) {
            favIds.add(s.getAge());
        }
    }
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comics catalog</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<div id="container">
    <div id="header">
        <jsp:include page="/WEB-INF/view/header.jsp"/>
    </div>
    <div id="middle">
        <div id="menu">
            <jsp:include page="/WEB-INF/view/menu.jsp"/>
        </div>
        <div id="content">
            <h2>Comics catalog</h2>

            <div class="card">
                <h3>Sort</h3>
                <form action="comics" method="get" style="display:flex; gap:0.6rem; align-items:center;">
                    <input type="hidden" name="action" value="list"/>
                    <label>Sort by</label>
                    <select name="sortBy">
                        <option value="title">Title</option>
                        <option value="release_date">Release date</option>
                        <option value="date_added">Date added</option>
                    </select>
                    <input type="submit" value="Apply"/>
                </form>
            </div>

            <div class="card">
                <h3>Search</h3>
                <form action="comics" method="get"
                      style="display:grid; grid-template-columns:repeat(2,1fr); gap:0.6rem;">
                    <input type="hidden" name="action" value="search"/>
                    <div><label>Title</label><br/><input type="text" name="title" style="width:100%;"/></div>
                    <div><label>Series</label><br/><input type="text" name="series" style="width:100%;"/></div>
                    <div><label>Cartoonist</label><br/><input type="text" name="cartoonist" style="width:100%;"/></div>
                    <div><label>Publisher</label><br/><input type="text" name="publisher" style="width:100%;"/></div>
                    <div style="grid-column:1/3;">
                        <input type="submit" value="Search"/>
                    </div>
                </form>
            </div>

            <% if (isAdmin) { %>
                <p><a href="comics?action=editForm" class="btn-success">+ Add new comic</a></p>
            <% } %>

            <table>
                <tr>
                    <th>Title</th><th>Series</th><th>Cartoonist</th><th>Publisher</th><th>Released</th><th>Actions</th>
                </tr>
                <% if (comics == null || comics.isEmpty()) { %>
                    <tr><td colspan="6" style="text-align:center; color:#6b7280;">No comics found.</td></tr>
                <% } else for (ComicDTO c : comics) { %>
                    <tr>
                        <td><%= c.getTitle() %></td>
                        <td><%= c.getSeries() %></td>
                        <td><%= c.getCartoonist() %></td>
                        <td><%= c.getPublisher() %></td>
                        <td><%= c.getReleaseDate() %></td>
                        <td>
                            <a href="comics?action=detail&id=<%= c.getId() %>">View</a>
                            <% if (isLogged) {
                                boolean isFav = favIds.contains(c.getId());
                            %>
                                <form action="comics" method="post">
                                    <input type="hidden" name="action" value="<%= isFav ? "removeFav" : "addFav" %>"/>
                                    <input type="hidden" name="comicId" value="<%= c.getId() %>"/>
                                    <input type="submit" value="<%= isFav ? "Unfavorite" : "Favorite" %>"/>
                                </form>
                            <% } %>
                            <% if (isAdmin) { %>
                                <a href="comics?action=editForm&id=<%= c.getId() %>">Edit</a>
                                <form style="display:inline" action="comics" method="post"
                                      onsubmit="return confirm('Delete this comic?');">
                                    <input type="hidden" name="action" value="delete"/>
                                    <input type="hidden" name="id" value="<%= c.getId() %>"/>
                                    <input type="submit" value="Delete"/>
                                </form>
                            <% } %>
                        </td>
                    </tr>
                <% } %>
            </table>
        </div>
    </div>
    <div id="footer">
        <jsp:include page="/WEB-INF/view/footer.jsp"/>
    </div>
</div>
</body>
</html>
