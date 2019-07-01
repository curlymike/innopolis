<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Студенты">
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Birthdate</th>
            <th>Login</th>
            <th>E-Mail</th>
            <th>Phone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="person" items="${persons}">
            <tr>
                <td scope="row">${person.id}</td>
                <td>${person.name}</td>
                <td>${person.birthDate}</td>
                <td>${person.login}</td>
                <td>${person.email}</td>
                <td>${person.phone}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</t:layout>
