<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div><a href="<%=request.getContextPath()%>">Main page</a>
&nbsp;|&nbsp;
<% if (session.getAttribute("person") != null) { %><a href="<%=request.getContextPath()%>/logout">Logout</a><% } %></div>