<%@page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<t:layout title="Login">
    <div class="narrow-form-wrapper">
    <form method="post" action="${pageContext.request.contextPath}/login?destination=${not empty destination ? destination : ""}" autocomplete="off" class="form-horizontal" >

            <div class="form-group form-inline">
                <input type="text" id="login" class="form-control" name="login" placeholder="homer" />
                <label for="login" class="control-label">логин</label>
            </div>

            <div class="form-group form-inline">
                <input type="password" id="password" class="form-control" name="password" placeholder="12345" />
                <label for="password" class="control-label">пароль</label>
            </div>

        <div class="form-group">
            <input type="submit" class="btn btn-primary" />
        </div>
    </form>
    </div>
<p>&nbsp;</p>
<div class="hint">Логин: <strong>user</strong>, пароль <strong>12345</strong>.</div>
</t:layout>