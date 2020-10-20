<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <meta charset="UTF-8">
    <link href="<c:url value='/style/css/bootstrap-reboot.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/bootstrap.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/main.css' />" rel="stylesheet" type="text/css">
    <fmt:setLocale value="${ empty sessionScope.lang ? 'en_US' : sessionScope.lang}" scope="session"/>
    <fmt:bundle basename="i18n">
    <title><fmt:message key="title.internal.server"/></title>
</head>

<body>
<jsp:include page="../fragment/header.jsp"/>
<div class="container justify-content-center vertical-center">
    <div class="row justify-content-center">
        <img src="<c:url value="/style/images/500.png"/>" width="1000" alt="Internal Server Error"/>
        <br />
        <p class="lead text-center" style="font-size: 30px;font-family: monospace;">
            <fmt:message key="internal.server.warning"/>
        </p>
    </div>
    <div class="row justify-content-center">
        <a class="btn btn-success" href="<c:url value="/"/>" role="button" style="background-color: #b13c13; border-color: #b13c13; font-family: cursive;">
            <fmt:message key="back.home"/>
        </a>
    </div>
</div>
</body>
</fmt:bundle>
</html>
