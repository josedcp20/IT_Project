<%@ page import="pk.wieik.it_project.model.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<jsp:useBean id="user" class="pk.wieik.it_project.model.DGuser" scope="session"/>
<%
    String Page = request.getParameter("page");
    String Subpages = "main;quadratic;third";
    if(user.getPrivileges() > 0) Subpages +=";settings";
    if(user.getPrivileges() == 2) Subpages +=";administration";
    Page = Tools.parsePage(Page, Subpages);
%>


<!DOCTYPE html>
<html style="background-color: ${(empty applicationScope.backgroundColor) ?
                                'white' : applicationScope.backgroundColor}">
<head>
    <meta charset="UTF-8" name="viewport" content="width=device-width, initial-scale=1"/>
    <title>IT-Lab6</title>
    <link rel="stylesheet" type="text/css" href="style.css"/>
    <script type="text/javascript" src="script.js"></script>
</head>
<body onload="functions(); clock(); setInterval(clock, 1000);">
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
