<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="pk.wieik.it_project.dto.ComicDTO" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    boolean isAdmin = user != null && user.getPrivileges() == 2;
    List<ComicDTO> comics = (List<ComicDTO>) request.getAttribute("comics");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comics catalog</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
</head>
<body>
<h1>Comics catalog</h1>

<p><a href="index.jsp?page=main">Main</a></p>

<h3>Filter / Sort</h3>
<form action="comics" method="get">
    <input type="hidden" name="action" value="list"/>
    Sort by:
    <select name="sortBy">
        <option value="title">Title</option>
        <option value="release_date">Release date</option>
        <option value="date_added">Date added</option>
    </select>
    <input type="submit" value="Apply"/>
</form>

<h3>Search</h3>
<form action="comics" method="get">
    <input type="hidden" name="action" value="search"/>
    Title: <input type="text" name="title"/>
    Series: <input type="text" name="series"/>
    Cartoonist: <input type="text" name="cartoonist"/>
    Publisher: <input type="text" name="publisher"/>
    <input type="submit" value="Search"/>
</form>

<% if (isAdmin) { %>
<h3>Admin</h3>
<p><a href="comics?action=editForm">+ Add new comic</a></p>
<% } %>

<h3>Results</h3>
<table border="1" cellpadding="6">
    <tr>
        <th>Title</th><th>Series</th><th>Cartoonist</th><th>Publisher</th><th>Released</th><th>Actions</th>
    </tr>
    <% if (comics != null) for (ComicDTO c : comics) { %>
    <tr>
        <td><%= c.getTitle() %></td>
        <td><%= c.getSeries() %></td>
        <td><%= c.getCartoonist() %></td>
        <td><%= c.getPublisher() %></td>
        <td><%= c.getReleaseDate() %></td>
        <td>
            <a href="comics?action=detail&id=<%= c.getId() %>">View</a>
            <% if (isAdmin) { %>
            | <a href="comics?action=editForm&id=<%= c.getId() %>">Edit</a>
            | <form style="display:inline" action="comics" method="post"
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
</body>
</html>