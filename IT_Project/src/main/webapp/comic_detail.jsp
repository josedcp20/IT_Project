<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pk.wieik.it_project.dto.ComicDTO" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%@ page import="pk.wieik.it_project.dao.SettingsDAO" %>
<%
    ComicDTO c = (ComicDTO) request.getAttribute("comic");
    UserDTO detailUser = (UserDTO) session.getAttribute("user");
    boolean detailLogged = detailUser != null && detailUser.getPrivileges() > 0;
    boolean alreadyFav = detailLogged && new SettingsDAO().isFavorite(detailUser.getId(), c.getId());
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= c.getTitle() %></title>
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
            <p><a href="comics">&laquo; Back to catalog</a></p>
            <h2><%= c.getTitle() %></h2>

            <% if (detailLogged) { %>
                <form action="comics" method="post" style="margin-bottom:1rem;">
                    <input type="hidden" name="action" value="<%= alreadyFav ? "removeFav" : "addFav" %>"/>
                    <input type="hidden" name="comicId" value="<%= c.getId() %>"/>
                    <input type="hidden" name="from" value="detail"/>
                    <input type="submit" value="<%= alreadyFav ? "Remove from favorites" : "Add to favorites" %>"/>
                </form>
            <% } %>

            <div class="card">
                <p><b>Series:</b> <%= c.getSeries() %></p>
                <p><b>Cartoonist:</b> <%= c.getCartoonist() %></p>
                <p><b>Writer:</b> <%= c.getWriter() %></p>
                <p><b>Publisher:</b> <%= c.getPublisher() %></p>
                <p><b>Release date:</b> <%= c.getReleaseDate() %></p>
                <p><b>Date added:</b> <%= c.getDateAdded() %></p>
            </div>

            <h3>Description</h3>
            <p><%= c.getDescription() %></p>
        </div>
    </div>
    <div id="footer">
        <jsp:include page="/WEB-INF/view/footer.jsp"/>
    </div>
</div>
</body>
</html>
