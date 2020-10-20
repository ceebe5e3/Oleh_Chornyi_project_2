<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:bundle basename="i18n">
<html>
<head>
    <meta charset="UTF-8">
    <link href="<c:url value='/style/css/bootstrap-reboot.min.css' />" rel="stylesheet">
    <link href="<c:url value='/style/css/bootstrap.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/main.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/login.css' />" rel="stylesheet" type="text/css">
    <title><fmt:message key="title.login"/></title>
</head>
<body>
<jsp:include page="fragment/header.jsp"/>

    <div id="login">
        <form method="post">
            <fieldset class="clearfix">
                <p><span class="fontawesome-user"></span>
                    <input type="hidden" name="command" value="login"/>
                    <input type="text" name="login" placeholder="<fmt:message key="login.form.login"/>" required="required"/><br/>
                <div class="text-danger">${incorrect_login}</div>
                <p><span class="fontawesome-lock"></span>
                    <input type="password" placeholder="<fmt:message key="login.form.password"/>" name="password" required="required"/><br/>
                <div class="text-danger">${incorrect_password}</div>
                <p><input type="submit" value="<fmt:message key="login.logIn"/>"></p>
            </fieldset>
        </form>
    </div>

    <c:if test="${isSuccessRegistration != null}">
        <div class="row justify-content-center">
            <p class="text-center text-success">
                <fmt:message key="login.success.registration" />
            </p>
        </div>
    </c:if>

</fmt:bundle>
</body>
</html>