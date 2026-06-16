<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pk.wieik.it_project.dto.ComicDTO" %>
<%
    ComicDTO c = (ComicDTO) request.getAttribute("comic");
%>
<!DOCTYPE html>
<html>
<head><meta charset="UTF-8"><title><%= c.getTitle() %></title>
    <link rel="stylesheet" type="text/css" href="style.css"/></head>
<body>
<p><a href="comics">&laquo; Back to list</a></p>
<h1><%= c.getTitle() %></h1>
<p><b>Series:</b> <%= c.getSeries() %></p>
<p><b>Cartoonist:</b> <%= c.getCartoonist() %></p>
<p><b>Writer:</b> <%= c.getWriter() %></p>
<p><b>Publisher:</b> <%= c.getPublisher() %></p>
<p><b>Release date:</b> <%= c.getReleaseDate() %></p>
<p><b>Date added:</b> <%= c.getDateAdded() %></p>
<p><b>Description:</b><br/> <%= c.getDescription() %></p>
</body>
</html>