<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<t:layout title="Adding new student">
    <div class="narrow-form-wrapper">
    <form method="post" action="${requestURI}" class="form-horizontal">
        <div class="form-group form-inline">
            <input type="text" id="name" name="name" class="form-control" placeholder="name" value="${form.name}" />
            <label for="name">Имя</label>
        </div>
        <div class="form-group form-inline">
            <input type="text" id="birth" name="birth" class="form-control" placeholder="dd.MM.yyyy" value="${form.birth}" />
            <label for="name">Дата рождения</label>
        </div>
        <div class="form-group form-inline">
            <input type="text" id="login" name="login" class="form-control" value="${form.login}" />
            <label for="name">Логин</label>
        </div>
        <div class="form-group form-inline">
            <input type="text" id="email" name="email" class="form-control" value="${form.email}" />
            <label for="name">E-mail</label>
        </div>
        <div class="form-group form-inline">
            <input type="text" id="phone" name="phone" class="form-control" value="${form.phone}" />
            <label for="name">Телефон</label>
        </div>
        <div class="form-group form-inline">
            <input type="password" id="password" name="password" class="form-control" value="${form.password}" />
            <label for="name">Пароль</label>
        </div>

        <div class="form-group form-inline">
            <input type="submit" class="btn btn-primary" />
        </div>
    </form>
    </div>
</t:layout>