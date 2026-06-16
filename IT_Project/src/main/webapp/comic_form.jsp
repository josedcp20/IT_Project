<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pk.wieik.it_project.dto.ComicDTO" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    if (user == null || user.getPrivileges() != 2) {
        response.sendRedirect("comics");
        return;
    }
    ComicDTO c = (ComicDTO) request.getAttribute("comic");
    boolean editing = c != null && c.getId() > 0;
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title><%= editing ? "Edit" : "Add" %> comic</title>
    <link rel="stylesheet" type="text/css" href="style.css"/></head>
<body>
<p><a href="comics">&laquo; Back</a></p>
<h1><%= editing ? "Edit comic" : "Add new comic" %></h1>

<form action="comics" method="post">
    <input type="hidden" name="action" value="<%= editing ? "update" : "add" %>"/>
    <% if (editing) { %>
    <input type="hidden" name="id" value="<%= c.getId() %>"/>
    <% } %>

    Title: <input type="text" name="title" value="<%= editing ? c.getTitle() : "" %>" required/><br/><br/>
    Series: <input type="text" name="series" value="<%= editing ? c.getSeries() : "" %>"/><br/><br/>
    Cartoonist: <input type="text" name="cartoonist" value="<%= editing ? c.getCartoonist() : "" %>"/><br/><br/>
    Writer: <input type="text" name="writer" value="<%= editing ? c.getWriter() : "" %>"/><br/><br/>
    Publisher: <input type="text" name="publisher" value="<%= editing ? c.getPublisher() : "" %>"/><br/><br/>
    Release date (YYYY-MM-DD): <input type="text" name="releaseDate" value="<%= editing ? c.getReleaseDate() : "" %>"/><br/><br/>
    Description:<br/>
    <textarea name="description" rows="4" cols="60"><%= editing ? c.getDescription() : "" %></textarea><br/><br/>

    <input type="submit" value="<%= editing ? "Save changes" : "Add comic" %>"/>
</form>
</body>
</html>