<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head><title>Lista de Cómics</title></head>
<body>
<h1>Catálogo de Cómics</h1>
<table border="1">
    <tr>
        <th>Título</th>
        <th>Serie</th>
        <th>Autor</th>
    </tr>
    <%-- Bucle para mostrar los datos que vienen del Servlet --%>
    <c:forEach var="comic" items="${listaComics}">
        <tr>
            <td>${comic.title}</td>
            <td>${comic.series}</td>
            <td>${comic.cartoonist}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>