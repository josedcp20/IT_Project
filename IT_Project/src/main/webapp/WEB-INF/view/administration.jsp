<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="pk.wieik.it_project.model.DGuser, java.util.HashMap" %>
<jsp:useBean id="user" class="pk.wieik.it_project.model.DGuser" scope="session"/>

<form action="DG?action=administration" method="post">
    Background color: <input type="text" name="backgroundColor"
value="${applicationScope.backgroundColor}"/><br/>

    <%
        HashMap<String, DGuser> users = (HashMap<String, DGuser>) application.getAttribute("users");
        for (String key : users.keySet()){
            DGuser u = users.get(key);
    %>
    <b><%= u.getLogin() %></b>:
    <input type="radio" name="priv_<%= u.getLogin() %>" value="1" <%= u.getPrivileges() == 1 ? "checked" : "" %>> User
    <input type="radio" name="priv_<%= u.getLogin() %>" value="2" <%= u.getPrivileges() == 2 ? "checked" : "" %>> Administrator
    <input type="submit" value="Set privileges"><br/>
    <%
        }
    %>
</form>
