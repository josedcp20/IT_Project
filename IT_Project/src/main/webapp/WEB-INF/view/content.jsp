<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%String whatPage = "/WEB-INF/view/"+request.getParameter("what_page")+".jsp";%>
<jsp:include page="<%=whatPage %>"/>
