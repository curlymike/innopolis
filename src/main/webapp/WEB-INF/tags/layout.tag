<%@tag description="Outer HTML Wrapper Tag" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@attribute name="title" required="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="base" value="${pageContext.request.contextPath}" scope="page" />
<!DOCTYPE html>
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>${not empty title ? title : "Hello"}</title>

    <link rel="stylesheet" href="${base}/static/bootstrap-3.3.7.min.css">

    <style>
        body {
            margin: 20px;
        }

        .main-container {
            width: 800px;
            min-width: 800px;
            max-width: 1024px;
        }

        .hint {
            border-color: #ed5;
            background-color: #fffce5;
            padding: 10px;
        }
        .narrow-form-wrapper {
            width: 350px;
        }
        .title {
            margin-top: 40px;
        }

        .messages {
            margin-top: 25px;
        }

        .content {
            margin: 40px 0;
        }
        ul.menu {
            padding: 0;
        }
        ul.menu li {
            list-style-type: none;
            float:left;
            margin-right: 3px;
            padding: 0;
        }
        ul.menu li a {
            padding: 8px 10px 6px 10px;
            background-color: #d7e9ff;
            border-bottom: 3px solid #d7e9ff;
        }
        ul.menu li.active a {
            border-bottom: 3px solid #2e6da4;
        }

    </style>

    <base href="${base}/" />
</head>
<body>
<div class="main-container">
<div class="top">
    <c:if test="${not empty sessionScope.person}">
    <ul class="menu clearfix">
        <c:forEach var="item" items="${navigation}">
            <li class="${item.active ? "active" : ""}"><a href="${item.path}">${item.label}</a></li>
        </c:forEach>
        <c:if test="${not empty sessionScope.person}">
            <li><a href="logout?destination=${requestURI}">Logout</a></li>
        </c:if>
    </ul>
    </c:if>
</div>
<div class="title"><c:if test="${not empty title}"><h1>${title}</h1></c:if></div>
    <c:if test="${not empty sessionScope.messages and fn:length(sessionScope.messages) gt 0}"><div class="messages">
        <c:forEach var="message" items="${sessionScope.messages}">
            <div class="alert ${message.typeClass}">${message.message}</div>
        </c:forEach>
    </div></c:if>
    <c:set var="messagesprinted" value="1" scope="session" />
<div class="content">
<jsp:doBody/>
</div>
<div class="footer">
    <c:if test="${not empty sessionScope.person}">
        <div class="current-user">Вы зашли как: <strong>${sessionScope.person.name}</strong></div>
    </c:if>
</div>
</div>
</body>
</html>