<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>New Student</title>
</head>
<body>
<h1>Adding a new student</h1>
<form method="post" action="${pageContext.request.contextPath}/person">
    <input type="text" name="name" placeholder="name"><br/>
    <input type="text" name="birth" placeholder="birth"><br/>
    <input type="submit"/>
</form>

</body>
</html>