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
    <input type="text" id="name" name="name" placeholder="name">
    <label for="name">Имя</label>
    <br/>
    <input type="text" id="birth" name="birth" placeholder="dd.MM.yyyy">
    <label for="name">Дата рождения</label>
    <br/>
    <input type="submit"/>
</form>
<p>&nbsp;</p>
<jsp:include page="footer.jsp" />
</body>
</html>