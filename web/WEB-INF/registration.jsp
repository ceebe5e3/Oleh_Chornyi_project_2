<%@ page pageEncoding="UTF-8" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <meta charset="UTF-8" >
    <link href="<c:url value='/style/css/bootstrap-reboot.min.css' />" rel="stylesheet" type="text/css">
    <link href="<c:url value='/style/css/bootstrap.min.css' />" rel="stylesheet" type="text/css">
    <fmt:setLocale value="${ empty sessionScope.lang ? 'en_US' : sessionScope.lang}" scope="session"/>
    <fmt:bundle basename="i18n">
    <title><fmt:message key="title.registration"/></title>
</head>

<body>

<jsp:include page="fragment/header.jsp"/>

<div class="container">
    <div class="row justify-content-center">
        <div class="col col-lg-4">
            <div class="card">
                <article class="card-body">
                    <h4 class="card-title" style="text-align: center;font-variant: all-small-caps;font-family: cursive;"><fmt:message key="registration.form.head"/></h4>

                    <form method="post">
                        <div class="form-group">
                            <input id="login" name="login" class="form-control" placeholder="<fmt:message key="registration.form.login"/>" type="text" value="${login}"/>
                            <span class="text-danger">${incorrect_login}</span>
                        </div>
                        <div class="form-group">
                            <input id="password" name="password" class="form-control" placeholder="<fmt:message key="registration.form.password"/>" type="password"/>
                            <div class="text-danger">${incorrect_password}</div>
                        </div>
                        <div class="form-group">
                            <input id="firstNameEN" name="firstNameEN" class="form-control" placeholder="<fmt:message key="registration.form.firstName.en"/>" type="text" value="${firstNameEN}">
                            <div class="text-danger">${incorrect_firstNameEN}</div>
                        </div>
                        <div class="form-group">
                            <input id="firstNameUA" name="firstNameUA" class="form-control" placeholder="<fmt:message key="registration.form.firstName.ua"/>" type="text" value="${firstNameUA}">
                            <div class="text-danger">${incorrect_firstNameUA}</div>
                        </div>
                        <div class="form-group">
                            <input id="lastNameEN" name="lastNameEN" class="form-control" placeholder="<fmt:message key="registration.form.lastName.en"/>" type="text" value="${lastNameEN}">
                            <div class="text-danger">${incorrect_lastNameEN}</div>
                        </div>
                        <div class="form-group">

                            <input id="lastNameUA" name="lastNameUA" class="form-control" placeholder="<fmt:message key="registration.form.lastName.ua"/>" type="text" value="${lastNameUA}">
                            <div class="text-danger">${incorrect_lastNameUA}</div>
                        </div>
                        <div class="form-group">
                            <input id="email" name="email" class="form-control" placeholder="<fmt:message key="registration.form.email" />" type="email" value="${email}" }>
                            <div class="text-danger">${incorrect_email}</div>
                        </div>
                        <div class="form-group">
                            <select id="role" class="custom-select form-control" name="role" >
                                <option value="USER" selected>USER</option>
                                <option value="SPEAKER">SPEAKER</option>
                            </select>
                            <div class="text-danger">${incorrect_role}</div>
                        </div>

                        <div class="form-group">
                            <button type="submit" class="btn btn-success btn-block">
                                <fmt:message key="registration.form.sign.up"/>
                            </button>
                        </div>
                    </form>
                </article>
            </div>
        </div>
    </div>
</div>

</fmt:bundle>

</body>
</html>
