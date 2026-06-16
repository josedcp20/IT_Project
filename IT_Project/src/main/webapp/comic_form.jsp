<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pk.wieik.it_project.dto.ComicDTO" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%
    UserDTO formUser = (UserDTO) session.getAttribute("user");
    if (formUser == null || formUser.getPrivileges() != 2) {
        response.sendRedirect("comics");
        return;
    }
    ComicDTO c = (ComicDTO) request.getAttribute("comic");
    boolean editing = c != null && c.getId() > 0;
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= editing ? "Edit" : "Add" %> comic</title>
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
            <h2><%= editing ? "Edit comic" : "Add new comic" %></h2>

            <form action="comics" method="post"
                  style="display:grid; grid-template-columns:1fr 1fr; gap:0.8rem; max-width:700px;">
                <input type="hidden" name="action" value="<%= editing ? "update" : "add" %>"/>
                <% if (editing) { %>
                    <input type="hidden" name="id" value="<%= c.getId() %>"/>
                <% } %>

                <div><label>Title</label><br/>
                    <input type="text" name="title" value="<%= editing ? c.getTitle() : "" %>" required style="width:100%;"/></div>
                <div><label>Series</label><br/>
                    <input type="text" name="series" value="<%= editing ? c.getSeries() : "" %>" style="width:100%;"/></div>
                <div><label>Cartoonist</label><br/>
                    <input type="text" name="cartoonist" value="<%= editing ? c.getCartoonist() : "" %>" style="width:100%;"/></div>
                <div><label>Writer</label><br/>
                    <input type="text" name="writer" value="<%= editing ? c.getWriter() : "" %>" style="width:100%;"/></div>
                <div><label>Publisher</label><br/>
                    <input type="text" name="publisher" value="<%= editing ? c.getPublisher() : "" %>" style="width:100%;"/></div>
                <div><label>Release date (YYYY-MM-DD)</label><br/>
                    <input type="text" name="releaseDate" value="<%= editing ? c.getReleaseDate() : "" %>" style="width:100%;"/></div>
                <div style="grid-column:1/3;"><label>Description</label><br/>
                    <textarea name="description" rows="5" style="width:100%;"><%= editing ? c.getDescription() : "" %></textarea></div>
                <div style="grid-column:1/3;">
                    <input type="submit" value="<%= editing ? "Save changes" : "Add comic" %>"/>
                </div>
            </form>
        </div>
    </div>
    <div id="footer">
        <jsp:include page="/WEB-INF/view/footer.jsp"/>
    </div>
</div>
</body>
</html>
