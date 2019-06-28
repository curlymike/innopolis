<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <style>
        .hint {
            border-color: #ed5;
            background-color: #fffce5;
            padding: 10px;
        }
    </style>
</head>
<body>
<h1>Login</h1>
<form method="post" action="${pageContext.request.contextPath}/login?destination=<%=(request.getParameter("destination") == null ? "" : request.getParameter("destination"))%>">
    <input type="text" id="login" name="login" placeholder="homer">
    <label for="login">логин</label>
    <br/>
    <input type="password" id="password" name="password" placeholder="12345">
    <label for="password">пароль</label>
    <br/>
    <input type="submit"/>
</form>
<p>&nbsp;</p>
<div class="hint">Логин: <strong>user</strong>, пароль <strong>12345</strong>.</div>
</body>
</html>