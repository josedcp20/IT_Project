<%@ page import="pk.wieik.it_project.model.Tools" %>
<%@ page import="pk.wieik.it_project.dto.UserDTO" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    UserDTO user = (UserDTO) session.getAttribute("user");
    int privileges = (user != null) ? user.getPrivileges() : -1;

    String Page = request.getParameter("page");
    String Subpages = "main";
    if (privileges > 0) Subpages += ";settings";
    if (privileges == 2) Subpages += ";administration";
    Page = Tools.parsePage(Page, Subpages);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>
    <title>Comic Book Database</title>
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
            <jsp:include page="/WEB-INF/view/content.jsp">
                <jsp:param name="what_page" value="<%=Page%>"/>
            </jsp:include>
        </div>
    </div>
    <div id="footer">
        <jsp:include page="/WEB-INF/view/footer.jsp"/>
    </div>
</div>
</body>
</html>
