<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Welcome">

    <ul>
        <li>servletPath=${servletPath}</li>
        <li>requestURI=${requestURI}</li>
    </ul>

    <div>Navigation</div>
    <ul>
        <c:forEach var="item" items="${navigation}">
            <li class="${item.active ? "active" : ""}">${item.label} - ${item.path}<c:if test="${item.active}"> - [active]</c:if></li>
        </c:forEach>
    </ul>

</t:layout>
